package jr.brian.mybarber.view.adapters.appointment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.TimeGridItemBinding
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.model.util.openDialog
import jr.brian.mybarber.viewmodel.appointment.AppointmentViewModel

class TimeSelectionAdapter(
    private val context: Context, private val slot: Map<String, Boolean>, private val tv: TextView
) :
    RecyclerView.Adapter<TimeSelectionAdapter.SelectDateHolder>() {
    lateinit var appointmentViewModel: AppointmentViewModel
    lateinit var binding: TimeGridItemBinding
    private val repository = Repository()
    private var selectedTimeSlots = ArrayList<String>()
    private val sharedPrefHelper = SharedPrefHelper(context)

    override fun getItemCount() = slot.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectDateHolder {
        binding =
            TimeGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        appointmentViewModel =
            ViewModelProvider(context as AppCompatActivity)[AppointmentViewModel::class.java]
        return SelectDateHolder()
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

    inner class SelectDateHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(time: String, booked: Boolean, position: Int) {
            binding.tvTimeSlot.apply {
                text = time.split("-")[0]
                if (booked) {
                    setBackgroundResource(R.drawable.time_slot_booked)
                } else {
                    setBackgroundResource(R.drawable.time_slot_available)
                }
                var isSelected = false
                setOnClickListener {
                    isSelected = !isSelected
                    val slots = 2
                    val freeSlots = freeSlots(slots, position)
                    if (freeSlots == -1) {
                        if (isSelected) {
                            repository.setAppointmentsStartFrom(position)
                            setTextColor(context.getColor(R.color.gold))
                            tv.text = context.getString(R.string.appt_start_time, this.text)
                            selectedTimeSlots.add(this.text.toString())
                            sharedPrefHelper.saveListOfTimeSlots(selectedTimeSlots)
                        } else {
                            selectedTimeSlots.remove(this.text.toString())
                            sharedPrefHelper.saveListOfTimeSlots(selectedTimeSlots)
                            setTextColor(context.getColor(R.color.white))
                            tv.text = context.getString(R.string.no_time_selected)
                        }
                    } else {
                        openDialog(
                            context as AppCompatActivity,
                            "Time Not Available",
                            R.drawable.info_24,
                            "Please select an available time"
                        )
                    }
                }
            }

            repository.appointmentsStartFromLiveData.observe(context as AppCompatActivity) {
                if (booked) {
                    binding.tvTimeSlot.setBackgroundResource(R.drawable.time_slot_booked)
                } else {
                    binding.tvTimeSlot.setBackgroundResource(R.drawable.time_slot_available)
                }
            }
        }
    }
}
