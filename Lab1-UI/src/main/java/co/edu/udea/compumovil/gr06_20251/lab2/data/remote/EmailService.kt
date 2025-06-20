package co.edu.udea.compumovil.gr06_20251.lab2.data.remote

import co.edu.udea.compumovil.gr06_20251.lab2.data.model.Email
import retrofit2.http.GET
import retrofit2.http.Query

interface EmailService {
    @GET("emails")
    suspend fun getEmails(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): List<Email>

    @GET("emails/unread")
    suspend fun getUnreadEmails(): List<Email>
} 