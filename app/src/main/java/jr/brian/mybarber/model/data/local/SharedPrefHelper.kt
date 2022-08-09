package jr.brian.mybarber.model.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jr.brian.mybarber.model.data.Constant.API_TOKEN
import jr.brian.mybarber.model.data.Constant.APPT_DATE
import jr.brian.mybarber.model.data.Constant.SELECTED_BARBER
import jr.brian.mybarber.model.data.Constant.SELECTED_SERVICES
import jr.brian.mybarber.model.data.Constant.TIME_SLOTS
import jr.brian.mybarber.model.data.barber.Barber
import jr.brian.mybarber.model.data.home.UserLogin
import jr.brian.mybarber.model.data.services.BarberService
import jr.brian.mybarber.view.auth_fragments.SignInFragment
import java.lang.reflect.Type

class SharedPrefHelper(context: Context) {
    var editor: SharedPreferences.Editor
    var encryptedSharedPrefs: SharedPreferences

    private val gson = Gson()

    init {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        encryptedSharedPrefs = EncryptedSharedPreferences.create(
            SignInFragment.FILENAME,
            mainKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
        editor = encryptedSharedPrefs.edit()
    }

    fun saveUser(user: UserLogin) {
        editor.apply {
            clear()
            apply()
            putString(SignInFragment.PHONE_NUM, user.phoneNum)
            putString(SignInFragment.PASSWORD, user.password)
            commit()
        }
    }

    private fun saveObject(obj: Any, key: String) {
        editor.putString(key, gson.toJson(obj))
        editor.apply()
    }

    private fun getObject(type: Type, key: String): Any {
        val json: String? = encryptedSharedPrefs.getString(key, null)
        return gson.fromJson(json, type)
    }

    fun saveApiToken(token: String) {
        saveObject(token, API_TOKEN)
    }

    fun getApiToken(): String {
        return encryptedSharedPrefs.getString(API_TOKEN, null).toString()
    }

    fun saveSelectedBarber(barber: Barber) {
        saveObject(barber, SELECTED_BARBER)
    }

    fun getSelectedBarber(): Barber {
        return getObject(
            type = object : TypeToken<Barber>() {}.type,
            key = SELECTED_BARBER) as Barber
    }

    fun saveListOfServices(list: ArrayList<BarberService>) {
        saveObject(list, SELECTED_SERVICES)
    }

    @Suppress("UNCHECKED_CAST")
    fun getBarberServices(): ArrayList<BarberService> {
        return getObject(
            type = object : TypeToken<ArrayList<BarberService>>() {}.type,
            key = SELECTED_SERVICES) as ArrayList<BarberService>
    }

    fun saveListOfTimeSlots(list: ArrayList<String>) {
        saveObject(list, TIME_SLOTS)
    }

    @Suppress("UNCHECKED_CAST")
    fun getTimeSlots() : ArrayList<String> {
        return getObject(
            type = object : TypeToken<ArrayList<String>>() {}.type,
            key = TIME_SLOTS) as ArrayList<String>
    }

    fun saveApptDateAndTime(date: String) {
        saveObject(date, APPT_DATE)
    }

    fun getApptDate(): String {
        return getObject(
            type = object : TypeToken<String>() {}.type,
            key = APPT_DATE) as String
    }

    fun removeData(key: String) {
        encryptedSharedPrefs
            .edit()
            .remove(key)
            .apply()
  }

    fun signOut() {
        editor.apply {
            clear()
            apply()
        }
    }
}