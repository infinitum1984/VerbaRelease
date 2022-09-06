package com.emptydev.verba.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Words::class],version = 1,exportSchema = false)
abstract class WordsDatabase :RoomDatabase(){
    abstract val wordsDatabaseDao:WordsDatabaseDao
    companion object{
        @Volatile
        private var INSTANCE: WordsDatabase?=null
        fun getInstance(context: Context):WordsDatabase{
            synchronized(this){
                var instance= INSTANCE
                if (instance==null){
                    instance= Room.databaseBuilder(context.applicationContext,WordsDatabase::class.java,"words_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE=instance
                return instance

            }
        }

    }
}
