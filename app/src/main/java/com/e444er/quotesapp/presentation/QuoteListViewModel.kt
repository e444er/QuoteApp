package com.e444er.quotesapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e444er.quotesapp.domain.repository.QuoteRepository
import com.e444er.quotesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteListViewModel @Inject constructor(
    private val repo: QuoteRepository
) : ViewModel() {

    var state by mutableStateOf(QuoteListState())

    init {
        getQuoteList()
    }

    fun onEvent(event: QuoteListEvent) {
        when (event) {
            is QuoteListEvent.Refresh -> {
                getQuoteList(true)
            }
        }
    }

    private fun getQuoteList(fetchFromRemote: Boolean = false) {
        viewModelScope.launch {
            repo.getQuoteList(fetchFromRemote = fetchFromRemote)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { quotes ->
                                state = state.copy(
                                    quotes = quotes
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                error = result.message.toString()
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                }
        }
    }
}