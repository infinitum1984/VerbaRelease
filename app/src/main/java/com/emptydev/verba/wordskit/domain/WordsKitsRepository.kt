package com.emptydev.verba.wordskit.domain

import com.emptydev.verba.core.data.model.WordsKit
import kotlinx.coroutines.flow.StateFlow

interface WordsKitsRepository {

    fun fetchWordsKitsList(): StateFlow<List<WordsKit>>

    suspend fun createWordsKit(name: String): Long

    suspend fun fetchWordsKit(id: Long): WordsKit

    suspend fun deleteWordsKit(wordsSetId: Long)
}