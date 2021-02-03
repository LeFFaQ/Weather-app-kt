package com.lffq.ktweatherapp.ui

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lffq.ktweatherapp.network.SearchRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class MainFragViewModel : ViewModel(){

    var mainTemp = MutableLiveData<String>()
    var maxTemp = MutableLiveData<String>()
    var minTemp = MutableLiveData<String>()

    var humidity = MutableLiveData<String>()
    var pressure = MutableLiveData<String>()
    var wind = MutableLiveData<String>()

    var daytime = MutableLiveData<String>()
    var currentTime = MutableLiveData<String>()

    @SuppressLint("CheckResult")
    fun request() {
        SearchRepositoryProvider
            .provideSearchRepository()
            .searchUsers("55.", "86.")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->

                if (result != null ) {
                    mainTemp.value = result.main?.temp.toString()
                    maxTemp.value = result.main?.tempMax.toString()
                    minTemp.value = result.main?.tempMin.toString()
                    humidity.value = result.main?.humidity.toString()
                    pressure.value = result.main?.pressure.toString()
                    wind.value = result.wind?.speed.toString()
                    result.sys?.sunrise.let { sun ->
                        if (sun != null)
                        { sunriseDate(sun) }
                    }
                    result.sys?.sunset.let { sun ->
                        if (sun != null)
                        { sunsetDate(sun) }
                    }
                }

            },
                { error -> error.printStackTrace() })

    }
    fun sunriseDate(item_rise: Int): String {
        //Просчет рассвета
        Log.d(ContentValues.TAG, "sunRiseUnix: $item_rise")
        // Конвертирование в секунды
        val date = Date(item_rise * 1000L)
        // Форматирование
        val sdf = SimpleDateFormat("h:mmaa", Locale.US)
        // даю Временную зону
        val c = Calendar.getInstance()
        sdf.timeZone = c.timeZone
        return sdf.format(date)
    }
    fun sunsetDate(item_set: Int): String {
        //Просчет рассвета
        Log.d(ContentValues.TAG, "sunRiseUnix: $item_set")
        // Конвертирование в секунды
        val date = Date(item_set * 1000L)
        // Форматирование
        val sdf = SimpleDateFormat("h:mmaa", Locale.US)
        // даю Временную зону
        val c = Calendar.getInstance()
        sdf.timeZone = c.timeZone
        return sdf.format(date)
    }

    fun getCurrentDate(): String {
        val currentDate = Date()
        val dateFormater = SimpleDateFormat("EEEE, d MMM yyyy  |  hh:mmaaa", Locale.US)
        return dateFormater.format(currentDate)
    }
}