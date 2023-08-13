package com.gamecodeschool.cintent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.*

private const val TAG = "MainActivity"

//Activity является подклассом Context
class MainActivity : AppCompatActivity(), CrimeListFragment.Callbacks {

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

    override fun onCrimeSelected(crimeId: UUID)
    {
        Log.d(TAG, "MainActivity.onCrimeSelected: $crimeId")
        val fragment = CrimeFragment()
        //Функция
        //FragmentTransaction.replace(Int,Fragment) заменяет
        //фрагмент, размещенный в activity (в контейнере с указанным
        //целочисленным идентификатором ресурса)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            //при нажатии пользователем кнопки «Назад»
            //транзакция будет обращена
            .addToBackStack(null) //название состояния обратного стека
            .commit()
    }

}