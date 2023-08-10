package com.gamecodeschool.cintent.database

import android.content.Context
import androidx.room.Room
import com.gamecodeschool.cintent.Crime
import java.util.*

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {

    private val database : CrimeDatabase =
        //создает конкретную
        //реализацию вашего абстрактного класса CrimeDatabase
        Room.databaseBuilder(
            //Сначала ему нужен объект
            //Context, так как база данных обращается к файловой системе.
            //Контекст приложения нужно передавать, так как синглтон,
            //скорее всего, существует дольше, чем любой из ваших классов
            //activity
            context.applicationContext,
            //класс базы данных, которую Room
            //должен создать
            CrimeDatabase::class.java, //java?
            //имя файла базы данных, которую
            //создаст Room
            DATABASE_NAME
        ).build()
    private val crimeDao = database.crimeDao()

    fun getCrimes(): List<Crime> = crimeDao.getCrimes()
    fun getCrime(id: UUID): Crime? = crimeDao.getCrime(id)

    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}