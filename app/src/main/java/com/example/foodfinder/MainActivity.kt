package com.example.foodfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity(){

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    lateinit var rootlinearLayout: LinearLayout

    lateinit var animationManager: AnimationManager

    private lateinit var nextStepOfAppTextView : TextView

    private lateinit var imageButtons: LinearLayout

    private val imageButtonList = mutableListOf<ImageButton>()

    lateinit var goToGoogleMapsButton : Button

    private val iconTypes = Icons()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nextStepOfAppTextView = findViewById(R.id.nextStepOfApp)

        val nextStepOfAppList = listOf(getString(R.string.choose_time_of_day), getString(R.string.choose_dining_experience))

        imageButtons = findViewById(R.id.buttons)

        rootlinearLayout = findViewById(R.id.root_layout)

        goToGoogleMapsButton = findViewById(R.id.googleMapsButton)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val googleMapsIntent = GoogleMapsIntent(this, iconTypes.chosenIcons)

        animationManager = AnimationManager(rootlinearLayout, imageButtonList, goToGoogleMapsButton, nextStepOfAppTextView, nextStepOfAppList, iconTypes)

        val imageButtonManager = ImageButtonSetUp(imageButtons, imageButtonList, goToGoogleMapsButton, animationManager, googleMapsIntent, iconTypes)

        imageButtonManager.setUpImageButtons()
    }

    @Override
    override fun onBackPressed() {
        if(animationManager.currentRowCount == 0)
        {
            super.onBackPressed()
        }
        else
        {
            animationManager.fadeViewsOflinearLayoutInAndOut(true)
            animationManager.moveLinearLayout(true)
        }
    }
}