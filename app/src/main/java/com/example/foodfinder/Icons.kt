package com.example.foodfinder

data class Icons(
        val timeOfDayIcons: MutableList<Int> = mutableListOf(
                R.drawable.morning,
                R.drawable.evening,
                R.drawable.night),

        val diningExperienceIcons: MutableList<Int> = mutableListOf(
                R.drawable.drive_through,
                R.drawable.fast_food,
                R.drawable.dine_in),

        val foodIcons: MutableList<MutableList<Int>> = mutableListOf(timeOfDayIcons, diningExperienceIcons),

        val chosenIcons: MutableList<String> = mutableListOf()
)

