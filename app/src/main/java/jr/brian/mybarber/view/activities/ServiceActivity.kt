package jr.brian.mybarber.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import jr.brian.mybarber.databinding.ActivityServiceBinding
import jr.brian.mybarber.model.data.ServiceCard
import jr.brian.mybarber.view.adapters.ServiceCardAdapter

class ServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiceBinding
    private lateinit var serviceCardAdapter: ServiceCardAdapter
    private lateinit var serviceCards: ArrayList<ServiceCard>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
    }

    private fun setAdapter() {
        initData()
        serviceCardAdapter = ServiceCardAdapter(this, serviceCards)
        binding.apply {
            serviceRecyclerView.layoutManager = GridLayoutManager(
                this@ServiceActivity, 2
            )
            serviceRecyclerView.adapter = serviceCardAdapter
        }
    }

    private fun initData() {
        serviceCards = ArrayList()
        for (i in 1..7) {
            serviceCards.add(
                ServiceCard(
                    "",
                    "Beard Styles"
                )
            )
        }
    }
}