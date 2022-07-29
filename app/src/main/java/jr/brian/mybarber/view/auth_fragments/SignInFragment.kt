package jr.brian.mybarber.view.auth_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import jr.brian.mybarber.databinding.FragmentSignInBinding
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.UserLogin
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.model.util.WorkManagerClass
import jr.brian.mybarber.model.util.startHomeActivity
import jr.brian.mybarber.model.util.validatePassword
import jr.brian.mybarber.model.util.validatePhone
import jr.brian.mybarber.viewmodel.sign_in.SignInVMFactory
import jr.brian.mybarber.viewmodel.sign_in.SignInViewModel
import java.util.concurrent.TimeUnit

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
        viewModel.loginResponse.observe(viewLifecycleOwner) {
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
                doWork()
            }
        }
    }

    private fun doWork() {
        val data = Data.Builder()

        data.putString("message", "Welcome to myBarber")
        data.putString("content", "Tap here to receive a free haircut!")

        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val otr = OneTimeWorkRequest.Builder(WorkManagerClass::class.java)
            .setInputData(data.build())
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(requireActivity().applicationContext).enqueue(otr)

        WorkManager.getInstance(requireActivity().applicationContext)
            .getWorkInfoByIdLiveData(otr.id).observe(
            requireActivity()
        ) {
            if (it != null && it.state == WorkInfo.State.SUCCEEDED) {
                viewModel.signIn()
            }
        }
    }

    companion object {
        const val FILENAME = "login-details"
        const val PHONE_NUM = "phone_num"
        const val PASSWORD = "password"
    }
}