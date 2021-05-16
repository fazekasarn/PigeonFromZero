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

class DetailsViewModel(choosenPigeonId: String) : ViewModel() {
    private val repository: Repository

    val basic = "VALAMI RANDOM"
    var choosenPigeon: LiveData<Pigeon>
    val allPigeons: LiveData<List<Pigeon>>
    val allDadsWithChildren: LiveData<List<DadWithChildrenNoRoom>>
    val allMomsWithChildren: LiveData<List<MomWithChildrenNoRoom>>
    var firstGenParents: List<Pigeon?> = emptyList()

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

    fun refreshFirstGenFamily() {
        var dadFound = false
        var momFound = false
        val tempList = emptyList<Pigeon?>().toMutableList()
        if (allDadsWithChildren.value != null)
            for (item in allDadsWithChildren.value!!) {
                if (item.children.contains(choosenPigeon.value)) {
                    tempList.add(item.pigeon)
                    dadFound = true
                }
            }
        if (!dadFound) {
            tempList.add(null)
        }
        if (allMomsWithChildren.value != null)
        for (item in allMomsWithChildren.value!!) {
            if (item.children.contains(choosenPigeon.value)) {
                tempList.add(item.pigeon)
                momFound = true
            }
        }
        if (!momFound) {
            tempList.add(null)
        }
        firstGenParents = tempList.toList()
    }
}