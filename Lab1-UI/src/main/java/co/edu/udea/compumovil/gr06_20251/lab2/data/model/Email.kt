package co.edu.udea.compumovil.gr06_20251.lab2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

import androidx.room.ColumnInfo

@Entity(tableName = "emails")
@JsonClass(generateAdapter = true)
data class Email(
    @PrimaryKey
    val id: Long,

    @Json(name = "sender_name")
    @ColumnInfo(name = "sender_name")
    val senderName: String,

    @Json(name = "sender_email")
    @ColumnInfo(name = "sender_email")
    val senderEmail: String,

    val subject: String,
    val body: String,

    @Json(name = "created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: String,

    @Json(name = "is_read")
    @ColumnInfo(name = "is_read")
    val isRead: Boolean = false,

    @Json(name = "is_starred")
    @ColumnInfo(name = "is_starred")
    val isStarred: Boolean = false
)
