package jr.brian.mybarber.view.activities.barber

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import jr.brian.mybarber.databinding.ActivityBarberServicesBinding
import jr.brian.mybarber.model.data.Constant.SELECTED_BARBER
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.model.data.services.BarberService
import jr.brian.mybarber.view.activities.appointment.TimeSelectionActivity
import jr.brian.mybarber.view.adapters.barber.BarberServiceAdapter
import jr.brian.mybarber.viewmodel.barbers.BarberServiceVMFactory
import jr.brian.mybarber.viewmodel.barbers.BarberServiceViewModel

class BarberServicesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBarberServicesBinding
    private lateinit var barberAdapter: BarberServiceAdapter
    private lateinit var barberServices: ArrayList<BarberService>
    lateinit var viewModel: BarberServiceViewModel
    private lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarberServicesBinding.inflate(layoutInflater)
        sharedPrefHelper = SharedPrefHelper(this)
        setContentView(binding.root)
        barberServices = ArrayList()
        setupViewModel()
        setupObservers()
        viewModel.getBarberServices()
        binding.apply {
            srLayout.setOnRefreshListener {
                Handler(Looper.getMainLooper()).postDelayed({
                    if (srLayout.isRefreshing) {
                        viewModel?.getBarberServices()
                        srLayout.isRefreshing = false;
                    }
                }, 2000)
            }
            backBarberServices.setOnClickListener {
                sharedPrefHelper.removeData(SELECTED_BARBER)
                super.onBackPressed()
                finish()
            }
            btnContinue.setOnClickListener {
                val selectedServices = ArrayList<BarberService>()
                for (b in barberServices) {
                    if (b.isSelected) {
                        selectedServices.add(b)
                    }
                }
                if (selectedServices.isNotEmpty()) {
                    sharedPrefHelper.saveListOfServices(selectedServices)
                    startActivity(
                        Intent(
                            this@BarberServicesActivity,
                            TimeSelectionActivity::class.java
                        )
                    )
                } else {
                    Toast.makeText(
                        this@BarberServicesActivity,
                        " Please select at least 1 option",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            btnChangeBarber.setOnClickListener {
                sharedPrefHelper.removeData(SELECTED_BARBER)
                super.onBackPressed()
            }
        }

    }

    private fun setAdapter() {
        barberAdapter = BarberServiceAdapter(this, barberServices)
        binding.apply {
            recyclerViewServices.layoutManager = LinearLayoutManager(this@BarberServicesActivity)
            recyclerViewServices.adapter = barberAdapter
        }
    }

    private fun setupViewModel() {
        val factory = BarberServiceVMFactory(Repository())
        viewModel = ViewModelProvider(this, factory)[BarberServiceViewModel::class.java]
        binding.viewModel = viewModel
    }

    private fun setupObservers() {
        viewModel.barberServiceResponse.observe(this) {
            barberServices = it.services
            setAdapter()
            binding.apply {
                animationView.visibility = View.GONE
                recyclerViewServices.visibility = View.VISIBLE
            }
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}