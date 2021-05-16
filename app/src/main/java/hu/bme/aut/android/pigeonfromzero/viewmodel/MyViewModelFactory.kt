package hu.bme.aut.android.pigeonfromzero.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MyViewModelFactory(private val someString: String): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = DetailsViewModel(someString) as T
}

class ChangeViewModelFactory(): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = ChangeViewModel() as T
}