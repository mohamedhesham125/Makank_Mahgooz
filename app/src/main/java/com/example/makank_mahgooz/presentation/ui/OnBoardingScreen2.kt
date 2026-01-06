package com.example.makank_mahgooz.presentation.ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.core.screen.Screen
import com.example.makank_mahgooz.R
import com.example.makank_mahgooz.presentation.ui.screens.OnboardingScreen3

object OnboardingScreen2 : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        OnboardingTemplate(
            imageRes = R.drawable.onboarding_2,
            title = "Pave the way.",
            description = "Pave the way to parking via\nGoogle maps .",
            indicatorIndex = 1,
            onNext = { navigator.push(OnboardingScreen3) }
        )
    }
}
