package com.emptydev.verba.wordskit.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emptydev.verba.R
import com.emptydev.verba.core.data.model.WordsKit

class WordsListAdapter(val itemClick:(Long)->Unit, val itemLongCLick:(Long, WordsListHolder.Action)->Unit):RecyclerView.Adapter<WordsListHolder>() {
    private var dataList= listOf<WordsKit>()
    fun setData(list:List<WordsKit>){
        dataList=list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.word_item,parent,false)
        return WordsListHolder(view)
    }

    override fun onBindViewHolder(holder: WordsListHolder, position: Int) {

        holder.bind(dataList[position],itemClick, itemLongCLick)
    }

    override fun getItemCount()=dataList.size
}
