package jr.brian.mybarber.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import jr.brian.mybarber.databinding.ActivityBarbersBinding
import jr.brian.mybarber.model.data.BarberCard
import jr.brian.mybarber.view.adapters.BarberAdapter

class BarbersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBarbersBinding
    private lateinit var barberAdapter: BarberAdapter
    private lateinit var barbers: ArrayList<BarberCard>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarbersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
        binding.apply {
            backBarbers.setOnClickListener {
                super.onBackPressed()
                finish()
            }
        }
    }

    private fun setAdapter() {
        initData()
        barberAdapter = BarberAdapter(this, barbers)
        binding.apply {
            barberRecyclerView.layoutManager = LinearLayoutManager(this@BarbersActivity)
            barberRecyclerView.adapter = barberAdapter
        }
    }

    private fun initData() {
        barbers = ArrayList()
        for (i in 1..7) {
            barbers.add(BarberCard("Greg Wilson", 4.5f, ""))
        }
    }
}