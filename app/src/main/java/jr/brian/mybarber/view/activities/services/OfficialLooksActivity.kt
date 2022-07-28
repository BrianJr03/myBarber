package jr.brian.mybarber.view.activities.services

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jr.brian.mybarber.databinding.ActivityOfficialLooksBinding

class OfficialLooksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOfficialLooksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOfficialLooksBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}