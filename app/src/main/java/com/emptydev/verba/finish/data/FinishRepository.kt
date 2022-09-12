package com.emptydev.verba.finish.data

import com.emptydev.verba.core.data.model.WordsKit

interface FinishRepository {

    suspend fun fetchWordsSet(wordsSetId: Long): WordsKit

}