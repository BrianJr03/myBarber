package jr.brian.mybarber.view.adapters.services

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ServiceItemBinding
import jr.brian.mybarber.model.data.services.ServiceItem

class BeardStylesAdapter(
    private val context: Context,
    private val serviceItems: List<ServiceItem>
) :
    RecyclerView.Adapter<BeardStylesAdapter.BeardStylesViewHolder>() {

    lateinit var binding: ServiceItemBinding

    override fun getItemCount() = serviceItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeardStylesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ServiceItemBinding.inflate(layoutInflater, parent, false)
        return BeardStylesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BeardStylesViewHolder, position: Int) {
        holder.apply {
            val serviceItem = serviceItems[position]
            bind(serviceItem)
            itemView.setOnClickListener {
//                context.startActivity(Intent(context, BeardStylesActivity::class.java))
//                (context as AppCompatActivity).overridePendingTransition(
//                    R.anim.slide_in_right,
//                    R.anim.slide_out_right
//                )
            }
        }
    }

    inner class BeardStylesViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(serviceItem: ServiceItem) {
            val title = v.findViewById<TextView>(R.id.sItem_title)
            val price = v.findViewById<TextView>(R.id.sItem_price)
            val duration = v.findViewById<TextView>(R.id.sItem_duration)
            val image = v.findViewById<AppCompatImageView>(R.id.sItem_image)
            // TODO - load image via glide
            title.text = serviceItem.title
            price.text = serviceItem.price
            duration.text = serviceItem.duration
        }
    }
}