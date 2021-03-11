package hu.bme.aut.android.pigeonfromzero.data

import androidx.room.*

@Dao
interface PigeonDAO {

    @Query("SELECT * FROM pigeon")
    fun findAllPigeons(): List<Pigeon>

    @Insert
    fun insertPigeon(pigeon: Pigeon) :Long

    @Delete
    fun deletePigeon(pigeon: Pigeon)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePigeon(pigeon: Pigeon)
}