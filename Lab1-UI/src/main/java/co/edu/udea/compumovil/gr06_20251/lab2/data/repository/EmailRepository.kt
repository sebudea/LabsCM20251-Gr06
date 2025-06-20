package co.edu.udea.compumovil.gr06_20251.lab2.data.repository

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import co.edu.udea.compumovil.gr06_20251.lab2.data.local.EmailDao
import co.edu.udea.compumovil.gr06_20251.lab2.data.model.Email
import co.edu.udea.compumovil.gr06_20251.lab2.worker.EmailSyncWorker
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class EmailRepository(
    private val emailDao: EmailDao,
    private val context: Context
) {
    val allEmails: Flow<List<Email>> = emailDao.getAllEmails()
    val starredEmails: Flow<List<Email>> = emailDao.getStarredEmails()
    val unreadEmails: Flow<List<Email>> = emailDao.getUnreadEmails()

    suspend fun toggleEmailStar(email: Email) {
        emailDao.updateEmail(email.copy(isStarred = !email.isStarred))
    }

    suspend fun markEmailAsRead(email: Email) {
        emailDao.updateEmail(email.copy(isRead = true))
    }

    fun searchEmails(query: String): Flow<List<Email>> {
        return emailDao.searchEmails(query)
    }

    fun startEmailSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<EmailSyncWorker>(
            15, TimeUnit.MINUTES, // Sincronizar cada 15 minutos
            5, TimeUnit.MINUTES  // Flexibilidad de 5 minutos
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            EmailSyncWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP, // Mantener el trabajo existente si ya está programado
            syncRequest
        )
    }
} 