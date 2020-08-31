package com.lffq.ktweatherapp.network

import com.lffq.ktweatherapp.network.models.Current
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeahterApi {

    @GET("data/2.5/weather")
    fun searchByCoords(
        @Query("lat") lat: String,
        @Query("lon") long: String,
        @Query("appid") appid: String = "e54d7937d6b33fe4b72b8ecbaf29c10b",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru"
    ): Observable<Current>

    /**
     * Компаньен Фабрика
     */
    companion object Factory {

        fun create(): WeahterApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.openweathermap.org/")
                .build()

            return retrofit.create(WeahterApi::class.java);
        }
    }
}