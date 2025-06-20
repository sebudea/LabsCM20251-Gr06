package co.edu.udea.compumovil.gr06_20251.lab2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.edu.udea.compumovil.gr06_20251.lab2.data.model.Email

@Database(entities = [Email::class], version = 2)
abstract class EmailDatabase : RoomDatabase() {
    abstract fun emailDao(): EmailDao

    companion object {
        @Volatile private var INSTANCE: EmailDatabase? = null

        fun getDatabase(context: Context): EmailDatabase {
            return INSTANCE
                    ?: synchronized(this) {
                        val instance =
                                Room.databaseBuilder(
                                                context.applicationContext,
                                                EmailDatabase::class.java,
                                                "email_database"
                                        )
                                        .fallbackToDestructiveMigration()
                                        .build()
                        INSTANCE = instance
                        instance
                    }
        }
    }
}
