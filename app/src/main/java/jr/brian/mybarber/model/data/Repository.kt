package jr.brian.mybarber.model.data

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import jr.brian.mybarber.model.data.barber.BarberResponse
import jr.brian.mybarber.model.data.remote.ApiClient.retrofit
import jr.brian.mybarber.model.data.remote.ApiService
import jr.brian.mybarber.model.data.request.SignInRequest
import jr.brian.mybarber.model.data.request.SignUpRequest
import jr.brian.mybarber.model.data.response.SignInResponse
import jr.brian.mybarber.model.data.response.SignUpResponse
import jr.brian.mybarber.model.data.services.BarberServiceResponse
import jr.brian.mybarber.view.adapters.appointment.TimeSelectionAdapter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Repository() {

    private val apiService = retrofit.create(ApiService::class.java)
    val signUpResponse = MutableLiveData<SignUpResponse>()
    val signInResponse = MutableLiveData<SignInResponse>()
    val barberLiveData = MutableLiveData<BarberResponse>()
    val barberServiceLiveData = MutableLiveData<BarberServiceResponse>()
    val isProcessing = ObservableField<Boolean>()

    val appointmentsDateLiveData = MutableLiveData<String>()
    val appointmentsStartFromLiveData = MutableLiveData<Int>()
    val currentAppointmentsLiveData = MutableLiveData<ArrayList<Slot>>()

    val barberServicesSelectLiveData = MutableLiveData<ArrayList<Int>>()

    val error = MutableLiveData<String>()

    fun setAppointmentsDate(date: String) {
        appointmentsDateLiveData.postValue(date)
    }

    fun setAppointmentsStartFrom(startFrom: Int) {
        appointmentsStartFromLiveData.postValue(startFrom)
    }

    fun signIn(signInRequest: SignInRequest) {
        isProcessing.set(true)
        val call = apiService.signIn(signInRequest)

        call.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                isProcessing.set(false)
                if (!response.isSuccessful) {
                    error.postValue("Failed to login. Error code: ${response.code()}")
                    return
                }

                val apiResponse = response.body()

                if (apiResponse == null) {
                    error.postValue("Empty response. Please retry.")
                    return
                }

                if (apiResponse.status == 0) {
                    isProcessing.set(false)
                    signInResponse.value = apiResponse
                    Log.e("TAG_SIGNIN", signInResponse.value!!.toString())
                } else {
                    error.postValue(apiResponse.message)
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                isProcessing.set(false)
                t.printStackTrace()
                error.postValue("Error is : $t.\n\nPlease retry.")
            }
        })
    }

    fun signUp(signUpRequest: SignUpRequest) {
        isProcessing.set(true)
        val call = apiService.signUp(signUpRequest)

        call.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                isProcessing.set(false)
                if (!response.isSuccessful) {
                    error.postValue("Failed to sign up. Error code: ${response.code()}")
                    return
                }

                val apiResponse = response.body()
                if (apiResponse == null) {
                    error.postValue("Empty response. Please retry.")
                    return
                }
                if (apiResponse.status == 0) {
                    signUpResponse.postValue(apiResponse)
                } else {
                    error.postValue(apiResponse.message)
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                isProcessing.set(false)
                t.printStackTrace()
                error.postValue("Error is : $t.\n\nPlease retry.")
            }
        })
    }

    fun getBarbers() {
        isProcessing.set(true)

        val call: Call<BarberResponse> = apiService.getBarbers()
        call.enqueue(object : Callback<BarberResponse> {
            override fun onResponse(
                call: Call<BarberResponse>,
                response: Response<BarberResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 0) {
                        barberLiveData.postValue(response.body()!!)
                    } else {
                        Log.e("TAG", response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<BarberResponse>, t: Throwable) {
                Log.e("TAG", t.toString())
                t.printStackTrace()
            }
        })
    }

    fun getBarberServices() {
        isProcessing.set(true)

        val map = HashMap<String, String>()
        map["services"] = "services"
        val reqJson: String = Gson().toJson(map)
        val body: RequestBody =
            reqJson.toRequestBody("application/json".toMediaTypeOrNull())

        val call: Call<BarberServiceResponse> = apiService.getBarberServices(body)
        call.enqueue(object : Callback<BarberServiceResponse> {
            override fun onResponse(
                call: Call<BarberServiceResponse>,
                response: Response<BarberServiceResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 0) {
                        barberServiceLiveData.postValue(response.body()!!)
                        barberServicesSelectLiveData.postValue(ArrayList())
                    } else {
                        Log.e("response error", response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<BarberServiceResponse>, t: Throwable) {
                Log.e("TAG", t.toString())
                t.printStackTrace()
            }
        })
    }

    fun loadCurrentAppointments() {
        val call: Call<CurrentApptResponse> = apiService.getCurrentAppts()
        call.enqueue(object : Callback<CurrentApptResponse> {
            override fun onResponse(
                call: Call<CurrentApptResponse>,
                response: Response<CurrentApptResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 0) {
                        Log.e("TAG", response.body()!!.slots.toString())
                        currentAppointmentsLiveData.postValue(response.body()!!.slots)

                    } else {
                        Log.e("TAG", response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<CurrentApptResponse>, t: Throwable) {
                Log.e("TAG", t.toString())
                t.printStackTrace()
            }
        })
    }

    fun updateFcmToken(userId: String, fcmToken: String, psAuthToken: String) {
        val map = HashMap<String, Any>()
        map["userId"] = userId
        map["fcmToken"] = fcmToken
        map["application"] = "Brian"
        val reqJson: String = Gson().toJson(map)
        val body: RequestBody =
            reqJson.toRequestBody("application/json".toMediaTypeOrNull())
        val call: Call<BasicResponse> = apiService.updateFcmToken(psAuthToken, body)
        call.enqueue(object : Callback<BasicResponse> {
            override fun onResponse(
                call: Call<BasicResponse>,
                response: Response<BasicResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 0) {
                        Log.e("TAG_UPDATE", response.body()!!.toString())
                    } else {
                        Log.e("TAG_UPDATE", response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.e("TAG_UPDATE", t.toString())
                t.printStackTrace()
            }
        })
    }

//    fun bookAppointment(bookApptRequest: BookApptRequest) {
//        val call: Call<BookApptResponse> = apiService.bookAppointment(bookApptRequest)
//        call.enqueue(object : Callback<BookApptResponse> {
//            override fun onResponse(
//                call: Call<BookApptResponse>,
//                response: Response<BookApptResponse>
//            ) {
//                if (response.isSuccessful) {
//                    if (response.body()!!.status == 0) {
//                        Log.e("TAG", response.body()!!.appointment.toString())
//                    } else {
//                        Log.e("TAG", response.body()!!.toString())
//                        error.postValue("Please retry.")
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<BookApptResponse>, t: Throwable) {
//                error.postValue("Error is : $t.\n\nPlease retry.")
//                t.printStackTrace()
//            }
//        })
//    }

    fun bookAppointment(map: HashMap<String, Any>, psAuthToken: String) {
        val reqJson: String = Gson().toJson(map)
        val body: RequestBody =
            reqJson.toRequestBody("application/json".toMediaTypeOrNull())
        val call: Call<BookApptResponse> = apiService.bookAppointment(psAuthToken, body)
        call.enqueue(object : Callback<BookApptResponse> {
            override fun onResponse(
                call: Call<BookApptResponse>,
                response: Response<BookApptResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 0) {
                        Log.e("TAG_BOOK", response.body()!!.appointment.toString())

                    } else {
                        Log.e("TAG_BOOK", response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<BookApptResponse>, t: Throwable) {
                Log.e("TAG_BOOK", t.toString())
                t.printStackTrace()
            }
        })
    }
}