package com.gamecodeschool.cintent.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gamecodeschool.cintent.Crime

//Аннотация @Database сообщает Room о том, что этот класс
//представляет собой базу данных в приложении. Самой
//аннотации требуется два параметра. Первый параметр — это
//список классов-сущностей, который сообщает Room, какие
//использовать классы при создании и управлении таблицами
//для этой базы данных.
@Database(entities = [Crime::class],
    version = 2
)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase : RoomDatabase() {

    abstract fun crimeDao(): CrimeDao

}