package com.emptydev.verba.di

import com.emptydev.verba.editwords.EditWordsViewModel
import com.emptydev.verba.finish.FinishViewModel
import com.emptydev.verba.training.TrainingType
import com.emptydev.verba.training.TrainingViewModel
import com.emptydev.verba.wordslist.WordsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { WordsListViewModel(get()) }

    viewModel { (wordsKey: Long, trainingType: TrainingType) ->
        TrainingViewModel(wordsKey, trainingType, get())
    }

    viewModel { (wordKey: Long, numCorrect:Int, numException:Int) ->
        FinishViewModel(wordKey, numCorrect, numException, get())
    }

    viewModel {  (wordsKey: Long)->
        EditWordsViewModel(wordsKey, get())
    }
}