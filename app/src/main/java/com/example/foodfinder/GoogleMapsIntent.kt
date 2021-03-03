package com.example.foodfinder

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import java.io.IOException

class GoogleMapsIntent(private var mainActivity: MainActivity, private var chosenIcons: MutableList<String>) : AppCompatActivity() {
    
    fun checkForLocationPermission()
    {
        if(ActivityCompat.checkSelfPermission(mainActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            getUserLocation()
        }
        else
        {
            ActivityCompat.requestPermissions(mainActivity
                    , arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 44)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation()
    {
        Log.d("Testing", "GettingUserLocation")
        mainActivity.fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            val location = task.result
            if (location != null)
            {
                try {
                    goToGoogleMaps()
                }catch(e: IOException)
                {
                    e.printStackTrace()
                }
            }
            getNewLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getNewLocation() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        mainActivity.fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object: LocationCallback()
    {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location = locationResult.lastLocation
            goToGoogleMaps()
        }
    }

    //Uri is intended to take the data from the chosenIcons, however it did not work as expected but hopefully my intent is clear
    private fun goToGoogleMaps()
    {
        intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("restaurants")
        var chooser = Intent.createChooser(intent, "Launch Maps")
        mainActivity.startActivity(chooser)
    }
}