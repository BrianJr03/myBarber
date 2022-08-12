package jr.brian.mybarber.view.activities.barber

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ActivityBarbersBinding
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.barber.Barber
import jr.brian.mybarber.view.adapters.barber.BarberAdapter
import jr.brian.mybarber.viewmodel.barbers.BarberVMFactory
import jr.brian.mybarber.viewmodel.barbers.BarberViewModel

class BarbersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBarbersBinding
    private lateinit var barberAdapter: BarberAdapter
    private lateinit var barbers: ArrayList<Barber>
    lateinit var viewModel: BarberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarbersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        barbers = ArrayList()
        setAdapter()
        setupViewModel()
        setupObservers()
        viewModel.getBarbers()
        binding.apply {
            backBarbers.setOnClickListener {
                super.onBackPressed()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                finish()
            }
        }
    }

    private fun setAdapter() {
        barberAdapter = BarberAdapter(this, barbers)
        binding.apply {
            barberRecyclerView.layoutManager = LinearLayoutManager(this@BarbersActivity)
            barberRecyclerView.adapter = barberAdapter
        }
    }

    private fun setupViewModel() {
        val factory = BarberVMFactory(Repository())
        viewModel = ViewModelProvider(this, factory)[BarberViewModel::class.java]
        binding.viewModel = viewModel
    }

    private fun setupObservers() {
        viewModel.barberResponse.observe(this) {
            barbers = it.barbers
            binding.apply {
                animationView.visibility = View.GONE
                barberRecyclerView.visibility = View.VISIBLE
            }
            setAdapter()
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }
}