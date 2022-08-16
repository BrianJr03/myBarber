package jr.brian.mybarber.view.activities.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ActivityNotificationsBinding
import jr.brian.mybarber.model.data.notification.Noti
import jr.brian.mybarber.model.data.roomdb.AppDatabase
import jr.brian.mybarber.view.adapters.home.NotificationAdapter

class NotificationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationsBinding
    private lateinit var notificationAdapter: NotificationAdapter
    private var notifications = ArrayList<Noti>()
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        appDatabase = AppDatabase.getInstance(applicationContext)!!
        setContentView(binding.root)
        fetchNotifications()
        setAdapter()
        binding.apply {
            notiBackArrow.setOnClickListener {
                super.onBackPressed()
                overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
                )
                finish()
            }
            if (notifications.isEmpty()) {
                noNotiMsgGroup.visibility = View.VISIBLE
                notiBell.setImageResource(R.drawable.notifications_none_36)
                notiCount.text = notifications.size.toString()
            } else {
                notiCount.text = notifications.size.toString()
            }
        }
    }

    private fun fetchNotifications() {
        notifications.apply {
            for (i in appDatabase.dao().getNotifications().asReversed()) {
                add(i)
            }
        }
    }

    private fun setAdapter() {
        notificationAdapter = NotificationAdapter(this, notifications)
        binding.apply {
            notificationRecyclerView.layoutManager = LinearLayoutManager(this@NotificationsActivity)
            notificationRecyclerView.adapter = notificationAdapter

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val pos = viewHolder.adapterPosition
                    handleDelete(pos)
                }
            }).attachToRecyclerView(notificationRecyclerView)

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val pos = viewHolder.adapterPosition
                    handleDelete(pos)
                }
            }).attachToRecyclerView(notificationRecyclerView)
        }
    }

    private fun handleDelete(pos: Int) {
        appDatabase.dao().deleteNotification(notifications[pos])
        notifications.removeAt(pos)
        notificationAdapter.notifyItemRemoved(pos)
        when {
            notifications.isEmpty() -> binding.apply {
                binding.notiBell.setImageResource(R.drawable.notifications_none_36)
                errorIcon.visibility = View.VISIBLE
                errorText.visibility = View.VISIBLE
            }
        }
        binding.notiCount.text = notifications.size.toString()
        Toast.makeText(
            this@NotificationsActivity,
            "Notification Deleted",
            Toast.LENGTH_LONG
        )
            .show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_left
        )
    }
}