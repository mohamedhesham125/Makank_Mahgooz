package com.example.makank_mahgooz.presentation.ui.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.core.screen.Screen
import com.example.makank_mahgooz.R
import androidx.compose.ui.platform.LocalContext
import com.example.makank_mahgooz.presentation.ui.OnboardingTemplate
import com.example.makank_mahgooz.presentation.ui.SignUpScreen
import com.example.makank_mahgooz.presentation.viewmodel.OnboardingManager

object OnboardingScreen3 : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        OnboardingTemplate(
            imageRes = R.drawable.onboarding_3,
            title = "Park your car.",
            description = "Park your car safely in our\nparking lot.",
            indicatorIndex = 2,
            onNext = {
                OnboardingManager.setOnboardingSeen(context)
                navigator.push(SignUpScreen())
            }
        )
    }
}
