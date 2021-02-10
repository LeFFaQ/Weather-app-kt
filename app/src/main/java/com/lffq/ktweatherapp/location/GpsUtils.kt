package com.lffq.ktweatherapp.location

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
//import com.lffq.ktweatherapp.ui.GPS_REQUEST

/*
class GpsUtils (private val context: Context) {
    private val settingClient: SettingsClient = LocationServices.getSettingsClient(context)
    private val locationSettingsRequest: LocationSettingsRequest?
    private val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    init {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(LocationLiveData.locationRequest)
        locationSettingsRequest = builder.build()
        builder.setAlwaysShow(true)
    }


    fun turnGPSOn(OnGPSListener: OnGpsListener?) {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPSListener?.gpsStatus(true)
        } else {
            settingClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener (context as Activity) {
                    OnGPSListener?.gpsStatus(true) }
                .addOnFailureListener(context) {e ->
                    when ((e as ApiException).statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                            try {
                                val rae = e as ResolvableApiException
                                rae.startResolutionForResult(context, GPS_REQUEST)
                            } catch (sie: IntentSender.SendIntentException) {
                                Log.i("123", "turnGPSOn: PendingIntent unable to execute request.")
                            }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            val errorMessage =
                                "Location settings are inadequate, and cannot be " + "fixed here. Fix in Settings"
                            Log.e("123", errorMessage )
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }
}

interface OnGpsListener {
    fun gpsStatus(isGPSEnable: Boolean)
} */
