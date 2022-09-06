package com.emptydev.verba.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emptydev.verba.database.WordsDatabaseDao
import com.emptydev.verba.editwords.EditWordsViewModel

class TrainingViewModelFactory(val wordsKey:Long, val database: WordsDatabaseDao, val trainingType:TrainingType):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrainingViewModel::class.java)){
            return TrainingViewModel(wordsKey,database,trainingType) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }

}
