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
import jr.brian.mybarber.model.data.notification.Notification
import jr.brian.mybarber.view.adapters.home.NotificationAdapter

class NotificationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationsBinding
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var notifications: ArrayList<Notification>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDummyData()
        setAdapter()
        binding.apply {
            notiBackArrow.setOnClickListener {
                super.onBackPressed()
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
                    notifications.removeAt(pos)
                    notificationAdapter.notifyItemRemoved(pos)
                    Toast.makeText(
                        this@NotificationsActivity,
                        "Notification Deleted",
                        Toast.LENGTH_LONG
                    )
                        .show()
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
                    notifications.removeAt(pos)
                    notificationAdapter.notifyItemRemoved(pos)
                    Toast.makeText(
                        this@NotificationsActivity,
                        "Notification Deleted",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }).attachToRecyclerView(notificationRecyclerView)
        }
    }

    private fun initDummyData() {
        notifications = ArrayList()
        notifications.apply {
            for (i in 1..7) {
                add(
                    Notification(
                        body = "Notification $i",
                        date = "7/$i/2022",
                    )
                )
            }
        }
    }
}