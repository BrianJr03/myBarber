package jr.brian.mybarber.view.adapters.appointment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.TimeGridItemBinding
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.util.openDialog
import jr.brian.mybarber.viewmodel.appointment.AppointmentViewModel

class TimeSelectionAdapter(
    private val context: Context, private val slot: Map<String, Boolean>
) :
    RecyclerView.Adapter<TimeSelectionAdapter.SelectDateHolder>() {
    lateinit var appointmentViewModel: AppointmentViewModel
    lateinit var binding: TimeGridItemBinding
    private val repository = Repository()


    override fun getItemCount() = slot.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectDateHolder {
        binding =
            TimeGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        appointmentViewModel =
            ViewModelProvider(context as AppCompatActivity)[AppointmentViewModel::class.java]
        return SelectDateHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SelectDateHolder, position: Int) {
        holder.apply {
            val slot = slot.keys.elementAt(position)
            holder.bind(slot, this@TimeSelectionAdapter.slot[slot]!!, position)
        }
    }

    fun freeSlots(slots: Int, position: Int): Int {
        for (i in 0 until slots) {
            if (position + i >= slot.size || slot[slot.keys.elementAt(position + i)] == true) {
                return i
            }
        }
        return -1
    }

    inner class SelectDateHolder(private val v: View) : RecyclerView.ViewHolder(binding.root) {
        fun bind(time: String, booked: Boolean, position: Int) {
            binding.tvTimeSlot.text = time.split("-")[0]
            if (booked) {
                binding.tvTimeSlot.setBackgroundResource(R.drawable.time_slot_booked)
            } else {
                binding.tvTimeSlot.setBackgroundResource(R.drawable.time_slot_available)
            }
            binding.tvTimeSlot.setOnClickListener {
                val slots = 1 // repository.appointmentsSlotLiveData.value!!
                val freeSlots = freeSlots(slots, position)
                binding.tvTimeSlot.setBackgroundResource(R.drawable.time_slot_selected)
                if (freeSlots == -1) {
                    repository.setAppointmentsStartFrom(position)
                } else {
                    openDialog(
                        context as AppCompatActivity,
                        "Time Not Available",
                        R.drawable.info_24,
                        "Please select an available time"
                    )
                }
            }

            repository.appointmentsStartFromLiveData.observe(context as AppCompatActivity) {
                val slots = 1 // repository.appointmentsSlotLiveData.value!!
                if (it != -1 && position in it until (it + slots)) {
                    binding.tvTimeSlot.setBackgroundResource(R.drawable.time_slot_selected)
                } else {
                    if (booked) {
                        binding.tvTimeSlot.setBackgroundResource(R.drawable.time_slot_booked)
                    } else {
                        binding.tvTimeSlot.setBackgroundResource(R.drawable.time_slot_available)
                    }
                }
            }

        }
    }
}
