package com.lffq.ktweatherapp.network

import com.lffq.ktweatherapp.network.models.Current
import io.reactivex.Observable

class SearchRepository(val apiService: WeahterApi) {
    fun searchUsers(lat: String, lon: String): Observable<Current> {
        return apiService.searchByCoords(lat, lon)
    }
}