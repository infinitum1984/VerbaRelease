package com.emptydev.verba.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordsDatabaseDao {
    @Insert
    suspend fun insert(words: Words):Long

    @Update
    suspend fun update(words: Words)

    @Query("Delete FROM words_table")
    suspend fun clear()

    @Query("Delete FROM words_table WHERE wordId=:key")
    suspend fun delete(key:Long)


    @Query("Select * FROM words_table WHERE wordId==:key")
    fun getLiveData(key:Long):LiveData<Words>

    @Query("SELECT * FROM words_table ORDER BY wordId DESC")
    fun getAllWordsLists():LiveData<List<Words>>

    @Query("Select * FROM words_table WHERE wordId==:key")
    suspend fun get(key:Long):Words



}
