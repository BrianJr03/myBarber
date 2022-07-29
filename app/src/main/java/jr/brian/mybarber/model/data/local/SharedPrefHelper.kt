package jr.brian.mybarber.model.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import jr.brian.mybarber.model.data.UserLogin
import jr.brian.mybarber.view.auth_fragments.SignInFragment

class SharedPrefHelper(context: Context) {
    var editor: SharedPreferences.Editor
    var encryptedSharedPrefs: SharedPreferences

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

    fun signOut() {
        editor.apply {
            clear()
            apply()
        }
    }
}