package com.e444er.quotesapp.data.local

import androidx.room.Database

@Database(
    entities = [QuoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class QuoteDatabase {

    abstract val dao: QuoteDao
}