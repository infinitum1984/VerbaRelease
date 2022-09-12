package com.emptydev.verba.wordskit.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emptydev.verba.wordskit.domain.WordsKitsRepository
import kotlinx.coroutines.launch


class WordsListViewModel(
    private val repository: WordsKitsRepository
) : ViewModel() {
    val words = repository.fetchWordsKitsList()
    private val _navigateToEditWords = MutableLiveData<Long>()
    val navigateToEditWords: LiveData<Long> get() = _navigateToEditWords
    val createWordListNavigate = MutableLiveData<Boolean?>()

    private val _onFastPlaySet = MutableLiveData<Long>()
    val onFastPlaySet: LiveData<Long> get() = _onFastPlaySet

    private val _setIsEmpty = MutableLiveData<Boolean>()
    val setIsEmpty: LiveData<Boolean> get() = _setIsEmpty
    fun onAdd() {
        Log.d("D_WordsListViewModel", "onAdd: ")
        viewModelScope.launch {
            _navigateToEditWords.value = repository.createWordsKit("Words set")
        }
    }

    fun onPlaySet(id: Long) {
        _navigateToEditWords.value = id
    }

    fun doneNavigation() {
        //_navigateToEditWords.value=null
    }

    fun fastPlaySet(wordsId: Long) {
        viewModelScope.launch {
            val wordsObject = repository.fetchWordsKit(wordsId)
            if (wordsObject.numWords == 0) {
                _setIsEmpty.value = true
            } else {
                _onFastPlaySet.value = wordsId
            }
        }
    }

    fun deleteSet(id: Long) {
        viewModelScope.launch {
            repository.deleteWordsKit(id)
        }
    }
}
