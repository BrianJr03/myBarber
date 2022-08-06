package jr.brian.mybarber.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import jr.brian.mybarber.databinding.ActivityBookingSummaryBinding
import jr.brian.mybarber.model.data.services.BarberService
import jr.brian.mybarber.model.util.startHomeActivity
import jr.brian.mybarber.view.adapters.SummaryServiceAdapter

class BookingSummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingSummaryBinding
    private lateinit var adapter: SummaryServiceAdapter
    private lateinit var services: ArrayList<BarberService>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            summaryBackArrow.setOnClickListener {
                super.onBackPressed()
//                startHomeActivity(
//                    this@BookingSummaryActivity,
//                    this@BookingSummaryActivity
//                )
            }
        }
        initDummyData()
    }

    private fun setAdapter() {
        adapter = SummaryServiceAdapter(this, services)
        binding.apply {
            recyclerViewServices.layoutManager = LinearLayoutManager(this@BookingSummaryActivity)
            recyclerViewServices.adapter = adapter
        }
    }

    private fun initDummyData() {
        services = ArrayList()
        for (i in 1..3) {
            services.add(
                BarberService(
                    30.0,
                    15.0,
                    1,
                    "Bald Fade",
                    "",
                    false
                )
            )
        }
        setAdapter()
    }
}