package hu.bme.aut.android.pigeonfromzero.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.bme.aut.android.pigeonfromzero.data.PigeonDAO
import hu.bme.aut.android.pigeonfromzero.data.RoomPigeon
import hu.bme.aut.android.pigeonfromzero.model.Pigeon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class Repository(private val pigeonDao: PigeonDAO) {

    fun findAllPigeons(): LiveData<List<Pigeon>> {
        return pigeonDao.findAllPigeons()
            .map {roomPigeons ->
                roomPigeons.map {roomPigeon ->
                    roomPigeon.toDomainModel() }
            }
    }

    suspend fun insert(pigeon: Pigeon) = withContext(Dispatchers.IO) {
        pigeonDao.insertPigeon(pigeon.toRoomModel())
    }

    suspend fun delete(pigeon: Pigeon) = withContext(Dispatchers.IO){
        val roomPigeon = pigeonDao.findPigeonById(pigeon.pigeonId) ?: return@withContext
        pigeonDao.deletePigeon(roomPigeon)
    }

    private fun RoomPigeon.toDomainModel(): Pigeon {
        return Pigeon(
            pigeonId = pigeonId,
            name = name,
            number = number,
            birth = birth,
            sex = sex
        )
    }

    private fun Pigeon.toRoomModel(): RoomPigeon {
        return RoomPigeon(
            name = name,
            number = number,
            birth = birth,
            sex = sex
        )
    }
}