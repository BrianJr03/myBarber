package jr.brian.mybarber.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jr.brian.mybarber.databinding.FragmentHaircutHomeBinding
import jr.brian.mybarber.model.data.Constant.HAIRCUT_PL_1
import jr.brian.mybarber.model.data.Constant.HAIRCUT_PL_2
import jr.brian.mybarber.model.data.Constant.HAIRCUT_PL_3
import jr.brian.mybarber.model.data.Constant.HAIRCUT_PL_4
import jr.brian.mybarber.model.data.Constant.HAIRCUT_PL_5
import jr.brian.mybarber.model.data.Constant.HAIRCUT_PL_6
import jr.brian.mybarber.model.data.Constant.HAIRCUT_PL_7
import jr.brian.mybarber.model.data.home.HaircutHomeImage
import jr.brian.mybarber.view.adapters.HaircutHomeImageAdapter

class HaircutHomeFragment : Fragment() {

    private lateinit var binding: FragmentHaircutHomeBinding
    private lateinit var homeImageAdapter: HaircutHomeImageAdapter
    private lateinit var cuts: ArrayList<HaircutHomeImage>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHaircutHomeBinding.inflate(inflater, container, false)
        init()
        setAdapter()
        return binding.root
    }

    private fun setAdapter() {
        initData()
        homeImageAdapter = HaircutHomeImageAdapter(requireContext(), cuts)
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            recyclerView.adapter = homeImageAdapter
        }
    }

    private fun init() {
        binding.apply {
            var isInFirstPosition = true
            playScrollBtn.setOnClickListener {
                isInFirstPosition = if (isInFirstPosition) {
                    recyclerView.smoothScrollToPosition(homeImageAdapter.itemCount)
                    false
                } else {
                    recyclerView.smoothScrollToPosition(0)
                    true
                }
            }
        }
    }

    private fun initData() {
        cuts = ArrayList<HaircutHomeImage>().apply {
            add(HaircutHomeImage(HAIRCUT_PL_1))
            add(HaircutHomeImage(HAIRCUT_PL_2))
            add(HaircutHomeImage(HAIRCUT_PL_3))
            add(HaircutHomeImage(HAIRCUT_PL_4))
            add(HaircutHomeImage(HAIRCUT_PL_5))
            add(HaircutHomeImage(HAIRCUT_PL_6))
            add(HaircutHomeImage(HAIRCUT_PL_7))
        }
    }
}