package jr.brian.mybarber.viewmodel.sign_in

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.request.SignInRequest
import jr.brian.mybarber.model.data.response.SignInResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: Repository) : ViewModel() {

    val signInResponse: LiveData<SignInResponse> = repository.signInResponse
    val error: LiveData<String> = repository.error
    val isProcessing = repository.isProcessing

    val mobileNo = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun signIn() {
        viewModelScope.launch(Dispatchers.IO) {
            val signInRequest = SignInRequest(mobileNo.value!!, password.value!!)
            Log.i("TAG", signInRequest.toString())
            repository.signIn(signInRequest)
        }
    }

    fun updateFCM(userId: String, fcm: String, psAuthToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFcmToken(userId = userId, fcmToken = fcm, psAuthToken = psAuthToken)
        }
    }
}