package com.example.foodfinder

import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.foodfinder.databinding.ActivityMainBinding

class ImageButtonSetUp(private var binding: ActivityMainBinding,
                       private var imageButtonList: MutableList<ImageButton>,
                       private val animationManager: AnimationManager,
                       private val googleMapsIntent: GoogleMapsIntent,
                       private val iconTypes: Icons) : AppCompatActivity() {

    //Add click listener to image buttons to move grid and fade in and out
    fun setUpImageButtons()
    {
        for(imageButtonIndex in 0 until binding.buttons.childCount)
        {
            imageButtonList.add(binding.buttons.getChildAt(imageButtonIndex) as ImageButton)

            imageButtonList[imageButtonIndex].setOnClickListener { animationManager.moveLinearLayout(); animationManager.fadeViewsOflinearLayoutInAndOut(); buttonClicked(imageButtonList[imageButtonIndex]); }
        }
        binding.googleMapsButton.setOnClickListener {googleMapsIntent.checkForLocationPermission()}
    }

    private fun buttonClicked(imageButton: ImageButton) {
        when(imageButton.id)
        {
            R.id.day -> iconTypes.chosenIcons.add("morning")
            R.id.night -> iconTypes.chosenIcons.add("night")
            R.id.overnight -> iconTypes.chosenIcons.add("overnight")
        }
    }
}