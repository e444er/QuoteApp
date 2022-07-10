package com.e444er.quotesapp.di

import android.app.Application
import androidx.room.Room
import com.e444er.quotesapp.data.local.QuoteDatabase
import com.e444er.quotesapp.data.remote.QuoteApi
import com.e444er.quotesapp.data.repository.QuoteRepositoryImpl
import com.e444er.quotesapp.domain.repository.QuoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuoteModule {

    @Provides
    @Singleton
    fun provideQuoteApi(): QuoteApi {
        return Retrofit.Builder()
            .baseUrl(QuoteApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteApi::class.java)
    }


    @Provides
    @Singleton
    fun provideQuoteDatabase(app: Application): QuoteDatabase {
        return Room.databaseBuilder(
            app,
            QuoteDatabase::class.java,
            "quote_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideQuoteRepository(api: QuoteApi, db: QuoteDatabase): QuoteRepository {
        return QuoteRepositoryImpl(api, db)
    }
}