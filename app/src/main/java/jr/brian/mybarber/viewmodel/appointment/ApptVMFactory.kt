package jr.brian.mybarber.viewmodel.appointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jr.brian.mybarber.model.data.Repository

@Suppress("UNCHECKED_CAST")
class ApptVMFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppointmentViewModel(repository) as T
    }
}