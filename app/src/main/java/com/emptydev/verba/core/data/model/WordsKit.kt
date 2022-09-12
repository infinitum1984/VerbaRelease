package com.emptydev.verba.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WordsKit(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String,
    var numWords: Int = 0,
    var lastResultPrc: Int = 0
)
