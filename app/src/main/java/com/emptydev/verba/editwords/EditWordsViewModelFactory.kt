package com.emptydev.verba.editwords

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emptydev.verba.database.WordsDatabaseDao
import com.emptydev.verba.wordslist.WordsListViewModel

class EditWordsViewModelFactory(
        private val setId:Long,
        private val dataSource:WordsDatabaseDao,

        ): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditWordsViewModel::class.java)){
            return EditWordsViewModel(setId,dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
