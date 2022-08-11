package jr.brian.mybarber.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import jr.brian.mybarber.databinding.ActivityTimeSelectionBinding
import jr.brian.mybarber.model.data.time.TimeSlot
import jr.brian.mybarber.view.adapters.TimeSelectionAdapter

class TimeSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimeSelectionBinding
    private lateinit var timeAdapter: TimeSelectionAdapter
    private lateinit var timeSlots: ArrayList<TimeSlot>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
        binding.apply {
            backTime.setOnClickListener {
                super.onBackPressed()
            }
            btnCancel.setOnClickListener {
                startActivity(
                    Intent(this@TimeSelectionActivity, HomeActivity::class.java)
                )
                finish()
            }
        }
    }

    private fun setAdapter() {
        initTimeData()
        timeAdapter = TimeSelectionAdapter(this, timeSlots)
        binding.apply {
            recyclerViewTime.layoutManager = GridLayoutManager(this@TimeSelectionActivity, 4)
            recyclerViewTime.adapter = timeAdapter
        }
    }

    private fun initTimeData() {
        timeSlots = ArrayList()
        for (i in 1..32) {
            timeSlots.add(
                TimeSlot("9:00 AM")
            )
        }
    }
}