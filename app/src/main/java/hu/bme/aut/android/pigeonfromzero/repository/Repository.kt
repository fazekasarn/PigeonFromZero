package hu.bme.aut.android.pigeonfromzero.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.Room
import com.google.android.play.core.splitinstall.c
import hu.bme.aut.android.pigeonfromzero.data.*
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

    fun findPigeonById(id :String): Pigeon? {
        val pidgey = pigeonDao.findPigeonById(id)
        if (pidgey==null){
            return pidgey}
        else
            return pidgey.toDomainModel()
    }

    fun getPigeonById(id :String) : LiveData<Pigeon>{
        return pigeonDao.getPigeonById(id)
            .map {
                roomPigeons-> roomPigeons.toDomainModel()
            }
    }

    fun getIdBySex(sex :Pigeon.Sex) :LiveData<List<String>>{
        return pigeonDao.getIdBySex(sex)
    }

    suspend fun insert(pigeon: Pigeon) = withContext(Dispatchers.IO) {
        pigeonDao.insertPigeon(pigeon.toRoomModel())
    }

    suspend fun delete(pigeon: Pigeon) = withContext(Dispatchers.IO){
        val roomPigeon = pigeonDao.findPigeonById(pigeon.pigeonId) ?: return@withContext
        pigeonDao.deletePigeon(roomPigeon)
    }

    suspend fun update(pigeon :Pigeon) = withContext(Dispatchers.IO){
        pigeonDao.updatePigeon(pigeon.toRoomModel())
    }

    fun getDadsWithChildren() :LiveData<List<DadWithChildrenNoRoom>>{
        return pigeonDao.getDadsWithChildren().map {roomPigeons ->
            roomPigeons.map {roomPigeon ->
                roomPigeon.toDomainModel() }
        }
    }

    fun getMomsWithChildren() : LiveData<List<MomWithChildrenNoRoom>> {
        return pigeonDao.getMomsWithChildren().map {roomPigeons ->
            roomPigeons.map {roomPigeon ->
                roomPigeon.toDomainModel() }
        }
    }

    private fun RoomPigeon.toDomainModel(): Pigeon {
        return Pigeon(
            pigeonId = pigeonId,
            name = name,
            birth = birth,
            sex = sex,
            scores = scores,
            dadId = dadId,
            momId = momId
        )
    }

    private fun Pigeon.toRoomModel(): RoomPigeon {
        return RoomPigeon(
            pigeonId = pigeonId,
            name = name,
            birth = birth,
            sex = sex,
            scores = scores,
            dadId = dadId,
            momId = momId
        )
    }

    private fun DadWithChildren.toDomainModel() : DadWithChildrenNoRoom {
        return DadWithChildrenNoRoom(
            pigeon = pigeon.toDomainModel(),
            children = children.map {roomPigeon ->
                roomPigeon.toDomainModel() }
        )
    }

    private fun MomWithChildren.toDomainModel() : MomWithChildrenNoRoom {
        return MomWithChildrenNoRoom(
            pigeon = pigeon.toDomainModel(),
            children = children.map {roomPigeon ->
                roomPigeon.toDomainModel() }
        )
    }
}