package co.edu.udea.compumovil.gr06_20251.lab2.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import co.edu.udea.compumovil.gr06_20251.lab2.data.local.EmailDatabase
import co.edu.udea.compumovil.gr06_20251.lab2.data.remote.EmailService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class EmailSyncWorker(context: Context, workerParams: WorkerParameters) :
        CoroutineWorker(context, workerParams) {

    private val loggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private val okHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val emailService =
            Retrofit.Builder()
                    .baseUrl("https://685503706a6ef0ed6630e738.mockapi.io/emails-api/")
                    .client(okHttpClient)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
                    .create(EmailService::class.java)

    private val emailDao = EmailDatabase.getDatabase(context).emailDao()

    override suspend fun doWork(): Result =
            withContext(Dispatchers.IO) {
                try {
                    Log.d(TAG, "Iniciando sincronización de correos...")

                    // Obtener emails del servidor
                    Log.d(TAG, "Obteniendo correos del servidor...")
                    val emails = emailService.getEmails()
                    Log.d(TAG, "Correos obtenidos del servidor: ${emails.size}")

                    // Guardar en la base de datos local
                    Log.d(TAG, "Guardando correos en la base de datos local...")
                    emailDao.insertEmails(emails)

                    Log.d(TAG, "Sincronización exitosa: ${emails.size} correos")
                    Result.success()
                } catch (e: Exception) {
                    Log.e(TAG, "Error durante la sincronización", e)
                    Log.e(TAG, "Detalles del error: ${e.message}")
                    e.printStackTrace()
                    Result.retry()
                }
            }

    companion object {
        const val WORK_NAME = "email_sync_worker"
        private const val TAG = "EmailSyncWorker"
    }
}
