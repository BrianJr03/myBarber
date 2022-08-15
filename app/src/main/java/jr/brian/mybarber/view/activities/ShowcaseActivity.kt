package jr.brian.mybarber.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ActivityShowcaseBinding
import jr.brian.mybarber.model.data.Constant.C_HAIRCUT_PL_1
import jr.brian.mybarber.model.data.Constant.C_HAIRCUT_PL_2
import jr.brian.mybarber.model.data.Constant.C_HAIRCUT_PL_3
import jr.brian.mybarber.model.data.Constant.C_HAIRCUT_PL_4
import jr.brian.mybarber.model.data.Constant.C_HAIRCUT_PL_5
import jr.brian.mybarber.model.data.Constant.C_HAIRCUT_PL_6
import jr.brian.mybarber.model.data.Constant.C_HAIRCUT_PL_7
import jr.brian.mybarber.model.data.Constant.HAIRCUT_PL_1
import jr.brian.mybarber.model.data.Constant.HAIRCUT_PL_2
import jr.brian.mybarber.model.data.Constant.HAIRCUT_PL_3
import jr.brian.mybarber.model.data.Constant.HAIRCUT_PL_4
import jr.brian.mybarber.model.data.Constant.HAIRCUT_PL_5
import jr.brian.mybarber.model.data.Constant.HAIRCUT_PL_6
import jr.brian.mybarber.model.data.Constant.HAIRCUT_PL_7
import jr.brian.mybarber.model.data.Constant.W_HAIRCUT_PL_1
import jr.brian.mybarber.model.data.Constant.W_HAIRCUT_PL_2
import jr.brian.mybarber.model.data.Constant.W_HAIRCUT_PL_3
import jr.brian.mybarber.model.data.Constant.W_HAIRCUT_PL_4
import jr.brian.mybarber.model.data.Constant.W_HAIRCUT_PL_5
import jr.brian.mybarber.model.data.Constant.W_HAIRCUT_PL_6
import jr.brian.mybarber.model.data.Constant.W_HAIRCUT_PL_7
import jr.brian.mybarber.model.data.home.HaircutImage
import jr.brian.mybarber.view.adapters.home.HaircutHomeImageAdapter

class ShowcaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowcaseBinding
    private lateinit var homeImageAdapter: HaircutHomeImageAdapter
    private lateinit var mCuts: ArrayList<HaircutImage>
    private lateinit var wCuts: ArrayList<HaircutImage>
    private lateinit var cCuts: ArrayList<HaircutImage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowcaseBinding.inflate(layoutInflater)
        binding.apply {
            setContentView(root)
            scBackArrow.setOnClickListener {
                super.onBackPressed()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
            }
            showcaseTv.isSelected = true
            initData()
            recyclerView1.init(mCuts)
            recyclerView2.init(wCuts)
            recyclerView3.init(cCuts)
        }
    }

    private fun RecyclerView.init(cuts: ArrayList<HaircutImage>) {
        homeImageAdapter = HaircutHomeImageAdapter(this@ShowcaseActivity, cuts)
        binding.apply {
            layoutManager = LinearLayoutManager(
                this@ShowcaseActivity,
                LinearLayoutManager.HORIZONTAL, false
            )
            adapter = homeImageAdapter
        }
    }

    private fun initData() {
        mCuts = ArrayList<HaircutImage>().apply {
            add(HaircutImage(HAIRCUT_PL_1))
            add(HaircutImage(HAIRCUT_PL_2))
            add(HaircutImage(HAIRCUT_PL_3))
            add(HaircutImage(HAIRCUT_PL_4))
            add(HaircutImage(HAIRCUT_PL_5))
            add(HaircutImage(HAIRCUT_PL_6))
            add(HaircutImage(HAIRCUT_PL_7))
        }
        wCuts = ArrayList<HaircutImage>().apply {
            add(HaircutImage(W_HAIRCUT_PL_1))
            add(HaircutImage(W_HAIRCUT_PL_2))
            add(HaircutImage(W_HAIRCUT_PL_3))
            add(HaircutImage(W_HAIRCUT_PL_4))
            add(HaircutImage(W_HAIRCUT_PL_5))
            add(HaircutImage(W_HAIRCUT_PL_6))
            add(HaircutImage(W_HAIRCUT_PL_7))
        }
        cCuts = ArrayList<HaircutImage>().apply {
            add(HaircutImage(C_HAIRCUT_PL_4))
            add(HaircutImage(C_HAIRCUT_PL_7))
            add(HaircutImage(C_HAIRCUT_PL_2))
            add(HaircutImage(C_HAIRCUT_PL_6))
            add(HaircutImage(C_HAIRCUT_PL_3))
            add(HaircutImage(C_HAIRCUT_PL_5))
            add(HaircutImage(C_HAIRCUT_PL_1))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }
}