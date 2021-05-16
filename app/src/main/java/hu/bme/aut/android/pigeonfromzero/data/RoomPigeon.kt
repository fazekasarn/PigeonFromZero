package hu.bme.aut.android.pigeonfromzero.data

import androidx.room.*
import hu.bme.aut.android.pigeonfromzero.model.Pigeon
import java.io.Serializable

@Entity(tableName = "pigeon")
data class RoomPigeon (
    @PrimaryKey(autoGenerate = false) var pigeonId :String,
    @ColumnInfo(name = "name") var name :String,
    @ColumnInfo(name = "birth") var birth :Int,
    @ColumnInfo(name = "sex") var sex :Pigeon.Sex,
    @ColumnInfo(name = "scores") var scores :String,
    @ColumnInfo(name = "dadId") var dadId :String?,
    @ColumnInfo(name = "momId") var momId :String?
) : Serializable

data class DadWithChildren(
    @Embedded val pigeon :RoomPigeon,
    @Relation(
        parentColumn = "pigeonId",
        entityColumn = "dadId"
    )
    val children : List<RoomPigeon>
)

data class DadWithChildrenNoRoom(
    val pigeon :Pigeon,
    val children : List<Pigeon>
)

data class MomWithChildren(
    @Embedded val pigeon :RoomPigeon,
    @Relation(
        parentColumn = "pigeonId",
        entityColumn = "momId"
    )
    val children : List<RoomPigeon>
)

data class MomWithChildrenNoRoom(
    val pigeon :Pigeon,
    val children : List<Pigeon>
)

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