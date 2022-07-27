package jr.brian.mybarber.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.request.SignUpRequest
import jr.brian.mybarber.model.data.response.SignUpResponse

class SignUpViewModel(private val repository: Repository) : ViewModel() {

    val loginResponse: LiveData<SignUpResponse> = repository.signUpResponse
    val error: LiveData<String> = repository.error
    val isProcessing = repository.isProcessing

    val mobileNo = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val fcmToken = MutableLiveData<String>()

    fun signUp() {
        val signUpRequest = SignUpRequest(fcmToken.value!!, mobileNo.value!!, password.value!!)
        Log.i("TAG", signUpRequest.toString())
        repository.signUp(signUpRequest)
    }
}