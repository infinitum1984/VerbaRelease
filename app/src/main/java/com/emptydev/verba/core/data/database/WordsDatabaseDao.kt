package com.emptydev.verba.core.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emptydev.verba.core.data.model.WordsKit
import kotlinx.coroutines.flow.StateFlow

@Dao
interface WordsDatabaseDao {
    @Insert
    suspend fun insert(wordsKit: WordsKit):Long

    @Update
    suspend fun update(wordsKit: WordsKit)

    @Query("Delete FROM WordsKit WHERE id=:key")
    suspend fun deleteWordsKit(key:Long)

    @Query("Select * FROM WordsKit WHERE id==:key")
    fun getWordsKit(key:Long):WordsKit

    @Query("SELECT * FROM WordsKit ORDER BY id DESC")
    fun getAllWordsKitList():List<WordsKit>

    @Query("SELECT * FROM WordsKit ORDER BY id DESC")
    fun getAllWordsKitStateFlow():StateFlow<List<WordsKit>>
}
