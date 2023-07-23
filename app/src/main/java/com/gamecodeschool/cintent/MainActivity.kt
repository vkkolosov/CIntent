package com.gamecodeschool.cintent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var crime: Crime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val fragment = CrimeListFragment.newInstance() //Этот код создает и закрепляет транзакцию фрагмента
            supportFragmentManager
                .beginTransaction() //«Создать новую транзакцию фрагмента, включить в нее одну операцию add, а затем закрепить»
                .add(R.id.fragment_container, fragment) //помещаем в fragment_container экземпляр CrimeFragment
                .commit()
        }

        crime = Crime()
    }

}