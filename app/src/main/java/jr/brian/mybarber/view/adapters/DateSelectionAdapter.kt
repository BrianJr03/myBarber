package jr.brian.mybarber.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.DateSelectionItemBinding
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.Slot
import jr.brian.mybarber.viewmodel.appointment.AppointmentViewModel

class DateSelectionAdapter(private val context: Context, private val slots: List<Slot>) :
    RecyclerView.Adapter<DateSelectionAdapter.SelectDateHolder>() {
    private val repository = Repository()
    lateinit var appointmentViewModel: AppointmentViewModel
    lateinit var binding: DateSelectionItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectDateHolder {
        binding = DateSelectionItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        appointmentViewModel =
            ViewModelProvider(context as AppCompatActivity)[AppointmentViewModel::class.java]
        return SelectDateHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SelectDateHolder, position: Int) {
        holder.apply {
            val info = slots[position]
            holder.bind(info)
        }
    }

    override fun getItemCount(): Int {
        return slots.size
    }

    inner class SelectDateHolder(val v: View) : RecyclerView.ViewHolder(binding.root) {
        fun bind(slot: Slot) {
            binding.tvDate.text = slot.date
            binding.tvDay.text = slot.day
            appointmentViewModel.appointmentsDateLiveData.observe(context as AppCompatActivity) {
                if (slot.date == it) {
                    binding.dateSlotBg.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.blueish_idk
                        )
                    )
                } else {
                    binding.dateSlotBg.setBackgroundColor(
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
