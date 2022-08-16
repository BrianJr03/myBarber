package jr.brian.mybarber.model.data.roomdb

import androidx.room.*
import jr.brian.mybarber.model.data.notification.Noti

@Dao
interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotification(notification: Noti)

    @Query("SELECT * FROM notifications")
    fun getNotifications(): List<Noti>

    @Delete
    fun deleteNotification(notification: Noti)
}