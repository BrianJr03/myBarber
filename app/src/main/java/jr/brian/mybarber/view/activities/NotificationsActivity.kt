package jr.brian.mybarber.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import jr.brian.mybarber.databinding.ActivityNotificationsBinding
import jr.brian.mybarber.model.data.Notification
import jr.brian.mybarber.model.util.showSnackbar
import jr.brian.mybarber.view.adapters.NotificationAdapter

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
//                    showSnackbar(
//                        "Notification Deleted",
//                        binding.notificationRecyclerView,
//                        Snackbar.LENGTH_SHORT
//                    )
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
//                    showSnackbar(
//                        "Notification Archived",
//                        binding.notificationRecyclerView,
//                        Snackbar.LENGTH_SHORT
//                    )
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
            for (i in 1..15) {
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