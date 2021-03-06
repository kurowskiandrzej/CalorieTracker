package com.kurowskiandrzej.tracker_domain.use_case

import com.kurowskiandrzej.core.domain.model.ActivityLevel
import com.kurowskiandrzej.core.domain.model.Gender
import com.kurowskiandrzej.core.domain.model.GoalType
import com.kurowskiandrzej.core.domain.model.UserInfo
import com.kurowskiandrzej.core.domain.preferences.Preferences
import com.kurowskiandrzej.tracker_domain.model.MealType
import com.kurowskiandrzej.tracker_domain.model.TrackedFood
import kotlin.math.roundToInt


class CalculateMealNutrients(
    private val preferences: Preferences
) {
    operator fun invoke(
        trackedFoods: List<TrackedFood>
    ): Result {
        val allNutrients = trackedFoods
            .groupBy { it.mealType }
            .mapValues { entry ->
                val type = entry.key
                val foods = entry.value
                MealNutrients(
                    carbs = foods.sumOf { it.carbs },
                    protein = foods.sumOf { it.protein },
                    fat = foods.sumOf { it.fat },
                    calories = foods.sumOf { it.calories },
                    mealType = type
                )
            }
        val totalCarbs = allNutrients.values.sumOf { it.carbs }
        val totalProtein = allNutrients.values.sumOf { it.protein }
        val totalFat = allNutrients.values.sumOf { it.fat }
        val totalCalories = allNutrients.values.sumOf { it.calories }

        val userInfo = preferences.loadUserInfo()
        val caloricGoal = dailyCalorieRequirements(userInfo)
        val carbsGoal = (caloricGoal * userInfo.carbRatio / 4f).roundToInt()
        val proteinGoal = (caloricGoal * userInfo.proteinRatio / 4).roundToInt()
        val fatGoal = (caloricGoal * userInfo.fatRatio / 9).roundToInt()

        return Result(
            carbsGoal = carbsGoal,
            proteinGoal = proteinGoal,
            fatGoal = fatGoal,
            caloriesGoal = caloricGoal,
            totalCarbs = totalCarbs,
            totalProtein = totalProtein,
            totalFat = totalFat,
            totalCalories = totalCalories,
            mealNutrients = allNutrients
        )
    }

    private fun bmr(userInfo: UserInfo): Int {
        return when (userInfo.gender) {
            is Gender.Male -> {
                (66.47f + 13.75f * userInfo.weight + 5f * userInfo.height - 6.75f * userInfo.age).roundToInt()
            }
            is Gender.Female -> {
                (665.09f + 9.56f * userInfo.weight + 1.84f * userInfo.height - 4.67f * userInfo.age).roundToInt()
            }
        }
    }

    private fun dailyCalorieRequirements(userInfo: UserInfo): Int {
        val activityFactor = when (userInfo.activityLevel) {
            is ActivityLevel.Low -> 1.4f
            is ActivityLevel.Medium -> 1.7f
            is ActivityLevel.High -> 2f
        }
        val extraCalories = when (userInfo.goalType) {
            is GoalType.LoseWeight -> -500
            is GoalType.MaintainWeight -> 0
            is GoalType.GainWeight -> 500
        }

        return (bmr(userInfo) * activityFactor + extraCalories).roundToInt()
    }

    data class MealNutrients(
        val carbs: Int,
        val protein: Int,
        val fat: Int,
        val calories: Int,
        val mealType: MealType
    )

    data class Result(
        val carbsGoal: Int,
        val proteinGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,
        val totalCarbs: Int,
        val totalProtein: Int,
        val totalFat: Int,
        val totalCalories: Int,
        val mealNutrients: Map<MealType, MealNutrients>
    )
}