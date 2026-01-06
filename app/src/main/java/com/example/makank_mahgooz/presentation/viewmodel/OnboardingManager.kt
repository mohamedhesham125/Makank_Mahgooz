package com.example.makank_mahgooz.presentation.viewmodel

import android.content.Context
import androidx.core.content.edit

object OnboardingManager {
    private const val PREF_NAME = "onboarding_prefs"
    private const val KEY_HAS_SEEN_ONBOARDING = "has_seen_onboarding"

    fun hasSeenOnboarding(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_HAS_SEEN_ONBOARDING, false)
    }

    fun setOnboardingSeen(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit { putBoolean(KEY_HAS_SEEN_ONBOARDING, true) }
    }
}
