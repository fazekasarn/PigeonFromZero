package hu.bme.aut.android.pigeonfromzero.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 3,
    exportSchema = false,
    entities = [RoomPigeon::class]
)
@TypeConverters(
    PigeonTypeConverter::class
)
abstract class PigeonDatabase : RoomDatabase() {

    abstract fun pigeonDao(): PigeonDAO

}