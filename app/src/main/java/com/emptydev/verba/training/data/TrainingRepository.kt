package com.emptydev.verba.training.data

import com.emptydev.verba.core.data.model.WordsSet

interface TrainingRepository {

    suspend fun fetchWordsSet(wordsSetId: Long): WordsSet

    suspend fun updateWordsSet(wordsSet: WordsSet)
}