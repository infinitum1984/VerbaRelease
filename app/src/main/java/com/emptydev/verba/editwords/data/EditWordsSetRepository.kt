package com.emptydev.verba.editwords.data

import com.emptydev.verba.core.data.model.WordsKit

interface EditWordsSetRepository {

    suspend fun fetchWordsSet(wordsSetId: Long)

    suspend fun updateWordsSet(wordsKit: WordsKit)
}