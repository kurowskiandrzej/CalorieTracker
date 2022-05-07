package com.kurowskiandrzej.tracker_domain.model

import com.kurowskiandrzej.core.R
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
                "snack" -> Snack
                else -> throw InvalidKeyException("MealType $name is not implemented")
            }
        }

        fun getAddMealStringResource(mealName: String): Int {
            return when (mealName) {
                "breakfast" -> R.string.add_breakfast
                "lunch" -> R.string.add_lunch
                "dinner" -> R.string.add_dinner
                "snack" -> R.string.add_snacks
                else -> throw InvalidKeyException("There is no such meal as: $mealName")
            }
        }
    }
}
