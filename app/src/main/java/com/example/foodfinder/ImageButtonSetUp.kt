package com.example.foodfinder

import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class ImageButtonSetUp(private var imageButtons: LinearLayout,
                       private var imageButtonList: MutableList<ImageButton>,
                       private var goToGoogleMapsButton: Button,
                       private val animationManager: AnimationManager,
                       private val googleMapsIntent: GoogleMapsIntent,
                       private val iconTypes: Icons) : AppCompatActivity() {

    //Add click listener to image buttons to move grid and fade in and out
    fun setUpImageButtons()
    {
        for(imageButtonIndex in 0 until imageButtons.childCount)
        {
            imageButtonList.add(imageButtons.getChildAt(imageButtonIndex) as ImageButton)

            imageButtonList[imageButtonIndex].setOnClickListener { animationManager.moveLinearLayout(); animationManager.fadeViewsOflinearLayoutInAndOut(); buttonClicked(imageButtonList[imageButtonIndex])}
        }
        goToGoogleMapsButton.setOnClickListener {googleMapsIntent.checkForLocationPermission()}
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