package jr.brian.mybarber.view.activities.appointment

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ActivityBookingSummaryBinding
import jr.brian.mybarber.model.data.Constant
import jr.brian.mybarber.model.data.Constant.APPT_DATE
import jr.brian.mybarber.model.data.Constant.SELECTED_BARBER
import jr.brian.mybarber.model.data.Constant.SELECTED_SERVICES
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.model.data.services.BarberService
import jr.brian.mybarber.model.util.startHomeActivity
import jr.brian.mybarber.view.adapters.services.SummaryServiceAdapter
import jr.brian.mybarber.viewmodel.appointment.AppointmentViewModel
import jr.brian.mybarber.viewmodel.appointment.ApptVMFactory

class BookingSummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingSummaryBinding
    private lateinit var adapter: SummaryServiceAdapter
    private lateinit var services: ArrayList<BarberService>
    private lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var viewModel: AppointmentViewModel

    private val map = HashMap<String, Any>()

    private lateinit var notiStr: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingSummaryBinding.inflate(layoutInflater)
        sharedPrefHelper = SharedPrefHelper(this)
        services = ArrayList()
        setContentView(binding.root)
        setupViewModel()
        initData()
        val t = sharedPrefHelper.getApiToken()
        binding.apply {
            summaryBackArrow.setOnClickListener {
                super.onBackPressed()
                overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
                )
            }
            viewCouponsTv.setOnClickListener {
                Toast.makeText(
                    this@BookingSummaryActivity,
                    "Coming Soon",
                    Toast.LENGTH_LONG
                ).show()
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
                val apiToken: String = t.substring(1, t.length - 1)
                viewModel?.bookAppointment(map, apiToken)
                LocalNotification().sendApptNotification()
                startActivity(
                    Intent(
                        this@BookingSummaryActivity,
                        ApptDetailsActivity::class.java
                    )
                )
                overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_right
                )
            }
        }
    }

    private fun setupViewModel() {
        val factory = ApptVMFactory(Repository())
        viewModel = ViewModelProvider(this, factory)[AppointmentViewModel::class.java]
        binding.viewModel = viewModel
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
                apptDuration.text = getString(R.string.appt_duration, duration)
                apptCost.text = getString(R.string.total_cost_details, cost)
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

        notiStr =
            "Appt Date: ${sharedPrefHelper.getFormattedApptDate()}" +
                    "\nStarting at $firstSlot, Ending at $lastSlot"

        binding.apply {
            selectedBarberName.text = selectedBarber.barberName
            apptTime.text = getString(R.string.appt_time, firstSlot, lastSlot)
            Glide.with(this@BookingSummaryActivity)
                .load(Constant.BASE_IMAGE_URL + selectedBarber.profilePic)
                .into(barberImage)
        }

        initApptMap(cost, duration, firstSlot, lastSlot)
    }

    private fun initApptMap(cost: Any, duration: Any, firstSlot: String, lastSlot: String) {
        val serviceIds = ArrayList<Int>()
        for (i in sharedPrefHelper.getBarberServices()) serviceIds.add(i.serviceId)
        map["userId"] = sharedPrefHelper.getSignInResponse().userId
        map["barberId"] = sharedPrefHelper.getSelectedBarber().barberId
        map["services"] = serviceIds.toString()
        map["aptDate"] = sharedPrefHelper.getApptDate()
        map["timeFrom"] = firstSlot
        map["timeTo"] = lastSlot
        map["totalDuration"] = duration.toString()
        map["totalCost"] = cost.toString()
        map["couponCode"] = ""
        map["sendSms"] = "true"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_left
        )
    }

    inner class LocalNotification {
        private val channelId = "ChannelId"
        private lateinit var notificationChannel: NotificationChannel
        private lateinit var notificationManager: NotificationManager
        private lateinit var notificationBuilder: Notification.Builder

        private fun getNotificationChannel() {
            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationChannel = NotificationChannel(
                channelId,
                "Description of channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                enableVibration(true)
                notificationManager.createNotificationChannel(this)
            }
        }

        fun sendApptNotification() {
            val intent = Intent(this@BookingSummaryActivity, ApptDetailsActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(
                    this@BookingSummaryActivity,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            getNotificationChannel()
            notificationBuilder = Notification.Builder(this@BookingSummaryActivity, channelId)
                .setContentTitle("Appointment Booked!")
                .setContentText(notiStr)
                .setSmallIcon(R.drawable.cut_24)
                .setContentIntent(pendingIntent)
            notificationManager.notify(1, notificationBuilder.build())
        }
    }
}