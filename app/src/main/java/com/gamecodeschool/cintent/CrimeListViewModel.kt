package com.gamecodeschool.cintent

import androidx.lifecycle.ViewModel
import com.gamecodeschool.cintent.database.CrimeRepository

class CrimeListViewModel : ViewModel() {
    val crimes = mutableListOf<Crime>()

    init {
        for (i in 0 until 100) {
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.isSolved = i % 2 == 0
            crime.requresPolice = i % 5 == 0
            crimes += crime
        }
    }
    //болванка БД не отзывается
    //private val crimeRepository = CrimeRepository.get()
    //val crimeListLiveData = crimeRepository.getCrimes()

}