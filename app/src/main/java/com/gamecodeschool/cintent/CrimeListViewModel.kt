package com.gamecodeschool.cintent

import androidx.lifecycle.ViewModel
import com.gamecodeschool.cintent.database.CrimeRepository

class CrimeListViewModel : ViewModel() {

    //болванка БД не отзывается
    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()

}