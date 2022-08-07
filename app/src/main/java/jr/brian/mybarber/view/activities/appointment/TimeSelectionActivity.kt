package jr.brian.mybarber.view.activities.appointment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import jr.brian.mybarber.databinding.ActivityTimeSelectionBinding
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.view.activities.home.HomeActivity
import jr.brian.mybarber.view.adapters.appointment.DateSelectionAdapter
import jr.brian.mybarber.view.adapters.appointment.TimeSelectionAdapter
import jr.brian.mybarber.viewmodel.appointment.AppointmentViewModel
import jr.brian.mybarber.viewmodel.appointment.ApptVMFactory

class TimeSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimeSelectionBinding
    lateinit var dateAdapter: DateSelectionAdapter
    lateinit var timeAdapter: TimeSelectionAdapter
    private lateinit var appointmentViewModel: AppointmentViewModel
    private val repository = Repository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        repository.updateAppointmentsSlot()
        setupViewModel()
        setupObservers()
        binding.apply {
            // TODO - Stop refresh after retrieval of data
            srLayout.setOnRefreshListener {
                Handler(Looper.getMainLooper()).postDelayed({
                    if (srLayout.isRefreshing) {
                        srLayout.isRefreshing = false;
                    }
                }, 2000)
            }
            backTime.setOnClickListener {
                super.onBackPressed()
            }
            fabCancel.setOnClickListener {
                startActivity(
                    Intent(this@TimeSelectionActivity, HomeActivity::class.java)
                )
                finish()
            }
            fabConfirm.setOnClickListener {
                startActivity(
                    Intent(
                        this@TimeSelectionActivity,
                        BookingSummaryActivity::class.java
                    )
                )
                finish()
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
        repository.currentAppointmentsLiveData.observe(this) {
            val availableSlots = it.filter { it.slots.isNotEmpty() }
            dateAdapter = DateSelectionAdapter(this, availableSlots)
//            binding.rvDates.adapter = dateAdapter
//            binding.rvDates.layoutManager =
//                LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
//            binding.tvSelectedDayDate.text = "${availableSlots[0].day}, ${availableSlots[0].date}"
            repository.setAppointmentsDate(availableSlots[0].date)
        }

        repository.appointmentsDateLiveData.observe(this) { date ->
            repository.currentAppointmentsLiveData.value!!.forEach() {
                if (it.date == date) {
                    timeAdapter = TimeSelectionAdapter(this, it.slots)
//                    binding.tvSelectedDayDate.text = "${it.day}, ${it.date}"
                    binding.recyclerViewTime.adapter = timeAdapter
                    binding.recyclerViewTime.layoutManager = GridLayoutManager(this, 4)
                }
            }
        }
    }
}