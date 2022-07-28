package jr.brian.mybarber.view.activities.services

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jr.brian.mybarber.databinding.ActivityMassageAndSpaBinding

class MassageAndSpaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMassageAndSpaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMassageAndSpaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}