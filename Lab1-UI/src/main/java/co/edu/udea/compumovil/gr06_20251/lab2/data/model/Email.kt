package co.edu.udea.compumovil.gr06_20251.lab2.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "emails")
@JsonClass(generateAdapter = true)
data class Email(
        @PrimaryKey val id: String,
        @ColumnInfo(name = "sender_name") val senderName: String,
        @ColumnInfo(name = "sender_email") val senderEmail: String,
        val subject: String,
        val body: String,
        @ColumnInfo(name = "created_at") val createdAt: String,
        @ColumnInfo(name = "is_read") val isRead: Boolean = false,
        @ColumnInfo(name = "is_starred") val isStarred: Boolean = false
)
