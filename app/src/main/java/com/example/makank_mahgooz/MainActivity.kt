package com.example.makank_mahgooz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cafe.adriel.voyager.navigator.Navigator
import com.example.makank_mahgooz.presentation.ui.OnboardingScreen1
import com.example.makank_mahgooz.presentation.ui.SignUpScreen
import com.example.makank_mahgooz.presentation.viewmodel.OnboardingManager
import com.example.makank_mahgooz.ui.theme.Makank_MahgoozTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val startScreen = if (OnboardingManager.hasSeenOnboarding(this)) {
            SignUpScreen()
        } else {
            OnboardingScreen1
        }

        setContent {
            Navigator(screen = startScreen)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Makank_MahgoozTheme {
        Greeting("Android")
    }
}