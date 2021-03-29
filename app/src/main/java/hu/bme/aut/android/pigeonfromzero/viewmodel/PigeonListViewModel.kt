package hu.bme.aut.android.pigeonfromzero.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.pigeonfromzero.PigeonApplication
import hu.bme.aut.android.pigeonfromzero.model.Pigeon
import hu.bme.aut.android.pigeonfromzero.repository.Repository
import kotlinx.coroutines.launch

class PigeonListViewModel : ViewModel() {

    private val repository: Repository

    val allPigeons: LiveData<List<Pigeon>>

    init {
        val pigeonDao = PigeonApplication.pigeonDatabase.pigeonDao()
        repository = Repository(pigeonDao)
        allPigeons = repository.findAllPigeons()
    }

    fun insert(pigeon: Pigeon) = viewModelScope.launch {
        repository.insert(pigeon)
    }

    fun delete(pigeon: Pigeon) = viewModelScope.launch {
        repository.delete(pigeon)
    }
}