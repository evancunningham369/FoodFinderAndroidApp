package com.example.foodfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.foodfinder.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

const val CHOSEN_ICONS = "com.example.foodfinder.ChosenIcons"

class MainActivity : AppCompatActivity(){

    private var binding: ActivityMainBinding? = null

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    lateinit var animationManager: AnimationManager

    private val imageButtonList = mutableListOf<ImageButton>()

    private val iconTypes = Icons()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val nextStepOfAppList = listOf(getString(R.string.choose_time_of_day), getString(R.string.choose_dining_experience))

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val googleMapsIntent = GoogleMapsIntent(this, iconTypes.chosenIcons)

        animationManager = AnimationManager(binding!!, iconTypes, imageButtonList, nextStepOfAppList)

        val imageButtonManager = ImageButtonSetUp(binding!!, imageButtonList, animationManager, googleMapsIntent, iconTypes)

        imageButtonManager.setUpImageButtons()

        binding!!.showChoices.setOnClickListener { showChoicesActivity() }
    }

    @Override
    override fun onBackPressed() {
        if(animationManager.currentRowCount == 0)
        {
            super.onBackPressed()
        }
        else
        {
            iconTypes.chosenIcons.removeAt(0)
            animationManager.fadeViewsOflinearLayoutInAndOut(true)
            animationManager.moveLinearLayout(true)
        }
    }

    private fun showChoicesActivity()
    {
        if(iconTypes.chosenIcons.size > 0)
        {
            val chosenIcons = iconTypes.chosenIcons
            val intent = Intent(this, ShowChoices::class.java).apply {
                putExtra(CHOSEN_ICONS,chosenIcons[0]) }

            startActivity(intent)
        }
        else
        {
            Toast.makeText(this, "Make a choice", Toast.LENGTH_SHORT).show()
        }
    }
}