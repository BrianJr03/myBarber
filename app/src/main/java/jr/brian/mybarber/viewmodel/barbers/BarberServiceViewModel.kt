package jr.brian.mybarber.viewmodel.barbers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.services.BarberServiceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BarberServiceViewModel(private val repository: Repository) : ViewModel() {
    val barberServiceResponse: LiveData<BarberServiceResponse> = repository.barberServiceLiveData
    val error: LiveData<String> = repository.error
    val isProcessing = repository.isProcessing

    fun getBarberServices() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBarberServices()
        }
    }
}