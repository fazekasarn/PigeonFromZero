package hu.bme.aut.android.pigeonfromzero.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.pigeonfromzero.PigeonApplication
import hu.bme.aut.android.pigeonfromzero.data.DadWithChildrenNoRoom
import hu.bme.aut.android.pigeonfromzero.data.MomWithChildrenNoRoom
import hu.bme.aut.android.pigeonfromzero.model.Pigeon
import hu.bme.aut.android.pigeonfromzero.repository.Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailsViewModel(choosenPigeonId: String) : ViewModel() {
    private val repository: Repository

    var choosenPigeon: LiveData<Pigeon>
    val allPigeons: LiveData<List<Pigeon>>
    val allDadsWithChildren: LiveData<List<DadWithChildrenNoRoom>>
    val allMomsWithChildren: LiveData<List<MomWithChildrenNoRoom>>
    var firstGenParents = ArrayList<Pigeon?>()
    var secondGenParents = ArrayList<Pigeon?>()
    var thirdGenParents = ArrayList<Pigeon?>()
    var fourthGenParents = ArrayList<Pigeon?>()

    init {
        val pigeonDao = PigeonApplication.pigeonDatabase.pigeonDao()
        repository = Repository(pigeonDao)
        allDadsWithChildren = getDadsWithChildren()
        allMomsWithChildren = getMomsWithChildren()
        choosenPigeon = repository.getPigeonById(choosenPigeonId)
        allPigeons = repository.findAllPigeons()
    }

    fun getDadsWithChildren(): LiveData<List<DadWithChildrenNoRoom>> {
        return repository.getDadsWithChildren()
    }

    fun getMomsWithChildren(): LiveData<List<MomWithChildrenNoRoom>> {
        return repository.getMomsWithChildren()
    }
}