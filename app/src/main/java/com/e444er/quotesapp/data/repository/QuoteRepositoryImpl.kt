package com.e444er.quotesapp.data.repository

import com.e444er.quotesapp.data.local.QuoteDatabase
import com.e444er.quotesapp.data.mapper.toQuote
import com.e444er.quotesapp.data.mapper.toQuoteEntity
import com.e444er.quotesapp.data.remote.QuoteApi
import com.e444er.quotesapp.domain.model.Quote
import com.e444er.quotesapp.domain.repository.QuoteRepository
import com.e444er.quotesapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuoteRepositoryImpl @Inject constructor(
    private val api: QuoteApi,
    private val db: QuoteDatabase
) : QuoteRepository {

    override suspend fun getQuoteList(fetchFromRemote: Boolean): Flow<Resource<List<Quote>>> {
        return flow {
            emit(Resource.Loading(true))
            val localQuoteList = db.dao.getStoredQuoteList()
            emit(Resource.Success(data = localQuoteList.map { it.toQuote() }))

            //above three statement lets us fetch data from out local database

            val isDbEmpty = localQuoteList.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            // now we are making our api request
            val remoteQuoteList = try {
                val response = api.getQuotes()
                response.map { it.toQuote() }
            } catch (e: IOException) {
                emit(Resource.Error(message = e.message.toString()))
                null
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.message().toString()))
                null
            }

            remoteQuoteList?.let { quotes ->
                db.apply {
                    dao.deleteQuoteList()
                    dao.insertQuoteList(quotes.map { it.toQuoteEntity() })
                    emit(Resource.Success(dao.getStoredQuoteList().map { it.toQuote() }))
                    emit(Resource.Loading(isLoading = false))
                }
            }

        }
    }
}