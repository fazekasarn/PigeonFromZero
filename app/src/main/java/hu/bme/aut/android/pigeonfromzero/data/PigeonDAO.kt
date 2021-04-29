package hu.bme.aut.android.pigeonfromzero.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.android.material.transition.MaterialContainerTransform

import hu.bme.aut.android.pigeonfromzero.model.Pigeon

@Dao
interface PigeonDAO {

    @Query("SELECT * FROM pigeon")
    fun findAllPigeons(): LiveData<List<RoomPigeon>>

    @Query("SELECT * FROM pigeon WHERE pigeonId = :id")
    fun findPigeonById(id :String?): RoomPigeon?

    @Query("SELECT * FROM pigeon WHERE pigeonId = :id")
    fun getPigeonById(id :String?): LiveData<RoomPigeon>

    @Insert
    fun insertPigeon(pigeon: RoomPigeon) :Long

    @Delete
    fun deletePigeon(pigeon: RoomPigeon)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePigeon(pigeon: RoomPigeon)

    @Query("SELECT pigeonId FROM pigeon WHERE sex= :s")
    fun getIdBySex(s :Pigeon.Sex)  :LiveData<List<String>>

    @Transaction
    @Query("SELECT * FROM pigeon")
    fun getDadWithChildren() :LiveData<List<DadWithChildren>>
}