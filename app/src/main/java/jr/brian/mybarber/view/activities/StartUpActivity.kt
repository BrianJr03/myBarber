package jr.brian.mybarber.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ActivityStartUpBinding
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.model.util.startHomeActivity
import jr.brian.mybarber.view.auth_fragments.SignInFragment

class StartUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartUpBinding
    private lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        sharedPrefHelper = SharedPrefHelper(this)
        Handler(Looper.getMainLooper()).postDelayed({
            verifySignIn()
        }, 1250)
    }

    private fun verifySignIn() {
        sharedPrefHelper.encryptedSharedPrefs.apply {
            if (this.contains(SignInFragment.PHONE_NUM) && this.contains(SignInFragment.PASSWORD)) {
                startHomeActivity(this@StartUpActivity, this@StartUpActivity)
            }
            else {
                startActivity(Intent(this@StartUpActivity, MainActivity::class.java))
                overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
                )
                finish()
            }
        }
    }
}