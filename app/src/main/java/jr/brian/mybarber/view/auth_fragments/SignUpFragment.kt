package jr.brian.mybarber.view.auth_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jr.brian.mybarber.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        binding.apply {
            val signUpBtn = signUpBtn
            val fullName = fullNameEt.text
            val mobileNo = mobileNoEt.text
            val email = emailEt.text
            val password = passwordEt.text
            val cPassword = cPasswordEt.text


            signUpBtn.setOnClickListener {
                if (email.toString().isNotEmpty()
                    || password.toString().isNotEmpty()
                    || cPassword.toString().isNotEmpty()
                    || fullName.toString().isNotEmpty()
                    || mobileNo.toString().isNotEmpty()
                ) {
                    if (password.toString() == cPassword.toString()) {
                        // TODO - Sign up user
                    } else {
                        // TODO showSnackbar("Passwords do not match", view, R.id.sign_up_root)
                    }
                } else {
                    // TODO showSnackbar("Ensure fields are not empty", view, R.id.sign_up_root)
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
            emailEt.text?.clear()
            passwordEt.text?.clear()
            cPasswordEt.text?.clear()
        }
    }
}