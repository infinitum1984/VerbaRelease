package com.emptydev.verba.core.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class WordsKitWithWords(
    @Embedded
    val wordSet: WordsKit,

    @Relation(
        parentColumn = "id",
        entityColumn = "wordKitId"
    )
    val words: List<Word>
)
