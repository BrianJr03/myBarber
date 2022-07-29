package jr.brian.mybarber.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ActivityBusinessHoursBinding
import java.time.LocalDateTime

class BusinessHoursActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBusinessHoursBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusinessHoursBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.apply {
            hoursBackArrow.setOnClickListener {
                super.onBackPressed()
                finish()
            }
        }
        initOpenTv()
    }

    private fun initOpenTv() {
        binding.apply {
            val currentDate = LocalDateTime.now()
            val day = currentDate.dayOfWeek
            val hour = currentDate.hour
            val businessHours = intArrayOf(9, 10, 11, 12, 13, 14, 15, 16, 17) // 9 am - 5 pm
            val satHours = intArrayOf(9, 10, 11, 12, 13, 14) // 9 am - 2 pm
            if (hour !in businessHours) {
                openTv.text = getString(R.string.closed_caps)
            } else if (day.toString() == "SATURDAY" && hour !in satHours) {
                openTv.text = getString(R.string.closed_caps)
            }
        }
    }
}