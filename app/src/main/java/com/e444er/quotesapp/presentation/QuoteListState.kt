package com.e444er.quotesapp.presentation

import com.e444er.quotesapp.domain.model.Quote

data class QuoteListState(
    val quotes: List<Quote> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String = ""
)
