package com.emptydev.verba.core.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emptydev.verba.core.data.model.WordsSet

@Dao
interface WordsDatabaseDao {
    @Insert
    suspend fun insert(wordsSet: WordsSet):Long

    @Update
    suspend fun update(wordsSet: WordsSet)

    @Query("Delete FROM words_table")
    suspend fun clear()

    @Query("Delete FROM words_table WHERE wordId=:key")
    suspend fun delete(key:Long)


    @Query("Select * FROM words_table WHERE wordId==:key")
    fun getLiveData(key:Long):LiveData<WordsSet>

    @Query("SELECT * FROM words_table ORDER BY wordId DESC")
    fun getAllWordsLists():LiveData<List<WordsSet>>

    @Query("Select * FROM words_table WHERE wordId==:key")
    suspend fun get(key:Long): WordsSet



}
