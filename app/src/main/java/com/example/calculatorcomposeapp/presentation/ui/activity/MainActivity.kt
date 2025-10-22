package com.example.calculatorcomposeapp.presentation.ui.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculatorcomposeapp.presentation.ui.appbar.AppBar
import com.example.calculatorcomposeapp.presentation.ui.screens.calculator.CalculatorScreen
import com.example.calculatorcomposeapp.presentation.ui.theme.CalculatorComposeAppTheme
import com.example.calculatorcomposeapp.util.PreferenceHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            CalculatorApp()
        }
    }

    @Composable
    fun CalculatorApp() {
        val sharedPreferences = PreferenceHelper(this)
        var selectedTheme by remember(sharedPreferences.chosenTheme) {
            mutableStateOf(sharedPreferences.chosenTheme)
        }
        val darkTheme = when (selectedTheme) {
            "Light" -> false
            "Dark" -> true
            else -> isSystemInDarkTheme()
        }
        CalculatorComposeAppTheme(darkTheme = darkTheme) {
            Scaffold(
                topBar = {
                    AppBar(
                        selectedTheme = selectedTheme,
                        onThemeSelected = { theme ->
                            selectedTheme = theme
                            sharedPreferences.chosenTheme = theme
                        })
                }
            ) {
                CalculatorScreen(
                    Modifier.Companion.padding(it),
                    viewModel = viewModel()
                )
            }
        }

    }
}