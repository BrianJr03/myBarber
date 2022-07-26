package jr.brian.mybarber.viewmodel.appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.appointment.BookedAppointment
import jr.brian.mybarber.model.data.appointment.Slot
import jr.brian.mybarber.model.data.response.SignInResponse
import jr.brian.mybarber.model.data.response.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppointmentViewModel(private val repository: Repository) : ViewModel() {
    val appointmentsDateLiveData: LiveData<String> = repository.appointmentsDateLiveData
    val currentAppointmentsLiveData: MutableLiveData<ArrayList<Slot>> =
        repository.currentAppointmentsLiveData
    val appointmentsLiveData: MutableLiveData<ArrayList<BookedAppointment>> =
        repository.appointmentsLiveData

    val signUpResponse: MutableLiveData<SignInResponse?> = repository.signInResponse

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

    fun getAppointments(apptId: String, psAuthToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAppointments(userId = apptId, psAuthToken = psAuthToken)
        }
    }

    fun setAppointmentsDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.appointmentsDateLiveData.postValue(date)
        }
    }
}