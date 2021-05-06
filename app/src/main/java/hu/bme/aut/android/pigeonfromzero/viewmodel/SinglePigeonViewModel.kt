package hu.bme.aut.android.pigeonfromzero.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.pigeonfromzero.PigeonApplication
import hu.bme.aut.android.pigeonfromzero.data.DadWithChildren
import hu.bme.aut.android.pigeonfromzero.data.MomWithChildren
import hu.bme.aut.android.pigeonfromzero.model.Pigeon
import hu.bme.aut.android.pigeonfromzero.repository.Repository
import kotlinx.coroutines.launch

class SinglePigeonViewModel : ViewModel(){
    private val repository: Repository

    val allPigeons: LiveData<List<Pigeon>>
    val maleSpinnerData : LiveData<List<String>>
    val femaleSpinnerData : LiveData<List<String>>
    val allDadsWithChildren :LiveData<List<DadWithChildren>>
    val allMomsWithChildren :LiveData<List<MomWithChildren>>

    init {
        val pigeonDao = PigeonApplication.pigeonDatabase.pigeonDao()
        repository = Repository(pigeonDao)
        allPigeons = repository.findAllPigeons()
        maleSpinnerData = getIdBySex(Pigeon.Sex.MALE)
        femaleSpinnerData = getIdBySex(Pigeon.Sex.FEMALE)
        allDadsWithChildren = getDadsWithChildren()
        allMomsWithChildren = getMomsWithChildren()
    }

    fun insert(pigeon: Pigeon) = viewModelScope.launch {
        repository.insert(pigeon)
    }

    fun update(pigeon: Pigeon) = viewModelScope.launch {
        repository.update(pigeon)
    }

    fun getPigeonById(id :String) : LiveData<Pigeon> {
        return repository.getPigeonById(id)
    }

    fun getIdBySex(sex :Pigeon.Sex) : LiveData<List<String>>{
        return repository.getIdBySex(sex)
    }

    fun getDadsWithChildren() : LiveData<List<DadWithChildren>>{
        return repository.getDadsWithChildren()
    }

    fun getMomsWithChildren() : LiveData<List<MomWithChildren>>{
        return repository.getMomsWithChildren()
    }

}