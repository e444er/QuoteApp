package com.e444er.quotesapp.data.mapper

import com.e444er.quotesapp.data.local.QuoteEntity
import com.e444er.quotesapp.data.remote.dto.QuoteDto
import com.e444er.quotesapp.domain.model.Quote

fun QuoteEntity.toQuote(): Quote {
    return Quote(
        quote = quote,
        author = author
    )
}

fun Quote.toQuoteEntity(): QuoteEntity {
    return QuoteEntity(
        quote = quote,
        author = author
    )
}

fun QuoteDto.toQuote(): Quote {
    return Quote(
        quote = q,
        author = a
    )
}