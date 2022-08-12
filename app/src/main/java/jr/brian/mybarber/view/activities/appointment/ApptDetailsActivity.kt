package jr.brian.mybarber.view.activities.appointment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ActivityApptDetailsBinding
import jr.brian.mybarber.model.data.Constant
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.model.data.services.BarberService
import jr.brian.mybarber.model.util.startHomeActivity
import jr.brian.mybarber.view.adapters.services.SummaryServiceAdapter

class ApptDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApptDetailsBinding
    private lateinit var adapter: SummaryServiceAdapter
    private lateinit var services: ArrayList<BarberService>
    private lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApptDetailsBinding.inflate(layoutInflater)
        sharedPrefHelper = SharedPrefHelper(this)
        services = ArrayList()
        setContentView(binding.root)
        binding.apply {
            detailsBackArrow.setOnClickListener {
                super.onBackPressed()
                overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
                )
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
            rescheduleTv.setOnClickListener {
                Toast.makeText(
                    this@ApptDetailsActivity,
                    "Coming Soon",
                    Toast.LENGTH_LONG
                ).show()
            }
            fabCancel.setOnClickListener {
                Toast.makeText(
                    this@ApptDetailsActivity,
                    "Appointment has been canceled",
                    Toast.LENGTH_LONG
                ).show()
                sharedPrefHelper.apply {
                    removeData(Constant.APPT_DATE)
                    removeData(Constant.SELECTED_BARBER)
                    removeData(Constant.SELECTED_SERVICES)
                }
                startHomeActivity(
                    this@ApptDetailsActivity,
                    this@ApptDetailsActivity
                )
            }
            fabConfirm.setOnClickListener {
                sharedPrefHelper.apply {
                    removeData(Constant.APPT_DATE)
                    removeData(Constant.SELECTED_BARBER)
                    removeData(Constant.SELECTED_SERVICES)
                }
                startHomeActivity(
                    this@ApptDetailsActivity,
                    this@ApptDetailsActivity
                )
            }
        }
        initData()
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

    private fun initData() {
        var cost = 0
        var duration = 0
        val selectedTimeSlots = sharedPrefHelper.getTimeSlots()

        val firstSlot = selectedTimeSlots[0]
        var lastSlot = selectedTimeSlots[selectedTimeSlots.size - 1]

        services = sharedPrefHelper.getBarberServices().apply {
            for (i in this) {
                cost += i.cost.toInt()
                duration += i.duration.toInt()
            }
            binding.apply {
                apptCost.text = getString(R.string.total_cost_details, cost)
                apptDate.text = sharedPrefHelper.getApptDate()
            }
        }

        if (selectedTimeSlots.size == 1) {
            val split = firstSlot.split(":")
            val firstHour = split[0]
            val minute = split[1].toInt()
            lastSlot = "$firstHour:${minute + duration}"
        }

        setAdapter()
        val selectedBarber = sharedPrefHelper.getSelectedBarber()
        binding.apply {
            selectedBarberName.text = selectedBarber.barberName
            apptTime.text = getString(R.string.appt_time, firstSlot, lastSlot)
            Glide.with(this@ApptDetailsActivity)
                .load(Constant.BASE_IMAGE_URL + selectedBarber.profilePic)
                .into(barberImage)
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