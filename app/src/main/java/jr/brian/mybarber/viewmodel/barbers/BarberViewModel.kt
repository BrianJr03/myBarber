package jr.brian.mybarber.viewmodel.barbers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jr.brian.mybarber.model.data.barber.BarberResponse
import jr.brian.mybarber.model.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BarberViewModel(private val repository: Repository) : ViewModel() {

    val barberResponse: LiveData<BarberResponse> = repository.barberLiveData
    val error: LiveData<String> = repository.error
    val isProcessing = repository.isProcessing

    fun getBarbers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBarbers()
        }
    }
}