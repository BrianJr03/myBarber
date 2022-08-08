package jr.brian.mybarber.view.activities.appointment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import jr.brian.mybarber.databinding.ActivityBookingSummaryBinding
import jr.brian.mybarber.model.data.Constant
import jr.brian.mybarber.model.data.Constant.APPT_DATE
import jr.brian.mybarber.model.data.Constant.SELECTED_BARBER
import jr.brian.mybarber.model.data.Constant.SELECTED_SERVICES
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.model.data.services.BarberService
import jr.brian.mybarber.model.util.startHomeActivity
import jr.brian.mybarber.view.adapters.services.SummaryServiceAdapter

class BookingSummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingSummaryBinding
    private lateinit var adapter: SummaryServiceAdapter
    private lateinit var services: ArrayList<BarberService>
    private lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var finalCost: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingSummaryBinding.inflate(layoutInflater)
        sharedPrefHelper = SharedPrefHelper(this)
        services = ArrayList()
        setContentView(binding.root)
        binding.apply {
            summaryBackArrow.setOnClickListener {
                super.onBackPressed()
            }
            fabCancel.setOnClickListener {
                sharedPrefHelper.apply {
                    removeData(APPT_DATE)
                    removeData(SELECTED_BARBER)
                    removeData(SELECTED_SERVICES)
                }
                startHomeActivity(
                    this@BookingSummaryActivity,
                    this@BookingSummaryActivity
                )
            }
            fabConfirm.setOnClickListener {
                startActivity(
                    Intent(
                        this@BookingSummaryActivity,
                        ApptDetailsActivity::class.java
                    )
                )
            }
        }
        initData()
    }

    private fun setAdapter() {
        adapter = SummaryServiceAdapter(this, services)
        binding.apply {
            recyclerViewServices.layoutManager = LinearLayoutManager(this@BookingSummaryActivity)
            recyclerViewServices.adapter = adapter
        }
    }

    private fun initData() {
        var cost = 0
        var duration = 0
        services = sharedPrefHelper.getBarberServices().apply {
            for (i in this) {
                cost += i.cost.toInt()
                duration += i.duration.toInt()
            }
            binding.apply {
                apptDuration.text = "$duration Minutes"
                apptCost.text = "Total Cost: $$cost"
                apptDate.text = sharedPrefHelper.getApptDateAndTime()
            }
        }
        setAdapter()
        val selectedBarber = sharedPrefHelper.getSelectedBarber()
        binding.apply {
            selectedBarberName.text = selectedBarber.barberName
            Glide.with(this@BookingSummaryActivity)
                .load(Constant.BASE_IMAGE_URL + selectedBarber.profilePic)
                .into(barberImage)
        }
    }
}