package jr.brian.mybarber.model.data.notification

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class Noti(val title: String, @PrimaryKey val body: String, val date: String)