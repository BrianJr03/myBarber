package jr.brian.mybarber.view.adapters.appointment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.DateSelectItemBinding
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.appointment.Slot
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.viewmodel.appointment.AppointmentViewModel

class DateSelectionAdapter(
    private val context: Context,
    private val slots: List<Slot>,
    private val tv: TextView
) :
    RecyclerView.Adapter<DateSelectionAdapter.SelectDateHolder>() {
    private val repository = Repository()
    lateinit var appointmentViewModel: AppointmentViewModel
    lateinit var binding: DateSelectItemBinding
    private val sharedPrefHelper = SharedPrefHelper(context)

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

    override fun getItemCount(): Int = slots.size

    inner class SelectDateHolder(val v: View) : RecyclerView.ViewHolder(binding.root) {
        fun bind(slot: Slot) {
            val dateSplit = slot.date.split("-")
            val monthAbbrev = getMonthAbbrevFromInt(dateSplit[1].toInt())
            val selectedDate = "$monthAbbrev ${dateSplit[2]}"
            binding.tvDayMonth.text = selectedDate
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

            binding.apply {
                var isSelected = false
                root.setOnClickListener {
                    isSelected = !isSelected
                    val split = slot.date.split("-")
                    val fDate =
                        "${getMonthAbbrevFromInt(split[1].toInt())} ${split[2]}, ${split[0]}"
                    if (isSelected) {
                        repository.setAppointmentsDate(slot.date)
                        repository.setAppointmentsStartFrom(-1)
                        sharedPrefHelper.apply {
                            saveApptDate(slot.date)
                            saveFormattedApptDate(fDate)
                        }
                        tvDay.setTextColor(context.getColor(R.color.gold))
                        tvDayMonth.setTextColor(context.getColor(R.color.gold))
                        tv.text = fDate
                    } else {
                        tvDay.setTextColor(context.getColor(R.color.white))
                        tvDayMonth.setTextColor(context.getColor(R.color.white))
                        tv.text = context.getString(R.string.no_date_selected)
                    }
                }
            }
        }

        private fun getMonthAbbrevFromInt(monthInt: Int): String {
            when (monthInt) {
                2 -> return "Feb"
                3 -> return "Mar"
                4 -> return "Apr"
                5 -> return "May"
                6 -> return "June"
                7 -> return "July"
                8 -> return "Aug"
                9 -> return "Sep"
                10 -> return "Oct"
                11 -> return "Nov"
                12 -> return "Dec"
            }
            return "Jan"
        }
    }
}



