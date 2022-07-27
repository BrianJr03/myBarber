package jr.brian.mybarber.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.NotificationBinding
import jr.brian.mybarber.model.data.Notification

class NotificationAdapter(
    private val context: Context,
    private val notifications: List<Notification>
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
                openDialog(notification)
            }
        }
    }

    inner class NotificationViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(notification: Notification) {
            val body = v.findViewById<TextView>(R.id.noti_body)
            val date = v.findViewById<TextView>(R.id.noti_date)
            body.text = notification.body
            date.text = notification.date
        }
    }

    private fun openDialog(notification: Notification) {
        AlertDialog.Builder(context)
            .setTitle("Notification")
            .setIcon(R.drawable.notifications_36)
            .setMessage("${notification.body}\n\n${notification.date}")
            .show()
    }
}
