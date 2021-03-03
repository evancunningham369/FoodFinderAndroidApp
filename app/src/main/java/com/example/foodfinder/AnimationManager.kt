package com.example.foodfinder

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AnimationManager(private val rootlinearLayout: LinearLayout,
                       private val imageButtonList: MutableList<ImageButton>,
                       private val goToGoogleMapsButton: Button,
                       private val nextStepOfAppTextView: TextView,
                       private val nextStepOfAppList: List<String>,
                       private val iconTypes: Icons) : AppCompatActivity()
{

    val MAX_ROW_COUNT = iconTypes.foodIcons.size - 1
    var currentRowCount = 0

    fun moveLinearLayout(backButtonClicked : Boolean = false)
    {
        var end : Float = if(!backButtonClicked)
        {
            rootlinearLayout.translationY + 300f
        }
        else
        {
            rootlinearLayout.translationY - 300f
        }
        ObjectAnimator.ofFloat(rootlinearLayout, "translationY", end).apply {
            duration = 1000
            start()
        }
    }
    fun fadeViewsOflinearLayoutInAndOut(backButtonClicked: Boolean = false)
    {
        disableButtons()
        rootlinearLayout.alpha = 1f
        val linearLayoutFadeOut = rootlinearLayout.animate().apply {
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
                val linearLayoutFadeIn = rootlinearLayout.animate().apply {
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
            true -> goToGoogleMapsButton.visibility = View.GONE
            else -> goToGoogleMapsButton.visibility = View.VISIBLE
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
        nextStepOfAppTextView.text = nextStepOfAppList[currentRowCount]
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