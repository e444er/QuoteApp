package com.e444er.quotesapp.domain.repository

import com.e444er.quotesapp.domain.model.Quote
import com.e444er.quotesapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    suspend fun getQuoteList(fetchFromRemote: Boolean): Flow<Resource<List<Quote>>>
}