package com.emptydev.verba.editwords.data

import com.emptydev.verba.core.data.model.WordsSet

interface EditWordsSetRepository {

    suspend fun fetchWordsSet(wordsSetId: Long)

    suspend fun updateWordsSet(wordsSet: WordsSet)
}