package jr.brian.mybarber.view.adapters.barber

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.BarberServiceItemBinding
import jr.brian.mybarber.model.data.Constant.BASE_IMAGE_URL
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.services.BarberService
import kotlin.math.roundToInt

class BarberServiceAdapter(
    private val context: Context,
    private val barbers: List<BarberService>
) :
    RecyclerView.Adapter<BarberServiceAdapter.BarberServiceViewHolder>() {

    lateinit var binding: BarberServiceItemBinding

    override fun getItemCount() = barbers.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarberServiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = BarberServiceItemBinding.inflate(layoutInflater, parent, false)
        return BarberServiceViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BarberServiceViewHolder, position: Int) {
        holder.apply {
            val barberService = barbers[position]
            bind(barberService)
            binding.apply {
                serviceCheckbox.setOnClickListener {
                    barberService.isSelected = serviceCheckbox.isChecked
                }
            }
        }
    }

    inner class BarberServiceViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(barberService: BarberService) {
            val name = v.findViewById<TextView>(R.id.tv_service_name)
            val cost = v.findViewById<TextView>(R.id.tv_cost)
            val duration = v.findViewById<TextView>(R.id.tv_duration)
            val image = v.findViewById<AppCompatImageView>(R.id.service_image)
            name.text = barberService.serviceName
            cost.text = "$${barberService.cost.roundToInt()}"
            duration.text = "${ barberService.duration.roundToInt() } MIN"
            Glide.with(context)
                .load(BASE_IMAGE_URL + barberService.servicePic)
                .into(image)
        }
    }
}
