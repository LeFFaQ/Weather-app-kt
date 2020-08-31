package com.lffq.ktweatherapp.ui
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

    fun request() {
        SearchRepositoryProvider
            .provideSearchRepository()
            .searchUsers("55.", "86.")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(io())
            .subscribe ({ result ->
                _Current.value = result
                getCurrentDate()
                result.sys?.sunset?.let { result.sys.sunrise?.let { it1 -> sunTimeUnix(it1, it) } }
                
            },
                { error -> error.printStackTrace()})

    }

    fun sunTimeUnix(item_rise: Int, item_set: Int) {
        //Просчет рассвета
        Log.d(TAG, "sunRiseUnix: "+ item_rise)
        // Конвертирование в секунды
        val date = Date(item_rise * 1000L)
        // Форматирование
        val sdf = SimpleDateFormat("h:mmaa")
        // даю Временную зону
        val c = Calendar.getInstance()
        sdf.timeZone = c.timeZone
        val formattedDate = sdf.format(date)
        _sunrise.value = formattedDate

        //просчет заката
        Log.d(TAG, "sunRiseUnix: "+ item_set)
        // Конвертирование в секунды
        val datetwo = Date(item_set * 1000L)
        // Форматирование
        val sdftwo = SimpleDateFormat("h:mmaa")
        // даю Временную зону
        val ctwo = Calendar.getInstance()
        sdftwo.timeZone = ctwo.timeZone
        val formattedDatetwo = sdftwo.format(date)
        _sunset.value = formattedDatetwo

    }

    fun getCurrentDate() {
        val currentDate = Date()
        val dateFormater = SimpleDateFormat("EEEE, d MMM yyyy  |  hh:mmaaa", Locale.US)
        _CurrentTime.value = dateFormater.format(currentDate)
    }


}