package com.gamecodeschool.cintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.gamecodeschool.cintent.Crime
import java.util.*

@Dao
interface CrimeDao {

    //Возвращая экземпляр LiveData из вашего класса DAO, вы
    //запускаете запрос в фоновом потоке.
    @Query("SELECT * FROM crime")
    fun getCrimes(): LiveData<List<Crime>>
    @Query("SELECT * FROM crime WHERE id=(:id)")
    fun getCrime(id: UUID): LiveData<Crime?>

}