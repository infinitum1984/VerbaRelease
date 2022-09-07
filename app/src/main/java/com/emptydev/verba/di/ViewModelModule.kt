package com.emptydev.verba.di

import com.emptydev.verba.editwords.presentation.EditWordsViewModel
import com.emptydev.verba.finish.presentation.FinishViewModel
import com.emptydev.verba.training.presentation.TrainingType
import com.emptydev.verba.training.presentation.TrainingViewModel
import com.emptydev.verba.wordslist.presentation.WordsListViewModel
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