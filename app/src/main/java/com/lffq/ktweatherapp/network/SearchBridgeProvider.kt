package com.lffq.ktweatherapp.network

object SearchBridgeProvider {
    fun provideSearchRepository(): SearchBridge {
        return SearchBridge(WeahterApi.create())
    }
}