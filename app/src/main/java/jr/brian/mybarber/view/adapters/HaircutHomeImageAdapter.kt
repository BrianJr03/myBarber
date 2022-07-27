package jr.brian.mybarber.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.databinding.HaircutHomeImageBinding
import jr.brian.mybarber.model.data.HaircutHomeImage

class HaircutHomeImageAdapter(private val cuts: List<HaircutHomeImage>) :
    RecyclerView.Adapter<HaircutHomeImageAdapter.HaircutHomeImageViewHolder>() {

    lateinit var binding: HaircutHomeImageBinding

    override fun getItemCount() = cuts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HaircutHomeImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = HaircutHomeImageBinding.inflate(layoutInflater, parent, false)
        return HaircutHomeImageViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: HaircutHomeImageViewHolder, position: Int) {
        holder.apply {
            val cut = cuts[position]
            bind(cut)
        }
    }

    inner class HaircutHomeImageViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(image: HaircutHomeImage) {
            binding.haircutImage.setImageResource(image.image)
        }
    }
}