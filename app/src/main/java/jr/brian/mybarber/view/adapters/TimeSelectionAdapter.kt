package jr.brian.mybarber.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.TimeGridItemBinding
import jr.brian.mybarber.model.data.time.TimeSlot

class TimeSelectionAdapter(
    private val context: Context,
    private val timeSlots: List<TimeSlot>
) :
    RecyclerView.Adapter<TimeSelectionAdapter.TimeViewHolder>() {

    lateinit var binding: TimeGridItemBinding

    override fun getItemCount() = timeSlots.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = TimeGridItemBinding.inflate(layoutInflater, parent, false)
        return TimeViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.apply {
            val timeSlot = timeSlots[position]
            bind(timeSlot)
//            itemView.setOnClickListener {
//                context.startActivity(Intent(context, BarberServicesActivity::class.java))
//            }
        }
    }

    inner class TimeViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(timeSlot: TimeSlot) {
            val time = v.findViewById<TextView>(R.id.tv_time_slot)
            time.text = timeSlot.time
        }
    }
}