package com.emptydev.verba.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.HashMap

@Entity(tableName = "words_table")
data class Words(

    @PrimaryKey(autoGenerate = true)
    var wordId:Long=0L,

    @ColumnInfo(name = "name")
    var name:String="",

    @ColumnInfo(name = "words")
    var words:String ="",

    @ColumnInfo(name = "last_mistakes")
    var lastMistakes:String ="",


    @ColumnInfo(name = "num_words")
    var numWords:Int =0,

    @ColumnInfo(name = "last_result_prc")
    var lastResultPrc:Int=0
)
