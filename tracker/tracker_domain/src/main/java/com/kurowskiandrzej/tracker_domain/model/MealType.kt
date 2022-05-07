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
            return when (name.lowercase()) {
                "breakfast", "śniadanie" -> Breakfast
                "lunch", "obiad" -> Lunch
                "dinner", "kolacja" -> Dinner
                "snack", "przekąski" -> Snack
                else -> throw InvalidKeyException("MealType $name is not implemented")
            }
        }

        fun getAddMealStringResource(mealName: String): Int {
            return when (mealName.lowercase()) {
                "breakfast", "śniadanie" -> R.string.add_breakfast
                "lunch", "obiad" -> R.string.add_lunch
                "dinner", "kolacja" -> R.string.add_dinner
                "snack", "przekąski" -> R.string.add_snacks
                else -> throw InvalidKeyException("There is no such meal as: $mealName")
            }
        }
    }
}
