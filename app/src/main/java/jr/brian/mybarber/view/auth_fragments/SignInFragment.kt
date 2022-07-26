package jr.brian.mybarber.view.auth_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import jr.brian.mybarber.databinding.FragmentSignInBinding
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.home.UserLogin
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.model.util.startHomeActivity
import jr.brian.mybarber.model.util.validatePassword
import jr.brian.mybarber.model.util.validatePhone
import jr.brian.mybarber.viewmodel.sign_in.SignInVMFactory
import jr.brian.mybarber.viewmodel.sign_in.SignInViewModel

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var sharedPrefHelper: SharedPrefHelper
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
        sharedPrefHelper = SharedPrefHelper(requireContext())
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
        viewModel.signInResponse.observe(viewLifecycleOwner) {
            sharedPrefHelper.saveUser(
                UserLogin(
                    binding.phoneEtSignIn.text.toString(),
                    binding.passwordEtSignIn.text.toString()
                )
            )
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
                viewModel?.signInResponse?.observe(viewLifecycleOwner) {
                    it?.userId?.let { it1 -> viewModel?.updateFCM(it1, it.fcmToken, it.apiToken) }
                    if (it != null) {
                        sharedPrefHelper.saveApiToken(it.apiToken)
                        sharedPrefHelper.saveSignInResponse(it)
                    }
                }
            }
        }
    }

    companion object {
        const val FILENAME = "login-details"
        const val PHONE_NUM = "phone_num"
        const val PASSWORD = "password"
    }
}