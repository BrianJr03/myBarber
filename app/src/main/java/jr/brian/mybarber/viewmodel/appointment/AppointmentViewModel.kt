package jr.brian.mybarber.viewmodel.appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.response.SignInResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppointmentViewModel(private val repository: Repository) : ViewModel() {
    val appointmentsDateLiveData: LiveData<String> = repository.appointmentsDateLiveData
    val signInResponse: LiveData<SignInResponse> = repository.signInResponse

    val error: LiveData<String> = repository.error
    val isProcessing = repository.isProcessing

    fun loadCurrentAppointments() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.loadCurrentAppointments()
        }
    }

    fun bookAppointment(map: HashMap<String, Any>, psAuthToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.bookAppointment(map = map, psAuthToken = psAuthToken)
        }
    }
}