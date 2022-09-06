package com.emptydev.verba.editwords

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emptydev.verba.arrayToString
import com.emptydev.verba.database.Words
import com.emptydev.verba.database.WordsDatabaseDao
import kotlinx.coroutines.launch

class EditWordsViewModel(
        private val wordsKey: Long,
        val database:WordsDatabaseDao
) : ViewModel() {
    // TODO: Implement the ViewModel
    val  wordsSet=database.getLiveData(wordsKey)

    private val _onEditSetName=MutableLiveData<String>()
    val onEditSetName:LiveData<String> get()=_onEditSetName

    private val _onSaveSetName=MutableLiveData<Boolean?>()
    val onSaveSetName:LiveData<Boolean?> get()=_onSaveSetName

    private val _onTextChanged=MutableLiveData<Boolean?>()

    private val _onSaveSet=MutableLiveData<Boolean?>()
    val onSaveSet:LiveData<Boolean?> get()=_onSaveSet

    private val _onPlaySet=MutableLiveData<Boolean?>()
    val onPlaySet:LiveData<Boolean?> get()=_onPlaySet

    private val _onShowLastMistakes=MutableLiveData<String?>()
    val onShowLastMistakes:LiveData<String?> get()=_onShowLastMistakes

    private val _onSetDeleted=MutableLiveData<Boolean?>()
    val onSetDeleted:LiveData<Boolean?> get()=_onSetDeleted

    private val _textSaved=MutableLiveData<Boolean?>()
    val textSaved:LiveData<Boolean?> get()=_textSaved

    fun onEditSetName(){
        if (!onEditSetName.value.isNullOrEmpty() ){
            _onSaveSetName.value=true
            _onEditSetName.value=null
        }else {
            _onEditSetName.value = wordsSet.value!!.name
        }
    }

    fun saveSetName(name:String){
        viewModelScope.launch {
            wordsSet.value!!.name=name
            update(wordsSet.value!!)
            _onSaveSetName.value=null


        }
    }
    fun onSaveSet(){
        if (_onTextChanged.value==true) {
            Log.d("D_EditWordsViewModel","onSaveSet: text save");
            _textSaved.value=false

            _onSaveSet.value = true
        }else{
            _onPlaySet.value=true
        }
    }
    fun saveSet(map: List<Pair<String,String>>){
        _onTextChanged.value=null
        Log.d("D_EditWordsViewModel","saveSet: ${map}");
        viewModelScope.launch {
            val mapStr= arrayToString(map)
            wordsSet.value!!.words=mapStr
            wordsSet.value!!.numWords=map.size
            update(wordsSet.value!!)
            _textSaved.value=true
            _onTextChanged.value=false
        }
    }
    private suspend fun update(set:Words){
        database.update(set)
    }

    fun onTextChanged(){
        if (_onTextChanged.value!=true)
        _onTextChanged.value=true
    }
    fun onSetPlayed(){
        _onPlaySet.value=false
    }
    fun showLastMistakes(){
        _onShowLastMistakes.value=wordsSet.value!!.lastMistakes
    }
    fun deleteSet(){
        viewModelScope.launch {
            database.delete(wordsSet.value!!.wordId)
            _onSetDeleted.value=true
        }
    }


}
