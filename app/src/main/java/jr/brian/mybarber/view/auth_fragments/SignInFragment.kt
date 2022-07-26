package jr.brian.mybarber.view.auth_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import jr.brian.mybarber.databinding.FragmentSignInBinding
import jr.brian.mybarber.model.util.validatePassword
import jr.brian.mybarber.model.util.validatePhone
import jr.brian.mybarber.view.activities.HomeActivity
import jr.brian.mybarber.view.activities.MainActivity

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
        initView(view)
    }

    private fun initView(view: View) {
//        val email = binding.emailEtSignIn.text

//        val password = binding.passwordEtSignIn.text
        binding.signInBTN.setOnClickListener {
            validateForm()
//            if (email?.isEmpty() == true || password?.isEmpty() == true) {
//                showEmptyFieldsMsg()
//            } else {
//                // TODO - Sign in user
//                startHomeActivity()
//            }
        }
    }

    private fun validateForm() {
        var validated: Boolean
        binding.apply {
            validated = validatePhone(phoneEtSignIn)
            validated = validatePassword(passwordEtSignIn)
        }
        if (validated) {
            startHomeActivity()
        }
    }

    companion object {
        const val FILENAME = "sign_in_details"
    }

    private fun showEmptyFieldsMsg() {
        activity?.let {
            Snackbar.make(
                it.findViewById(android.R.id.content),
                "Ensure fields are not empty", Snackbar.LENGTH_LONG
            ).show()
        }
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