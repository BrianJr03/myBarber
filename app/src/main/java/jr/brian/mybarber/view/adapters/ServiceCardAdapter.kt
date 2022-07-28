package jr.brian.mybarber.view.adapters

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ServiceCardBinding
import jr.brian.mybarber.model.data.ServiceCard

class ServiceCardAdapter(private val serviceCards: List<ServiceCard>) :
    RecyclerView.Adapter<ServiceCardAdapter.ServiceCardViewHolder>() {

    lateinit var binding: ServiceCardBinding

    override fun getItemCount() = serviceCards.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceCardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ServiceCardBinding.inflate(layoutInflater, parent, false)
        return ServiceCardViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ServiceCardViewHolder, position: Int) {
        holder.apply {
            val serviceCard = serviceCards[position]
            bind(serviceCard)
            itemView.setOnClickListener {
                // TODO
            }
        }
    }

    inner class ServiceCardViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(serviceCard: ServiceCard) {
            val name = v.findViewById<TextView>(R.id.service_name)
            val img = v.findViewById<AppCompatImageView>(R.id.service_card_img)
            name.text = serviceCard.serviceName
//            img
        }
    }
}