package jr.brian.mybarber.viewmodel.sign_in

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.model.data.request.SignInRequest
import jr.brian.mybarber.model.data.response.SignInResponse

class SignInViewModel(private val repository: Repository) : ViewModel() {

    val loginResponse: LiveData<SignInResponse> = repository.signInResponse
    val error: LiveData<String> = repository.error
    val isProcessing = repository.isProcessing

    val mobileNo = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun signIn() {
        val signInRequest = SignInRequest(mobileNo.value!!, password.value!!)
        Log.i("TAG", signInRequest.toString())
        repository.signIn(signInRequest)
    }
}