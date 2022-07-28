package jr.brian.mybarber.view.auth_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import jr.brian.mybarber.databinding.FragmentSignInBinding
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.util.startHomeActivity
import jr.brian.mybarber.model.util.validatePassword
import jr.brian.mybarber.model.util.validatePhone
import jr.brian.mybarber.view.activities.HomeActivity
import jr.brian.mybarber.viewmodel.sign_in.SignInVMFactory
import jr.brian.mybarber.viewmodel.sign_in.SignInViewModel
import jr.brian.mybarber.viewmodel.sign_up.SignUpVMFactory
import jr.brian.mybarber.viewmodel.sign_up.SignUpViewModel

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        setupViewModel()
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.apply {
            signInBTN.setOnClickListener {
                validateForm()
            }
        }
    }

    private fun setupViewModel() {
        val factory = SignInVMFactory(Repository())
        viewModel = ViewModelProvider(this, factory)[SignInViewModel::class.java]
        binding.viewModel = viewModel
    }

    private fun setupObservers() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {
//            TODO - saveUser(it.user)
            startHomeActivity(requireContext(), requireActivity())
            activity?.finish()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateForm() {
        binding.apply {
            val isPhoneValid = validatePhone(phoneEtSignIn)
            val isPasswordValid = validatePassword(passwordEtSignIn)
            if (isPhoneValid && isPasswordValid) {
                viewModel?.signIn()
            }
        }
    }

    companion object {
        const val FILENAME = "sign_in_details"
    }

    //    private fun saveUser(user: User) {
//        val pref = getSharedPreferences("users", AppCompatActivity.MODE_PRIVATE)
//
//        pref.edit().apply{
//            putString("name", user.name)
//            putString("mobile_no", user.mobile_no)
//            putString("user_id", user.user_id)
//            apply()
//        }
//    }
}