package com.gamecodeschool.cintent.database

import android.app.Application

//Как и Activity.onCreate(...), функция
//Application.onCreate() вызывается системой, когда
//приложение впервые загружается в память.

//Регистрируется в AndroidManifest.xml
class CriminalIntentApplication : Application() {
    //Единственная функция жизненного цикла, которую вам надо
    //переопределить
    override fun onCreate() {
        super.onCreate()
        //Сейчас нам нужно передать экземпляр приложения в
        //репозиторий в качестве объекта Context
        CrimeRepository.initialize(this)
    }

}