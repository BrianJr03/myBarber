package jr.brian.mybarber.view.sign_up

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
        val mn = mobileNo.value!!
        val p = password.value!!
        val f = fcmToken.value!!

        val loginRequest = SignUpRequest(mn, p, f)
        repository.signUp(loginRequest)
    }
}