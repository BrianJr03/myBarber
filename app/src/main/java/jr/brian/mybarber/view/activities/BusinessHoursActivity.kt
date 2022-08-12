package jr.brian.mybarber.view.activities

import android.os.Bundle
import android.widget.TextView
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
                overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
                )
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
            val businessHours = intArrayOf(9, 10, 11, 12, 13, 14, 15, 16) // 9 am - 4 pm CST
            val satHours = intArrayOf(9, 10, 11, 12, 13) // 9 am - 1 pm CST
            if (day.toString() == "MONDAY") {
                openTv.text = getString(R.string.closed_caps)
            }
            if (hour !in businessHours) {
                openTv.text = getString(R.string.closed_caps)
            } else if (day.toString() == "SATURDAY" && hour !in satHours) {
                openTv.text = getString(R.string.closed_caps)
            }
        }
        initDayTVs()
    }

    private fun initDayColor(tv: TextView) {
        val now = LocalDateTime.now()
        val text = tv.text.toString()
        if (text.uppercase() == now.dayOfWeek.toString()) {
            tv.setTextColor(getColor(R.color.gold))
        }
    }

    private fun initDayTVs() {
        binding.apply {
            initDayColor(sundayTv)
            initDayColor(mondayTv)
            initDayColor(tuesdayTv)
            initDayColor(wednesdayTv)
            initDayColor(thursdayTv)
            initDayColor(fridayTv)
            initDayColor(saturdayTv)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_left
        )
    }
}