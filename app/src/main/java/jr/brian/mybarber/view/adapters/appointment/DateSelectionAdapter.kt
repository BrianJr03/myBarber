package jr.brian.mybarber.view.adapters.appointment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.DateSelectItemBinding
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.Slot
import jr.brian.mybarber.viewmodel.appointment.AppointmentViewModel

class DateSelectionAdapter(private val context: Context, private val slots: List<Slot>) :
    RecyclerView.Adapter<DateSelectionAdapter.SelectDateHolder>() {
    private val repository = Repository()
    lateinit var appointmentViewModel: AppointmentViewModel
    lateinit var binding: DateSelectItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectDateHolder {
        binding = DateSelectItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        appointmentViewModel =
            ViewModelProvider(context as AppCompatActivity)[AppointmentViewModel::class.java]
        return SelectDateHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SelectDateHolder, position: Int) {
        holder.apply {
            val date = slots[position]
            holder.bind(date)
        }
    }

    override fun getItemCount(): Int {
        return slots.size
    }

    inner class SelectDateHolder(val v: View) : RecyclerView.ViewHolder(binding.root) {
        fun bind(slot: Slot) {
            binding.tvDayMonth.text = slot.date
            binding.tvDay.text = slot.day
            appointmentViewModel.appointmentsDateLiveData.observe(context as AppCompatActivity) {
                if (slot.date == it) {
                    binding.dateCard.setBackgroundResource(R.drawable.time_slot_available)
                    binding.dateCard.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.blueish_idk
                        )
                    )
                } else {
                    binding.dateCard.setBackgroundResource(R.drawable.time_slot_selected)
                    binding.dateCard.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.white
                        )
                    )
                }
            }
            binding.root.setOnClickListener {
                repository.setAppointmentsDate(slot.date)
                repository.setAppointmentsStartFrom(-1)
            }
        }
    }
}
