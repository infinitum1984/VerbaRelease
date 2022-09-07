package com.emptydev.verba.di

import com.emptydev.verba.core.data.database.WordsDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val dataModule = module {

    single { WordsDatabase.getInstance(androidApplication()) }

    single { get<WordsDatabase>().wordsDatabaseDao }
}