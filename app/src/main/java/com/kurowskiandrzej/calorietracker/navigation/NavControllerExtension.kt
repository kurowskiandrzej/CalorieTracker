package com.kurowskiandrzej.calorietracker.navigation

import androidx.navigation.NavController
import com.kurowskiandrzej.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}