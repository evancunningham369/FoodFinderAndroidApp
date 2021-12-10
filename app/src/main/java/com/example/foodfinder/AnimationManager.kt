package com.example.foodfinder

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.foodfinder.databinding.ActivityMainBinding

class AnimationManager(
    private var binding: ActivityMainBinding,
    private var iconTypes: Icons,
    private var imageButtonList: MutableList<ImageButton>,
    private var nextStepOfAppList: List<String>
) : AppCompatActivity()
{

    val MAX_ROW_COUNT = iconTypes.foodIcons.size - 1
    var currentRowCount = 0

    fun moveLinearLayout(backButtonClicked : Boolean = false)
    {
        val end : Float = if(!backButtonClicked)
        {
            binding.rootLayout.translationY + 300f
        }
        else
        {
            binding.rootLayout.translationY - 300f
        }
        ObjectAnimator.ofFloat(binding.rootLayout, "translationY", end).apply {
            duration = 1000
            start()
        }
    }
    fun fadeViewsOflinearLayoutInAndOut(backButtonClicked: Boolean = false)
    {
        disableButtons()
        binding.rootLayout.alpha = 1f
        val linearLayoutFadeOut = binding.rootLayout.animate().apply {
            duration = 500
            alpha(0f)
            start()
        }
        linearLayoutFadeOut.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                changeImageIcons(backButtonClicked)
                toggleGoogleMapButtonVisibility(backButtonClicked)
                val linearLayoutFadeIn = binding.rootLayout.animate().apply {
                    duration = 500
                    alpha(1f)
                    start()
                }
                linearLayoutFadeIn.setListener(object: AnimatorListenerAdapter()
                {
                    @Override
                    override fun onAnimationEnd(animation: Animator?) {
                        if(currentRowCount < MAX_ROW_COUNT)
                        {
                            enableButtons()
                        }
                    }
                })
            }
        })

    }

    private fun toggleGoogleMapButtonVisibility(backButtonClicked: Boolean) {

        when(backButtonClicked)
        {
            true -> binding.googleMapsButton.visibility = View.GONE
            else -> binding.googleMapsButton.visibility = View.VISIBLE
        }
    }

    /*
        Changes Icons depending on whether back button is pressed or not
     */
    fun changeImageIcons(backButtonClicked: Boolean = false)
    {
        if(!backButtonClicked)
        {
            currentRowCount++
            changeTextOfTextView(currentRowCount)
            for(imageButtonIndex in 0 until imageButtonList.size)
            {
                imageButtonList[imageButtonIndex].setImageResource(iconTypes.foodIcons[currentRowCount][imageButtonIndex])
            }

        }
        else
        {
            currentRowCount--
            changeTextOfTextView(currentRowCount)
            for(imageButtonIndex in imageButtonList.size - 1 downTo  0)
            {
                imageButtonList[imageButtonIndex].setImageResource(iconTypes.foodIcons[currentRowCount][imageButtonIndex])
            }
        }
    }

    private fun changeTextOfTextView(currentRowCount: Int) {
        binding.nextStepOfApp.text = nextStepOfAppList[currentRowCount]
    }

    fun disableButtons()
    {
        imageButtonList.forEach{imageButton -> imageButton.isClickable = false}
    }

    fun enableButtons()
    {
        imageButtonList.forEach{imageButton -> imageButton.isClickable = true}
    }
}