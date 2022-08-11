package jr.brian.mybarber.viewmodel.barbers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jr.brian.mybarber.model.data.Repository

@Suppress("UNCHECKED_CAST")
class BarberServiceVMFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BarberServiceViewModel(repository) as T
    }
}