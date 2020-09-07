package com.lffq.ktweatherapp.ui
import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lffq.ktweatherapp.R
import com.lffq.ktweatherapp.location.LocationLiveData
import com.lffq.ktweatherapp.location.LocationModel
import com.lffq.ktweatherapp.network.SearchRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val locationData = LocationLiveData(application)
    fun getLocationData() = locationData

    var mainTemp = MutableLiveData<String>()
    var maxTemp = MutableLiveData<String>()
    var minTemp = MutableLiveData<String>()
    
    var humidity = MutableLiveData<String>()
    var pressure = MutableLiveData<String>()
    var wind = MutableLiveData<String>()

    var daytime = MutableLiveData<String>()
    var currentTime = MutableLiveData<String>()
    var sunrise = MutableLiveData<String>()
    var sunset = MutableLiveData<String>()

    var icon = MutableLiveData<Int>()

    @SuppressLint("CheckResult")
    fun request() {
        SearchRepositoryProvider
            .provideSearchRepository()
            .searchUsers("55.", "86.")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(io())
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
    fun sunriseDate(item_rise: Int) {
        //Просчет рассвета
        Log.d(TAG, "sunRiseUnix: $item_rise")
        // Конвертирование в секунды
        val date = Date(item_rise * 1000L)
        // Форматирование
        val sdf = SimpleDateFormat("h:mmaa", Locale.US)
        // даю Временную зону
        val c = Calendar.getInstance()
        sdf.timeZone = c.timeZone
        sunrise.value = sdf.format(date)
    }
    fun sunsetDate(item_set: Int) {
        //Просчет рассвета
        Log.d(TAG, "sunRiseUnix: $item_set")
        // Конвертирование в секунды
        val date = Date(item_set * 1000L)
        // Форматирование
        val sdf = SimpleDateFormat("h:mmaa", Locale.US)
        // даю Временную зону
        val c = Calendar.getInstance()
        sdf.timeZone = c.timeZone
        sunset.value = sdf.format(date)
    }
    fun getCurrentDate(): String {
        val currentDate = Date()
        val dateFormater = SimpleDateFormat("EEEE, d MMM yyyy  |  hh:mmaaa", Locale.US)
        return dateFormater.format(currentDate)
    }

    fun weatherIcon(type: String) {
        when (type) {
            "Thunderstorm" -> icon.value = R.drawable.swi_thunderstorm
            "Drizzle" -> icon.value = R.drawable.swi_showers
            "Rain" -> icon.value = R.drawable.swi_showers
        }
    }


}