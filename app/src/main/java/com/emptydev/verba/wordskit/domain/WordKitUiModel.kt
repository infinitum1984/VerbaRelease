package com.emptydev.verba.wordskit.domain

data class WordKitUiModel(
    val id: Long,
    val numWords: Int,
    val name: String,
    val lastResultPercent: Int
)
