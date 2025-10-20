package com.example.calculatorcomposeapp.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculatorcomposeapp.presentation.ui.appbar.AppBar
import com.example.calculatorcomposeapp.presentation.ui.screens.calculator.CalculatorScreen
import com.example.calculatorcomposeapp.presentation.ui.theme.CalculatorComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedTheme by remember { mutableStateOf("System") }
            val darkTheme = when (selectedTheme) {
                "Light" -> false
                "Dark" -> true
                else -> isSystemInDarkTheme()
            }
            CalculatorComposeAppTheme(darkTheme = darkTheme) {
                Scaffold(
                    topBar = {
                        AppBar(onThemeSelected = { theme ->
                            selectedTheme = theme
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
}