package com.kurowskiandrzej.tracker_domain.model

import java.security.InvalidKeyException

sealed class MealType(val name: String) {
    object Breakfast: MealType("breakfast")
    object Lunch: MealType("lunch")
    object Dinner: MealType("dinner")
    object Snack: MealType("snack")

    companion object {
        fun fromString(name: String): MealType {
            return when (name) {
                "breakfast" -> Breakfast
                "lunch" -> Lunch
                "dinner" -> Dinner
                "Snack" -> Snack
                else -> throw InvalidKeyException("MealType $name is not implemented")
            }
        }
    }
}
