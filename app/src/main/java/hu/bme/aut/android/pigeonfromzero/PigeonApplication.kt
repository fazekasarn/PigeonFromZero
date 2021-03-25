package hu.bme.aut.android.pigeonfromzero

import android.app.Application
import androidx.room.Room
import hu.bme.aut.android.pigeonfromzero.data.PigeonDatabase

class PigeonApplication : Application() {

    companion object {
        lateinit var pigeonDatabase: PigeonDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        pigeonDatabase = Room.databaseBuilder(
            applicationContext,
            PigeonDatabase::class.java,
            "pigeon_database"
        ).fallbackToDestructiveMigration().build()
    }

}