package com.lffq.ktweatherapp.network

object SearchRepositoryProvider {
    fun provideSearchRepository(): SearchRepository {
        return SearchRepository(WeahterApi.create())
    }
}