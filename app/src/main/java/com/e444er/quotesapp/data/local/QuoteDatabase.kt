package com.e444er.quotesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [QuoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class QuoteDatabase: RoomDatabase() {

    abstract val dao: QuoteDao
}