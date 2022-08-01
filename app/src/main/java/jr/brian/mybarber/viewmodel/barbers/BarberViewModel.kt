package jr.brian.mybarber.viewmodel.barbers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jr.brian.mybarber.model.data.BarberResponse
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.model.data.request.SignInRequest
import jr.brian.mybarber.model.data.response.SignInResponse

class BarberViewModel(private val repository: Repository) : ViewModel() {

    val barberResponse: LiveData<BarberResponse> = repository.barberLiveData
    val error: LiveData<String> = repository.error
    val isProcessing = repository.isProcessing

    val mobileNo = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun getBarbers() {
        repository.getBarbers()
    }
}