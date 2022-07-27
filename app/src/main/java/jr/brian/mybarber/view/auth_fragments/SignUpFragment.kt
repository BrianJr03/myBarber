package jr.brian.mybarber.view.auth_fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessaging
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.FragmentSignUpBinding
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.remote.ApiService
import jr.brian.mybarber.model.util.*
import jr.brian.mybarber.view.activities.HomeActivity
import jr.brian.mybarber.viewmodel.SignUpVMFactory
import jr.brian.mybarber.viewmodel.SignUpViewModel

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
                validateForm(view)
            }
        }
    }

    private fun validateForm(view: View) {
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
                    showSnackbar("Passwords do not match", view, R.id.sign_up_root)
                }
            }
        }
    }

    companion object {
        const val FILENAME = "login-details"
        const val FULL_NAME = "full name"
        const val MOBILE_NO = "mobile no"
        const val EMAIL = "email"
        const val PASSWORD = "password"
    }

    private fun clear() {
        binding.apply {
            fullNameEt.text?.clear()
            mobileNoEt.text?.clear()
            passwordEt.text?.clear()
            cPasswordEt.text?.clear()
        }
    }

    private fun setupViewModel() {
        val factory = SignUpVMFactory(Repository())
        viewModel = ViewModelProvider(this, factory)[SignUpViewModel::class.java]
        binding.viewModel = viewModel
    }

    private fun setupObservers() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {
//            saveUser(it.user)
            startActivity(Intent(requireContext(), HomeActivity::class.java))
            activity?.finish()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
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