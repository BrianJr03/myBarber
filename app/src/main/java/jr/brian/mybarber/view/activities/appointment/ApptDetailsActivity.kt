package jr.brian.mybarber.view.activities.appointment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import jr.brian.mybarber.databinding.ActivityApptDetailsBinding
import jr.brian.mybarber.model.data.services.BarberService
import jr.brian.mybarber.model.util.startHomeActivity
import jr.brian.mybarber.view.adapters.services.SummaryServiceAdapter

class ApptDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApptDetailsBinding
    private lateinit var adapter: SummaryServiceAdapter
    private lateinit var services: ArrayList<BarberService>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApptDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            detailsBackArrow.setOnClickListener {
                super.onBackPressed()
            }
            var isInFirstPosition = true
            playScrollBtn.setOnClickListener {
                isInFirstPosition = if (isInFirstPosition) {
                    recyclerViewDetails.smoothScrollToPosition(adapter.itemCount)
                    false
                } else {
                    recyclerViewDetails.smoothScrollToPosition(0)
                    true
                }
            }
            fabCancel.setOnClickListener {
                Toast.makeText(
                    this@ApptDetailsActivity,
                    "Appointment has been canceled",
                    Toast.LENGTH_LONG
                ).show()
                startHomeActivity(
                    this@ApptDetailsActivity,
                    this@ApptDetailsActivity
                )
            }
            fabConfirm.setOnClickListener {
                Toast.makeText(
                    this@ApptDetailsActivity,
                    "Appointment has been set",
                    Toast.LENGTH_LONG
                ).show()
                startHomeActivity(
                    this@ApptDetailsActivity,
                    this@ApptDetailsActivity
                )
            }
        }
        initDummyData()
    }

    private fun setAdapter() {
        adapter = SummaryServiceAdapter(this, services)
        binding.apply {
            recyclerViewDetails.layoutManager = LinearLayoutManager(
                this@ApptDetailsActivity,
                LinearLayoutManager.HORIZONTAL, false
            )
            recyclerViewDetails.adapter = adapter
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