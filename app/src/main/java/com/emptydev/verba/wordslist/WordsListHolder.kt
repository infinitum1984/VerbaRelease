package com.emptydev.verba.wordslist

import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.emptydev.verba.R
import com.emptydev.verba.database.Words

class WordsListHolder(val view : View):RecyclerView.ViewHolder(view){
    enum class Action{DELETE,PLAY}
    val progressBar:ProgressBar=view.findViewById(R.id.words_progress)
    val progressText:TextView=view.findViewById(R.id.tv_progress)
    val numWords:TextView=view.findViewById(R.id.num_words)
    val nameWords:TextView=view.findViewById(R.id.words_name)
    val playButton:ImageButton=view.findViewById(R.id.playButton)

    val mainView: ConstraintLayout=view.findViewById(R.id.main_view)
    fun bind(item: Words,itemClick:(Long)->Unit,onOptions:(Long,Action)->Unit){
        numWords.text="${view.context!!.getString(R.string.words)}: ${item.numWords}"
        nameWords.text="${item.name}"
        mainView.isLongClickable=true
        mainView.setTag(item.wordId)

        view.setOnClickListener {
            itemClick.invoke(item.wordId)
        }
        view.setOnLongClickListener {
            Log.d("D_WordsListHolder","bind: long click");
            val popupMenu = PopupMenu(mainView.context, mainView)
            popupMenu.inflate(R.menu.context_menu_words)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_delete-> onOptions.invoke(item.wordId,Action.DELETE)
                }
                return@setOnMenuItemClickListener false
            }
            popupMenu.show()
            return@setOnLongClickListener true
        }
        view.tag=item.wordId
        progressBar.progress=item.lastResultPrc
        progressText.text="${item.lastResultPrc}%"
        playButton.setOnClickListener {
            onOptions.invoke(item.wordId,Action.PLAY)
        }
    }




}
