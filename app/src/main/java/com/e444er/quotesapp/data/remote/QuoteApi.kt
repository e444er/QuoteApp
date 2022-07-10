package com.e444er.quotesapp.data.remote

import com.e444er.quotesapp.data.remote.dto.QuoteDto
import retrofit2.http.GET

interface QuoteApi {

    @GET("api/quotes")
    suspend fun getQuotes(): List<QuoteDto>

    companion object {
        const val BASE_URL = "https://zenquotes.io/"
    }
}