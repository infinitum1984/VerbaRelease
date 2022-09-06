package com.emptydev.verba.wordslist

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emptydev.verba.database.Words
import com.emptydev.verba.database.WordsDatabaseDao
import kotlinx.coroutines.launch


class WordsListViewModel(private val database: WordsDatabaseDao,
                         private val context: Context
) : ViewModel() {
    val words = database.getAllWordsLists()
    private val _navigateToEditWords = MutableLiveData<Long>()
    val navigateToEditWords:LiveData<Long> get()=_navigateToEditWords
    val createWordListNavigate=MutableLiveData<Boolean?>()

    private val _onFastPlaySet = MutableLiveData<Long>()
    val onFastPlaySet:LiveData<Long> get()=_onFastPlaySet

    private val _setIsEmpty = MutableLiveData<Boolean>()
    val setIsEmpty:LiveData<Boolean> get()=_setIsEmpty
    fun onAdd(){
        Log.d("D_WordsListViewModel", "onAdd: ")
        viewModelScope.launch {

           val word_list = Words(name = "Words set ${words.value?.size}")
           val id= insert(word_list)
            _navigateToEditWords.value=id
        }


    }
    fun onPlaySet(id:Long){
        _navigateToEditWords.value=id
    }
    fun doneNavigation(){
        _navigateToEditWords.value=null
    }
    private suspend fun insert(night: Words):Long {

        return database.insert(night)
    }
    fun fastPlaySet(wordsId:Long){
        viewModelScope.launch {
            val wordsObject=database.get(wordsId)

            if (wordsObject.numWords==0) {
                _setIsEmpty.value = true
            }else{
                _onFastPlaySet.value=wordsId
            }
        }
    }
    fun deleteSet(id:Long){
        viewModelScope.launch {
            database.delete(id)

        }
    }
}
