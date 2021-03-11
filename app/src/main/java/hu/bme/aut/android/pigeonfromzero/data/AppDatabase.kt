package hu.bme.aut.android.pigeonfromzero.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Pigeon::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pigeonDao(): PigeonDAO

    companion object {
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "pigeon.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}