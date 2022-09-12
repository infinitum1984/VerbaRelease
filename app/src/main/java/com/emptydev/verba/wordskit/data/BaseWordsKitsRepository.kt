package com.emptydev.verba.wordskit.data

import com.emptydev.verba.core.data.database.WordsDatabaseDao
import com.emptydev.verba.core.data.model.WordsKit
import com.emptydev.verba.wordskit.domain.WordsKitsRepository

class BaseWordsKitsRepository(val dao: WordsDatabaseDao) : WordsKitsRepository {
    override fun fetchWordsKitsList() = dao.getAllWordsKitStateFlow()

    override suspend fun createWordsKit(name: String) =
        dao.insert(WordsKit(name = name))

    override suspend fun fetchWordsKit(id: Long) = dao.getWordsKit(id)

    override suspend fun deleteWordsKit(wordsSetId: Long) = dao.deleteWordsKit(wordsSetId)
}