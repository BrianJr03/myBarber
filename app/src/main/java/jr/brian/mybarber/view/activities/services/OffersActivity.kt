package jr.brian.mybarber.view.activities.services

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jr.brian.mybarber.databinding.ActivityOffersBinding

class OffersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOffersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOffersBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}