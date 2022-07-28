package jr.brian.mybarber.view.activities.services

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jr.brian.mybarber.databinding.ActivityHairColorsBinding

class HairColorsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHairColorsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHairColorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}