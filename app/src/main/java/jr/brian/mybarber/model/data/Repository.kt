package jr.brian.mybarber.model.data

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import jr.brian.mybarber.model.data.remote.ApiClient.retrofit
import jr.brian.mybarber.model.data.remote.ApiService
import jr.brian.mybarber.model.data.request.SignInRequest
import jr.brian.mybarber.model.data.request.SignUpRequest
import jr.brian.mybarber.model.data.response.SignInResponse
import jr.brian.mybarber.model.data.response.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository() {

    private val apiService = retrofit.create(ApiService::class.java)
    val signUpResponse = MutableLiveData<SignUpResponse>()
    val signInResponse = MutableLiveData<SignInResponse>()
    val barberLiveData = MutableLiveData<BarberResponse>()
    val isProcessing = ObservableField<Boolean>()

    val error = MutableLiveData<String>()

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
                    signInResponse.postValue(apiResponse)
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
            override fun onResponse(call: Call<BarberResponse>, response: Response<BarberResponse>) {
                if (response.isSuccessful) {
                    if(response.body()!!.status == 0){
                        barberLiveData.postValue(response.body()!!)
                        Log.i("TAG", response.body()!!.barbers.toString())
                    } else {
                        Log.e("response error", response.body()!!.message)
                    }
                }
            }
            override fun onFailure(call: Call<BarberResponse>, t: Throwable) {
                Log.e("TAG", t.toString())
                t.printStackTrace()
            }
        })
    }
}