package jr.brian.mybarber.viewmodel.appointment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jr.brian.mybarber.model.data.barber.BarberResponse
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.Slot
import jr.brian.mybarber.model.data.request.SignInRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppointmentViewModel(private val repository: Repository) : ViewModel() {
    val appointmentsDateLiveData: LiveData<String> = repository.appointmentsDateLiveData
    val currentAppointmentsLiveData = MutableLiveData<ArrayList<Slot>>()

    val appointmentsStartFromLiveData = MutableLiveData<Int>()

    val error: LiveData<String> = repository.error
    val isProcessing = repository.isProcessing

    fun loadCurrentAppointments() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.loadCurrentAppointments()
        }
    }
}