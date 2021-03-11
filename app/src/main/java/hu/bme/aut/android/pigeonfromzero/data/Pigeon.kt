package hu.bme.aut.android.pigeonfromzero.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pigeon")
data class Pigeon (
    @PrimaryKey(autoGenerate = true) var recipeId: Long?,
    @ColumnInfo(name = "number") var number :String,
    @ColumnInfo(name = "name") var name :String,
    @ColumnInfo(name = "birth") var birth :Int,
    @ColumnInfo(name = "sex") var sex :String
) : Serializable