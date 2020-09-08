package com.lffq.ktweatherapp.ui

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.lffq.ktweatherapp.R
import com.lffq.ktweatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var model: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        model = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewmodel = model
        binding.lifecycleOwner = this

        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        permis()

    }

    fun permis() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) -> {

                model.getLocationData().observe(this, Observer {
                    model.request(it.latitude, it.longitude)
                    Log.d("123", "onCreate: ${it.latitude} ${it.longitude}")
                }) }

            else -> {
                Log.d("123", "onCreate: заблокирован доступ")
                val builder: AlertDialog.Builder = AlertDialog.Builder(applicationContext)
                builder.setMessage(R.string.geo_error)
                    .setPositiveButton("Ok") { dialog, i -> permis() }
                    .setNegativeButton("No") { dialog, i ->
                        Log.d("123", "checkPermission: панимаю, хорошего вам дня") } }
        } }

}
const val LOCATION_REQUEST = 100
const val GPS_REQUEST = 101