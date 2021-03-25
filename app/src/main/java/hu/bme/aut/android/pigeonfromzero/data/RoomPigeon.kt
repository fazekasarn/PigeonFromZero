package hu.bme.aut.android.pigeonfromzero.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import hu.bme.aut.android.pigeonfromzero.model.Pigeon
import java.io.Serializable

@Entity(tableName = "pigeon")
data class RoomPigeon (
    @PrimaryKey(autoGenerate = true) var pigeonId: Int = 0,
    @ColumnInfo(name = "number") var number :String,
    @ColumnInfo(name = "name") var name :String,
    @ColumnInfo(name = "birth") var birth :Int,
    @ColumnInfo(name = "sex") var sex :Pigeon.Sex
) : Serializable

class PigeonTypeConverter {

    companion object {
        const val MALE = "MALE"
        const val FEMALE = "FEMALE"
        const val UNKNOWN = "UNKNOWN"
    }

    @TypeConverter
    fun toSex(value: String?): Pigeon.Sex {
        return when (value) {
            MALE -> Pigeon.Sex.MALE
            FEMALE -> Pigeon.Sex.FEMALE
            UNKNOWN -> Pigeon.Sex.UNKNOWN
            else -> Pigeon.Sex.UNKNOWN
        }
    }

    @TypeConverter
    fun toString(enumValue: Pigeon.Sex): String? {
        return enumValue.name
    }

}