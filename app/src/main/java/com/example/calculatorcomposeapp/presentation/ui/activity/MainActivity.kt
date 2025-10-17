package com.example.calculatorcomposeapp.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculatorcomposeapp.presentation.ui.appbar.AppBar
import com.example.calculatorcomposeapp.presentation.ui.screens.calculator.CalculatorScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                topBar = { AppBar() }
            ) {
                CalculatorScreen(
                    Modifier.Companion.padding(it),
                    viewModel = viewModel()
                )
            }
        }
    }
}