package com.example.unitconverter.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.unitconverter.R

sealed class Screen(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    companion object {

        val screens = listOf(Temperature, Distances, Weight)
    }


    private object Temperature: Screen(
        route = "temperature",
        R.string.temperature,
        R.drawable.outline_thermostat_24
    )

    private object Distances: Screen(
        route = "distances",
        R.string.distances,
        R.drawable.outline_straighten_24
    )

    private object Weight: Screen(
        route = "weight",
        R.string.weight,
        R.drawable.outline_scale_24
    )
}