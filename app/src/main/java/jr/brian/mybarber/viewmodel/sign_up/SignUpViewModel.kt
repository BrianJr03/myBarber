package jr.brian.mybarber.viewmodel.sign_up

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.request.SignUpRequest
import jr.brian.mybarber.model.data.response.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: Repository) : ViewModel() {
    val loginResponse: MutableLiveData<SignUpResponse?> = repository.signUpResponse
    val error: LiveData<String> = repository.error
    val isProcessing = repository.isProcessing

    val mobileNo = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val fcmToken = MutableLiveData<String>()

    fun signUp() {
        viewModelScope.launch(Dispatchers.IO) {
            val signUpRequest = fcmToken.value?.let {
                mobileNo.value?.let { it1 ->
                    password.value?.let { it2 ->
                        SignUpRequest(it, it1, it2)
                    }
                }
            }
            signUpRequest?.let { repository.signUp(it) }
        }
    }
}