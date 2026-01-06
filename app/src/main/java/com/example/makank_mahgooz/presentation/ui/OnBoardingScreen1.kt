package com.example.makank_mahgooz.presentation.ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.core.screen.Screen
import com.example.makank_mahgooz.R

object OnboardingScreen1 : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        OnboardingTemplate(
            imageRes = R.drawable.onboarding_1,
            title = "Search For Parking.",
            description = "Finding the perfect parking\nspace everytime.",
            indicatorIndex = 0,
            onNext = { navigator.push(OnboardingScreen2) }
        )
    }
}
