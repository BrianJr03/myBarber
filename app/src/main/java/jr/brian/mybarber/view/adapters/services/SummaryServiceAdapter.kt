package jr.brian.mybarber.view.adapters.services

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.BarberServiceBookedItemBinding
import jr.brian.mybarber.model.data.services.BarberService
import kotlin.math.roundToInt

class SummaryServiceAdapter(
    private val context: Context,
    private val services: ArrayList<BarberService>
) :
    RecyclerView.Adapter<SummaryServiceAdapter.SummaryServiceViewHolder>() {

    lateinit var binding: BarberServiceBookedItemBinding

    override fun getItemCount() = services.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryServiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = BarberServiceBookedItemBinding.inflate(layoutInflater, parent, false)
        return SummaryServiceViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SummaryServiceViewHolder, position: Int) {
        holder.apply {
            val barberService = services[position]
            bind(barberService)
        }
    }

    inner class SummaryServiceViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(barberService: BarberService) {
            val name = v.findViewById<TextView>(R.id.tv_service_name)
            val cost = v.findViewById<TextView>(R.id.tv_cost)
            val duration = v.findViewById<TextView>(R.id.tv_duration)
            val image = v.findViewById<AppCompatImageView>(R.id.service_image)
            name.text = barberService.serviceName
            cost.text = "$${barberService.cost.roundToInt()}"
            duration.text = "${barberService.duration.roundToInt()} MIN"
//            Glide.with(context)
//                .load(BASE_IMAGE_URL + barberService.servicePic)
//                .into(image)
        }
    }
}
