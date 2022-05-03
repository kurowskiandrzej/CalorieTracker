package com.kurowskiandrzej.core.data.preferences

import android.content.SharedPreferences
import com.kurowskiandrzej.core.domain.model.ActivityLevel
import com.kurowskiandrzej.core.domain.model.Gender
import com.kurowskiandrzej.core.domain.model.GoalType
import com.kurowskiandrzej.core.domain.model.UserInfo
import com.kurowskiandrzej.core.domain.preferences.Preferences

class DefaultPreferences(
    private val sharedPref: SharedPreferences
): Preferences {
    override fun saveGender(gender: Gender) {
        sharedPref.edit()
            .putString(Preferences.KEY_GENDER, gender.name)
            .apply()
    }

    override fun saveAge(age: Int) {
        sharedPref.edit()
            .putInt(Preferences.KEY_AGE, age)
            .apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPref.edit()
            .putFloat(Preferences.KEY_WEIGHT, weight)
            .apply()
    }

    override fun saveHeight(height: Int) {
        sharedPref.edit()
            .putInt(Preferences.KEY_HEIGHT, height)
            .apply()
    }

    override fun saveActivityLevel(level: ActivityLevel) {
        sharedPref.edit()
            .putString(Preferences.KEY_ACTIVITY_LEVEL, level.name)
            .apply()
    }

    override fun saveGoalType(type: GoalType) {
        sharedPref.edit()
            .putString(Preferences.KEY_GOAL_TYPE, type.name)
            .apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(Preferences.KEY_CARB_RATIO, ratio)
            .apply()
    }

    override fun saveProteinRation(ratio: Float) {
        sharedPref.edit()
            .putFloat(Preferences.KEY_PROTEIN_RATIO, ratio)
            .apply()
    }

    override fun saveFatRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(Preferences.KEY_FAT_RATIO, ratio)
            .apply()
    }

    override fun loadUserInfo(): UserInfo {
        return UserInfo(
            Gender.fromString(sharedPref.getString(Preferences.KEY_GENDER, null) ?: "male"),
            sharedPref.getInt(Preferences.KEY_AGE, -1),
            sharedPref.getFloat(Preferences.KEY_WEIGHT, -1f),
            sharedPref.getInt(Preferences.KEY_HEIGHT, -1),
            ActivityLevel.fromString(sharedPref.getString(Preferences.KEY_ACTIVITY_LEVEL, null) ?: "medium_activity"),
            GoalType.fromString(sharedPref.getString(Preferences.KEY_GOAL_TYPE, null) ?: "maintain_weight"),
            sharedPref.getFloat(Preferences.KEY_CARB_RATIO, -1f),
            sharedPref.getFloat(Preferences.KEY_PROTEIN_RATIO, -1f),
            sharedPref.getFloat(Preferences.KEY_FAT_RATIO, -1f)
        )
    }

    override fun saveShouldShowOnBoarding(shouldShow: Boolean) {
        sharedPref.edit()
            .putBoolean(Preferences.SHOULD_SHOW_ON_BOARDING, shouldShow)
            .apply()
    }

    override fun loadShouldShowOnBoarding(): Boolean {
        return sharedPref.getBoolean(
            Preferences.SHOULD_SHOW_ON_BOARDING,
            true
        )
    }
}