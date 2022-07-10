package com.e444er.quotesapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuoteEntity(
    @PrimaryKey
    val id: Int? = null,
    val quote: String,
    val author: String
)
