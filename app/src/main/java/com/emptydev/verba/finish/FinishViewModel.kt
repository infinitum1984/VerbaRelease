package com.emptydev.verba.finish

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emptydev.verba.database.WordsDatabaseDao

import com.emptydev.verba.stringToPairArray
import kotlinx.coroutines.launch

class FinishViewModel(val wordKey: Long, val database: WordsDatabaseDao,val numCorrect:Int,val numException:Int) : ViewModel() {
    val numMistakes=MutableLiveData<Pair<Int,Int>>()
    val goodPercent=MutableLiveData<Double>()
    val onShowMistakes=MutableLiveData<Boolean>()
    lateinit var mistakes:String
    init {
        viewModelScope.launch {
            val wordsObject=database.get(wordKey)
            val allWords= stringToPairArray(wordsObject.words)
            val mistakesArray= stringToPairArray(wordsObject.lastMistakes)
            Log.d("D_FinishViewModel",": ${wordsObject.lastMistakes}");
            mistakes=wordsObject.lastMistakes
            numMistakes.value= Pair(numException,numCorrect)
            goodPercent.value=100-((numException.toDouble()*100)/numCorrect.toDouble())
        }
    }
    fun showMistakes(){
        onShowMistakes.value=true
    }
}
