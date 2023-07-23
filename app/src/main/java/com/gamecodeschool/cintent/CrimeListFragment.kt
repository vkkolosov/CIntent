package com.gamecodeschool.cintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    private lateinit var crimeRecyclerView: RecyclerView
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    //создание Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
    }

    //создание View
    override fun onCreateView(
        inflater: LayoutInflater, //это класс, который умеет из содержимого layout-файла создать View-элемент
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context) //RecyclerView не отображает элементы на самом экране. Он передает эту задачу объекту LayoutManager.
        return view
    }

    //RecyclerView никогда не создает объекты View сам по
    //себе. Он всегда создает ViewHolder, которые выводят свои
    //itemView
    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
    }

    private inner class CrimeAdapter(var crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {
        //отвечает за
        //создание представления на дисплее
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }
        //отвечает за заполнение данного холдера
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.apply {
                titleTextView.text = crime.title
                dateTextView.text = crime.date.toString()
            }
        }
        //возвращает количество элементов в списке, отвечая на запрос утилизатора - он же RecyclerView
        override fun getItemCount() = crimes.size
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}