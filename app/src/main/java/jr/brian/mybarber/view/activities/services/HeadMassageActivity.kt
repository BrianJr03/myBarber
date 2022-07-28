package jr.brian.mybarber.view.activities.services

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jr.brian.mybarber.databinding.ActivityHeadMassageBinding

class HeadMassageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeadMassageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeadMassageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}