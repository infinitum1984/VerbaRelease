package com.emptydev.verba.wordslist.data

import com.emptydev.verba.core.data.model.WordsSet

interface WordsListRepository {

    suspend fun fetchWordsSetList(): List<WordsSet>

    suspend fun insertWordsSet(): List<WordsSet>

    suspend fun deleteWordsSet(wordsSetId: Long)
}