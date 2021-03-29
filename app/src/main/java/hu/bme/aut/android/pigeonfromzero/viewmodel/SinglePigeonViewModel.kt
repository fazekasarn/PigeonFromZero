package hu.bme.aut.android.pigeonfromzero.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.pigeonfromzero.PigeonApplication
import hu.bme.aut.android.pigeonfromzero.model.Pigeon
import hu.bme.aut.android.pigeonfromzero.repository.Repository
import kotlinx.coroutines.launch

class SinglePigeonViewModel : ViewModel(){
    private val repository: Repository

    init {
        val pigeonDao = PigeonApplication.pigeonDatabase.pigeonDao()
        repository = Repository(pigeonDao)
    }

    fun update(pigeon: Pigeon) = viewModelScope.launch {
        repository.update(pigeon)
    }

    fun getPigeonById(id :Int) : LiveData<Pigeon> {
        return repository.getPigeonById(id)
    }

}