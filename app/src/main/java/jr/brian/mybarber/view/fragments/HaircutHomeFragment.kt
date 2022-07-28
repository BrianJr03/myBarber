package jr.brian.mybarber.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.FragmentHaircutHomeBinding
import jr.brian.mybarber.model.data.HaircutHomeImage
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
            add(
                HaircutHomeImage(
                    "https://preview.redd.it/zvdm1l99tij81.jpg?" +
                            "width=640&crop=smart&auto=webp&s=ac3d8f28164" +
                            "7085cf246ccc3c2405658b800784a"
                )
            )
            add(
                HaircutHomeImage(
                    "https://i.pinimg.com/564x/d3/25/cf/d325cf54b05983afa1e0a203471f4d94.jpg"
                )
            )
            add(
                HaircutHomeImage(
                    "https://i.pinimg.com/564x/c6/d3/8f/c6d38f8835fa4cc46032bbb66c56b5b4.jpg"
                )
            )
            add(
                HaircutHomeImage(
                    "https://therighthairstyles.com/wp-content/uploads/900x/designs-for-men/" +
                            "1-mens-line-haircut-with-long-top.jpg"
                )
            )
            add(
                HaircutHomeImage(
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9X6yxc" +
                            "SkVT5_6Svy3K6mlPjo4786oXJzfYA&usqp=CAU"
                )
            )
            add(
                HaircutHomeImage(
                    "https://www.menshairstyletrends.com/wp-content/uploads/2021/01" +
                            "/High-fade-haircut-with-beard-nurii_b.jpg"
                )
            )
            add(
                HaircutHomeImage(
                    "https://menshaircuts.com/wp-content/uploads/2021/12/how-to-bear" +
                            "d-styles-imperial-short-hair-fade-683x1024.jpg"
                )
            )
        }
    }
}