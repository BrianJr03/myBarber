package jr.brian.mybarber.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.databinding.BarberServiceItemBinding
import jr.brian.mybarber.model.data.services.BarberService
import jr.brian.mybarber.view.activities.BarberServicesActivity

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
            itemView.setOnClickListener {
                context.startActivity(Intent(context, BarberServicesActivity::class.java))
            }
        }
    }

    inner class BarberServiceViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(barberService: BarberService) {
            binding.tvServiceName.text = barberService.serviceName
            binding.tvCost.text = barberService.cost.toString()
            binding.tvDuration.text = barberService.duration.toString()
//            if (barberService.serviceId in viewModel.barberServiceResponse.value!!) {
//                binding.ivSelect.isSelected = true
//            }
//            binding.root.setOnClickListener {
//                binding.ivSelect.isSelected = !binding.ivSelect.isSelected
//                if (binding.ivSelect.isSelected && !viewModel.barberServiceResponse.value!!.contains(
//                        service.serviceId
//                    )
//                ) {
//                    mainViewModel.barberServicesSelectLiveData.value!!.add(service.serviceId)
//                }
//
//                if (!binding.ivSelect.isSelected && mainViewModel.barberServicesSelectLiveData.value!!.contains(
//                        service.serviceId
//                    )
//                ) {
//                    mainViewModel.barberServicesSelectLiveData.value!!.remove(service.serviceId)
//                }
//                Log.e(
//                    "barberServicesSelect",
//                    mainViewModel.barberServicesSelectLiveData.value.toString()
//                )
//            }

//            Glide.with(fragment.requireActivity().applicationContext)
//                .load(BASE_IMAGE_URL + service.servicePic)
//                .into(binding.ivServicePic)
//        }
        }
    }
}
