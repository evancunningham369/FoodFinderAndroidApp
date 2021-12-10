package com.example.foodfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import org.w3c.dom.Text

class ShowChoices : AppCompatActivity() {

    var chosenIcons = Icons().chosenIcons

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_choices)

        val choiceOne: TextView = findViewById(R.id.choiceOne)

        val choiceOneText = intent.getStringExtra(CHOSEN_ICONS)

        choiceOne.apply {
            text = choiceOneText
        }
    }
}