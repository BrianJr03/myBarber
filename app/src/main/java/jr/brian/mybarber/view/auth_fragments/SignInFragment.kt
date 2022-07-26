package jr.brian.mybarber.view.auth_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import jr.brian.mybarber.databinding.FragmentSignInBinding
import jr.brian.mybarber.model.util.validatePassword
import jr.brian.mybarber.model.util.validatePhone
import jr.brian.mybarber.view.activities.HomeActivity

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
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

    private fun validateForm() {
        binding.apply {
            val isPhoneValid = validatePhone(phoneEtSignIn)
            val isPasswordValid = validatePassword(passwordEtSignIn)
            if (isPhoneValid && isPasswordValid) {
                startHomeActivity()
            }
        }
    }

    companion object {
        const val FILENAME = "sign_in_details"
    }

    private fun startHomeActivity() {
        ContextCompat.startActivity(
            requireContext(),
            Intent(context, HomeActivity::class.java),
            null
        )
        activity?.finish()
    }
}