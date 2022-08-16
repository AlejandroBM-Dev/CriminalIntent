package com.raiserdev.criminalintent.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.raiserdev.criminalintent.CrimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class CrimeDetailViewModel(crimeId: UUID) : ViewModel() {
    private val crimeRepository = CrimeRepository.get()

    private val _crime : MutableStateFlow<Crime?> = MutableStateFlow(null)
    val crime : StateFlow<Crime?> = _crime.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO){
            _crime.value = crimeRepository.getCrime(crimeId)
        }
    }

    class CrimeDetailViewModelFactory(
        private val crimeId: UUID
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CrimeDetailViewModel(crimeId) as T
        }
    }

    fun updateCrime(onUpdate: (Crime) -> Crime){
        _crime.update { oldCrime ->
            oldCrime?.let { onUpdate(it)}
        }
    }

    override fun onCleared() {
        super.onCleared()
        crime.value?.let { crimeRepository.updateCrime(it) }

    }
}