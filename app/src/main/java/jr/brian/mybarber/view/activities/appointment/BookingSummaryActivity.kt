package jr.brian.mybarber.view.activities.appointment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import jr.brian.mybarber.databinding.ActivityBookingSummaryBinding
import jr.brian.mybarber.model.data.BookApptRequest
import jr.brian.mybarber.model.data.Constant
import jr.brian.mybarber.model.data.Constant.APPT_DATE
import jr.brian.mybarber.model.data.Constant.SELECTED_BARBER
import jr.brian.mybarber.model.data.Constant.SELECTED_SERVICES
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.model.data.services.BarberService
import jr.brian.mybarber.model.util.startHomeActivity
import jr.brian.mybarber.view.adapters.services.SummaryServiceAdapter

class BookingSummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingSummaryBinding
    private lateinit var adapter: SummaryServiceAdapter
    private lateinit var services: ArrayList<BarberService>
    private lateinit var sharedPrefHelper: SharedPrefHelper
    private var repository = Repository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingSummaryBinding.inflate(layoutInflater)
        sharedPrefHelper = SharedPrefHelper(this)
        services = ArrayList()
        setContentView(binding.root)
        initData()
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
                val list = listOf(2, 3, 5, 7)
                val br = BookApptRequest(
                    userId = "14",
                    barberId = "15",
                    services = list.toString(),
                    aptDate = "2018/11/25",
                    timeFrom = "11:00",
                    timeTo = "12:30",
                    totalDuration = "90",
                    totalCost = "550",
                    couponCode = "",
                    sendSms = "false"
                )
                repository.bookAppointment(br)
                startActivity(
                    Intent(
                        this@BookingSummaryActivity,
                        ApptDetailsActivity::class.java
                    )
                )
            }
        }
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
                apptDate.text = sharedPrefHelper.getApptDate()
            }
        }
        setAdapter()
        val selectedBarber = sharedPrefHelper.getSelectedBarber()
        val selectedTimeSlots = sharedPrefHelper.getTimeSlots()

        val firstSlot = selectedTimeSlots[0]
        var lastSlot = selectedTimeSlots[selectedTimeSlots.size - 1]

        if (selectedTimeSlots.size == 1) {
            val split = firstSlot.split(":")
            val firstHour = split[0]
            val minute = split[1].toInt()
            lastSlot = "$firstHour:${minute + duration}"
        }

        binding.apply {
            selectedBarberName.text = selectedBarber.barberName
            apptTime.text = "$firstSlot - $lastSlot"
            Glide.with(this@BookingSummaryActivity)
                .load(Constant.BASE_IMAGE_URL + selectedBarber.profilePic)
                .into(barberImage)
        }
    }

//    private fun initApptMap(cost: Any, duration: Any) {


//        map["userId"] = "14" // sharedPrefHelper.getUserData().userId
//        map["barberId"] = "15" // sharedPrefHelper.getSelectedBarber().barberId.toString()
//        map["services"] = intArrayOf(3, 4, 7, 5).toString() //  sharedPrefHelper.getBarberServices()
//        map["aptDate"] = "2018/11/25" //sharedPrefHelper.getApptDateAndTime()
//        map["timeFrom"] = "3:00"
//        map["timeTo"] = "3:30"
//        map["totalDuration"] = duration.toString()
//        map["totalCost"] = cost.toString()
//        map["couponCode"] = ""
//        map["sendSms"] = false.toString()


//        {
//        "userId" : 147,
//        "barberId": 15,
//        "services" : [4, 5, 9, 7],
//        "aptDate":"2018/11/25",
//        "timeFrom": "11:00",
//        "timeTo": "12:30",
//        "totalDuration": 90;
//        "totalCost": 550;
//        "couponCode" : "",
//        "sendSms" : true
//        }
}