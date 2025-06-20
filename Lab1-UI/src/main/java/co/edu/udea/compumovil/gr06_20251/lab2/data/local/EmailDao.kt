package co.edu.udea.compumovil.gr06_20251.lab2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import co.edu.udea.compumovil.gr06_20251.lab2.data.model.Email
import kotlinx.coroutines.flow.Flow

@Dao
interface EmailDao {
    @Query("SELECT * FROM emails ORDER BY created_at DESC")
    fun getAllEmails(): Flow<List<Email>>

    @Query("SELECT * FROM emails WHERE is_starred = 1 ORDER BY created_at DESC")
    fun getStarredEmails(): Flow<List<Email>>

    @Query("SELECT * FROM emails WHERE is_read = 0 ORDER BY created_at DESC")
    fun getUnreadEmails(): Flow<List<Email>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmails(emails: List<Email>)

    @Update
    suspend fun updateEmail(email: Email)

    @Query("SELECT * FROM emails WHERE subject LIKE '%' || :query || '%' OR body LIKE '%' || :query || '%'")
    fun searchEmails(query: String): Flow<List<Email>>
} 