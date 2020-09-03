package com.lffq.ktweatherapp.ui
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lffq.ktweatherapp.network.SearchRepositoryProvider
import com.lffq.ktweatherapp.network.models.Current
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel: ViewModel() {

    var _Current = MutableLiveData<Current>()
    var _CurrentTime = MutableLiveData<String>()
    var _sunrise = MutableLiveData<String>()
    var _sunset = MutableLiveData<String>()
    
    
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
            .subscribeOn(io())
            .subscribe ({ result ->
//                _Current.value = result
//                getCurrentDate()
//                result.sys?.sunset?.let { result.sys.sunrise?.let { it1 -> sunTimeUnix(it1, it) } }

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
        Log.d(TAG, "sunRiseUnix: $item_rise")
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
        Log.d(TAG, "sunRiseUnix: $item_set")
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