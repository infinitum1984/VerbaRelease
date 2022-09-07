package com.emptydev.verba.finish.data

import com.emptydev.verba.core.data.model.WordsSet

interface FinishRepository {

    suspend fun fetchWordsSet(wordsSetId: Long): WordsSet

}