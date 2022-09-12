package com.emptydev.verba.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val wordKitId: Long,
    val learningWord: String,
    val translatedWord: String
)
