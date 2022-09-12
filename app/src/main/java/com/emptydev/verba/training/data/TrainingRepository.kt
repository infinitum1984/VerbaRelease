package com.emptydev.verba.training.data

import com.emptydev.verba.core.data.model.WordsKit

interface TrainingRepository {

    suspend fun fetchWordsSet(wordsSetId: Long): WordsKit

    suspend fun updateWordsSet(wordsKit: WordsKit)
}