package com.e444er.quotesapp.presentation

sealed class QuoteListEvent {
    object Refresh : QuoteListEvent()
}
