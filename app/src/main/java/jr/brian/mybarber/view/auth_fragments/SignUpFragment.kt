package jr.brian.mybarber.view.auth_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import jr.brian.mybarber.databinding.FragmentSignUpBinding
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.util.validateCountryCode
import jr.brian.mybarber.model.util.validateName
import jr.brian.mybarber.model.util.validatePassword
import jr.brian.mybarber.model.util.validatePhone
import jr.brian.mybarber.viewmodel.sign_up.SignUpVMFactory
import jr.brian.mybarber.viewmodel.sign_up.SignUpViewModel

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                viewModel.fcmToken.postValue(it.result)
                Log.d("FCM_Token", "FCM_TOKEN: ${it.result}")
            }
        }
        setupViewModel()
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        binding.apply {
            signUpBtn.setOnClickListener {
                validateForm()
            }
        }
    }

    private fun validateForm() {
        binding.apply {
            val isNameValid = validateName(fullNameEt)
            val isPhoneValid = validatePhone(mobileNoEt)
            val isCountryCodeValid = validateCountryCode(countryCodeEt)
            val isPasswordValid = validatePassword(passwordEt)
            val isCPasswordValid = validatePassword(cPasswordEt)
            if (
                isNameValid
                && isPhoneValid
                && isCountryCodeValid
                && isPasswordValid
                && isCPasswordValid
            ) {
                if (passwordEt.text.toString() == cPasswordEt.text.toString()) {
                    viewModel?.signUp()
                } else {
                    activity?.let {
                        Snackbar.make(
                            it.findViewById(android.R.id.content),
                            "Passwords do not match",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                }
            }
        }
    }

    private fun clear() {
        binding.apply {
            fullNameEt.text?.clear()
            mobileNoEt.text?.clear()
            passwordEt.text?.clear()
            cPasswordEt.text?.clear()
            countryCodeEt.text?.clear()
        }
    }

    private fun setupViewModel() {
        val factory = SignUpVMFactory(Repository())
        viewModel = ViewModelProvider(this, factory)[SignUpViewModel::class.java]
        binding.viewModel = viewModel
    }

    private fun setupObservers() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                "Your account has been created. Please sign in",
                Toast.LENGTH_LONG
            ).show()
            clear()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}