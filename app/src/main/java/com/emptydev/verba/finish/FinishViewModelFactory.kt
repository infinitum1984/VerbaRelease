package com.emptydev.verba.finish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emptydev.verba.database.WordsDatabase
import com.emptydev.verba.database.WordsDatabaseDao

class FinishViewModelFactory(val wordKey: Long, val database: WordsDatabaseDao, val numCorrect:Int,val numException:Int): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinishViewModel::class.java)){
            return FinishViewModel(wordKey,database,numCorrect,numException) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")

    }

}
