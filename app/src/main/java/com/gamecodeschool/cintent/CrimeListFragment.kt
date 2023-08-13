package com.gamecodeschool.cintent

import android.content.Context
import android.icu.text.DateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gamecodeschool.cintent.CrimeListFragment.Companion.newInstance
import java.util.*

private const val TAG = "CrimeListFragment"
//удалили  onCreate : Activity, непонятно почему
class CrimeListFragment : Fragment() {

    //Интерфейс обратного вызова позволяет передавать события
    //кликов из CrimeListFragment обратно на хост-activity
    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }

    private var callbacks: Callbacks? = null
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    //Activity является подклассом Context,
    //поэтому функция onAttach(...) передает в качестве
    //параметра объект Context, который более гибок
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    //создание View
    override fun onCreateView(
        inflater: LayoutInflater, //это класс, который умеет из содержимого layout-файла создать View-элемент
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        //RecyclerView не отображает элементы на самом экране. Он передает эту задачу объекту LayoutManager.
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter
        //updateUI() //пока тут
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI() //что тут, то там не сильно много разницы. onViewCreated сдеално, чтобы можно было ожидать в UI подгрузку с БД
        /*
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    Log.i(TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }
            })
         */

    }

    private fun updateUI() { //было private fun updateUI(crimes: List<Crime>)
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    //RecyclerView никогда не создает объекты View сам по
    //себе. Он всегда создает ViewHolder, которые выводят свои
    //itemView
    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var crime: Crime
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private var buttonCallPolice: Button = itemView.findViewById(R.id.crime_call)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        init {
            itemView.setOnClickListener(this)
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = DateFormat.getPatternInstance("EEEE,MMM dd, yyyy", Locale.getDefault()).format(this.crime.date)

            buttonCallPolice.visibility = if (crime.requresPolice) {
                View.VISIBLE //почему-то надо включать visibility
            } else {
                View.GONE
            }

            solvedImageView.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        override fun onClick(v: View?) {
            callbacks?.onCrimeSelected(crime.id)
            //Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class CrimeAdapter(var crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {
        //Когда создано достаточно объектов ViewHolder,
        //RecyclerView перестает вызывать
        //onCreateViewHolder(...). Вместо этого он экономит время
        //и память путем утилизации старых объектов ViewHolder и
        //передает их в onBindViewHolder(ViewHolder,Int).

        //отвечает за
        //создание представления на дисплее
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        //отвечает за заполнение данного холдера
        @RequiresApi(Build.VERSION_CODES.N)
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
            holder.itemViewType
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