package jr.brian.mybarber.viewmodel.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jr.brian.mybarber.model.data.Repository

@Suppress("UNCHECKED_CAST")
class SignUpVMFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpViewModel(repository) as T
    }
}