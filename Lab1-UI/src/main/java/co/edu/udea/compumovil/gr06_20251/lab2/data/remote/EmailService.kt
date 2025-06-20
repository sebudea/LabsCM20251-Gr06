package co.edu.udea.compumovil.gr06_20251.lab2.data.remote

import co.edu.udea.compumovil.gr06_20251.lab2.data.model.Email
import retrofit2.http.GET

interface EmailService {
    @GET("emails") suspend fun getEmails(): List<Email>

    @GET("emails/unread") suspend fun getUnreadEmails(): List<Email>
}
