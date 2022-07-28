package jr.brian.mybarber.view.activities.services

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jr.brian.mybarber.databinding.ActivityHaircutBinding

class HaircutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHaircutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHaircutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}