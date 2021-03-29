package hu.bme.aut.android.pigeonfromzero.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PigeonDAO {

    @Query("SELECT * FROM pigeon")
    fun findAllPigeons(): LiveData<List<RoomPigeon>>

    @Query("SELECT * FROM pigeon WHERE pigeonId = :id")
    fun findPigeonById(id :Int?): RoomPigeon?

    @Query("SELECT * FROM pigeon WHERE pigeonId = :id")
    fun getPigeonById(id :Int?): LiveData<RoomPigeon>

    @Insert
    fun insertPigeon(pigeon: RoomPigeon) :Long

    @Delete
    fun deletePigeon(pigeon: RoomPigeon)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePigeon(pigeon: RoomPigeon)
}