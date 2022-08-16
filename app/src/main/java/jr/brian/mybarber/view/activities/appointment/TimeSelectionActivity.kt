package jr.brian.mybarber.view.activities.appointment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ActivityTimeSelectionBinding
import jr.brian.mybarber.model.data.Constant.APPT_DATE
import jr.brian.mybarber.model.data.Constant.SELECTED_BARBER
import jr.brian.mybarber.model.data.Constant.SELECTED_SERVICES
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.view.activities.home.HomeActivity
import jr.brian.mybarber.view.adapters.appointment.DateSelectionAdapter
import jr.brian.mybarber.view.adapters.appointment.TimeSelectionAdapter
import jr.brian.mybarber.viewmodel.appointment.AppointmentViewModel
import jr.brian.mybarber.viewmodel.appointment.ApptVMFactory

class TimeSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimeSelectionBinding
    private lateinit var dateAdapter: DateSelectionAdapter
    private lateinit var timeAdapter: TimeSelectionAdapter
    private lateinit var appointmentViewModel: AppointmentViewModel
    private lateinit var sharedPrefHelper: SharedPrefHelper
    private val repository = Repository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeSelectionBinding.inflate(layoutInflater)
        sharedPrefHelper = SharedPrefHelper(this)
        setContentView(binding.root)
        setupViewModel()
        setupObservers()
        binding.apply {
            val numOfSlots = sharedPrefHelper.getBarberServices().size
            selectNumSlots.text = getString(R.string.select_1_time_slot_s, numOfSlots)
            srLayout.setOnRefreshListener {
                Handler(Looper.getMainLooper()).postDelayed({
                    if (srLayout.isRefreshing) {
                        srLayout.isRefreshing = false
                    }
                }, 2000)
            }
            backTime.setOnClickListener {
                super.onBackPressed()
                overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
                )
            }
            fabCancel.setOnClickListener {
                sharedPrefHelper.apply {
                    removeData(APPT_DATE)
                    removeData(SELECTED_BARBER)
                    removeData(SELECTED_SERVICES)
                }
                startActivity(
                    Intent(this@TimeSelectionActivity, HomeActivity::class.java)
                )
                overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
                )
                finish()
            }
            fabConfirm.setOnClickListener {
                if (!selectedDate.text.equals(getString(R.string.no_date_selected))
                    && !selectedTime.text.equals(getString(R.string.no_time_selected))
                ) {
                    startActivity(
                        Intent(
                            this@TimeSelectionActivity,
                            BookingSummaryActivity::class.java
                        )
                    )
                    overridePendingTransition(
                        R.anim.slide_in_right,
                        R.anim.slide_out_right
                    )
                    finish()
                } else {
                    Toast.makeText(
                        this@TimeSelectionActivity,
                        "Please choose a date and time",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setupViewModel() {
        val factory = ApptVMFactory(repository)
        appointmentViewModel = ViewModelProvider(this, factory)[AppointmentViewModel::class.java]
        appointmentViewModel.loadCurrentAppointments()
        binding.viewModel = appointmentViewModel
    }

    private fun setupObservers() {
        appointmentViewModel.currentAppointmentsLiveData.observe(this) { it ->
            val availableSlots = it.filter { it.slots.isNotEmpty() }
            dateAdapter = DateSelectionAdapter(this, availableSlots, binding.selectedDate)
            binding.recyclerViewDateSelect.adapter = dateAdapter
            binding.recyclerViewDateSelect.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.apply {
                animationView.visibility = View.GONE
                recyclerViewDateSelect.visibility = View.VISIBLE
            }
            appointmentViewModel.setAppointmentsDate(availableSlots[0].date)
        }

        appointmentViewModel.appointmentsDateLiveData.observe(this) { date ->
            appointmentViewModel.currentAppointmentsLiveData.value!!.forEach {
                if (it.date == date) {
                    timeAdapter = TimeSelectionAdapter(this, it.slots, binding.selectedTime)
                    binding.recyclerViewTime.adapter = timeAdapter
                    binding.recyclerViewTime.layoutManager = GridLayoutManager(this, 4)
                    binding.apply {
                        animationView.visibility = View.GONE
                        recyclerViewTime.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }
}