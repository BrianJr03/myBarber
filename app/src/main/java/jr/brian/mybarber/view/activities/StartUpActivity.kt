package jr.brian.mybarber.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import jr.brian.mybarber.databinding.ActivityStartUpBinding
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.model.util.WorkManagerClass
import jr.brian.mybarber.model.util.startHomeActivity
import jr.brian.mybarber.view.auth_fragments.SignInFragment
import java.util.concurrent.TimeUnit

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
        }, 2000)

        val data = Data.Builder()
        data.putString("message", "App Update")
        data.putString("content", "Please install latest update.")
        val otr = OneTimeWorkRequest.Builder(WorkManagerClass::class.java)
            .setInitialDelay(2, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(applicationContext).enqueue(otr)
    }

    private fun verifySignIn() {
        sharedPrefHelper.encryptedSharedPrefs.apply {
            if (this.contains(SignInFragment.PHONE_NUM) && this.contains(SignInFragment.PASSWORD)) {
                startHomeActivity(this@StartUpActivity, this@StartUpActivity)
            }
            else {
                startActivity(Intent(this@StartUpActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}