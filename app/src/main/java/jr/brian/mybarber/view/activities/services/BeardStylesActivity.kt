package jr.brian.mybarber.view.activities.services

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ActivityBeardStylesBinding
import jr.brian.mybarber.model.data.services.ServiceItem
import jr.brian.mybarber.view.adapters.services.BeardStylesAdapter

class BeardStylesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeardStylesBinding
    private lateinit var beardStylesAdapter: BeardStylesAdapter
    private lateinit var serviceItems: ArrayList<ServiceItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeardStylesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
        binding.apply {
            backBeardStyles.setOnClickListener {
                super.onBackPressed()
                overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
                )
                finish()
            }
        }
    }

    private fun setAdapter() {
        initData()
        beardStylesAdapter = BeardStylesAdapter(this, serviceItems)
        binding.apply {
            beardRecyclerView.layoutManager = LinearLayoutManager(this@BeardStylesActivity)
            beardRecyclerView.adapter = beardStylesAdapter
        }
    }

    private fun initData() {
        serviceItems = ArrayList()
        for (i in 1..5) {
            serviceItems.add(
                ServiceItem(
                    "",
                    "Trimmed",
                    "25 Minutes",
                    "20"
                )
            )
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