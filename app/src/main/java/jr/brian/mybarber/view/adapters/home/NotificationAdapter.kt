package jr.brian.mybarber.view.adapters.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.NotificationBinding
import jr.brian.mybarber.model.data.notification.Noti
import jr.brian.mybarber.model.util.openDialog

class NotificationAdapter(
    private val context: Context,
    private val notifications: List<Noti>
) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    lateinit var binding: NotificationBinding

    override fun getItemCount() = notifications.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = NotificationBinding.inflate(layoutInflater, parent, false)
        return NotificationViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.apply {
            val notification = notifications[position]
            bind(notification)
            itemView.setOnClickListener {
                openDialog(
                    context,
                    "Notification",
                    R.drawable.notifications_none_36,
                    "${notification.body}\n\nReceived on ${notification.date}"
                )
            }
        }
    }

    inner class NotificationViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(notification: Noti) {
            val title = v.findViewById<TextView>(R.id.noti_title)
            val date = v.findViewById<TextView>(R.id.noti_date)
            title.text = notification.title
            date.text = notification.date.split(" @ ")[0]
        }
    }
}
