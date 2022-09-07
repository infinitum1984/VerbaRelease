package com.emptydev.verba.training.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emptydev.verba.*
import com.emptydev.verba.core.data.model.WordsSet
import com.emptydev.verba.core.data.database.WordsDatabaseDao

import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class TrainingViewModel(val wordsKey:Long, val trainingType: TrainingType, val database:WordsDatabaseDao ) : ViewModel() {
    // TODO: Implement the ViewModel
    lateinit var wordsSetObject: WordsSet

    //private val _Words= MutableLiveData<Map<String,String>>()
    val Words: ArrayList<Pair<String,String>> = ArrayList()

    val Mistakes= ArrayList<Pair<String,String>>()


    private var curStateVerify=true

    private val _curPairWords=MutableLiveData<Pair<String,String>>()
    val curPairWords:LiveData<Pair<String,String>> get() = _curPairWords

    private val _onShowMistake=MutableLiveData<Boolean>()
    val onShowMistake:LiveData<Boolean> get() = _onShowMistake

    private val _onChangeBtnText=MutableLiveData<Int>()
    val onChangeBtnText:LiveData<Int> get() = _onChangeBtnText

    private val _onVerifyWords=MutableLiveData<Boolean>()
    val onVerifyWords:LiveData<Boolean> get() = _onVerifyWords

    private val _mistakes=MutableLiveData<List<Pair<String,String>>>()
    val mistakes:LiveData<List<Pair<String,String>>> get() = _mistakes

    private val _curWordPairNum=MutableLiveData<Pair<Int,Int>>()
    val curWordPairNum:LiveData<Pair<Int,Int>> get() = _curWordPairNum

    private val _toFinish=MutableLiveData<Boolean>()
    val toFinish:LiveData<Boolean> get() = _toFinish

    private val _nameWordsList=MutableLiveData<String>()
    val nameWordsList:LiveData<String> get() = _nameWordsList
    private var curWordId=0
    init {
        Log.d("D_TrainingViewModel",": $trainingType");
        viewModelScope.launch {
            wordsSetObject=database.get(wordsKey)
            var map:List<Pair<String,String>>

            when(trainingType){

                TrainingType.BASIC -> {
                    map= stringToPairArray(wordsSetObject.words)

                }
                TrainingType.LAST_MISTAKE -> map= stringToPairArray(wordsSetObject.lastMistakes)
                TrainingType.REVERS ->{
                    map= stringToPairArrayRevers(wordsSetObject.words)
                }
                TrainingType.REVERS_LAST_MISTAKE ->{
                    map= stringToPairArrayRevers(wordsSetObject.lastMistakes)
                }

            }

            for (it in map){
                Words.add(it)
            }
            Words.shuffle()
            _curWordPairNum.value= Pair(1,Words.size)
            _mistakes.value=Mistakes
            _curPairWords.value= Words[curWordId]
            _nameWordsList.value=wordsSetObject.name
        }



    }

    fun onNextClick(){
        Log.d("D_TrainingViewModel","onNextClick: ");
        if (curStateVerify==true){
            _onVerifyWords.value=true
            curStateVerify=false
        }else{
            curStateVerify=true
            _onChangeBtnText.value=R.string.verify
            _onShowMistake.value=null
            nextPair()
        }
    }
    private fun nextPair(){
        curWordId++
        if (curWordId>=Words.size){
            endTraining()
            return
        }
        _curWordPairNum.value=Pair(curWordId+1,Words.size)

        _curPairWords.value=Words[curWordId]
    }
    private fun endTraining(){
        saveResults()
    }
    fun verifyWords(word:String){
        Log.d("D_TrainingViewModel","verifyWords: ${word}");
        _onVerifyWords.value=false

    

        if (word.trim().toLowerCase().equals(curPairWords.value!!.first.trim().toLowerCase())){
            _onShowMistake.value=false

        }else{
            Mistakes.add(curPairWords.value!!)
            _mistakes.value=Mistakes
            _onShowMistake.value=true
        }
        _onChangeBtnText.value=R.string.next

    }
    fun saveResults(){
        viewModelScope.launch {
            if (trainingType== TrainingType.REVERS || trainingType== TrainingType.REVERS_LAST_MISTAKE){
                wordsSetObject.lastMistakes= arrayToStringRevers(Mistakes)
            }else{
                wordsSetObject.lastMistakes= arrayToString(Mistakes)
            }
            if (trainingType!= TrainingType.LAST_MISTAKE && trainingType!= TrainingType.REVERS_LAST_MISTAKE){
                wordsSetObject.lastResultPrc= (100-(Mistakes.size.toDouble()*100)/  wordsSetObject.numWords.toDouble()).roundToInt()
            }
            update(wordsSetObject)
            _toFinish.value=true

        }
    }
    private suspend fun update(set: WordsSet){
        database.update(set)
    }


}
