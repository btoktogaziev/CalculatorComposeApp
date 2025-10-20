package com.example.calculatorcomposeapp.presentation.ui.screens.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun CalculatorScreen(
    modifier: Modifier,
    viewModel: CalculatorViewModel
) {
    val state = viewModel.state.collectAsState().value
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .weight(0.55f)
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 32.dp,
                        bottomEnd = 32.dp
                    )
                )
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Display(
                expr = state.displayText,
            )
        }
        Box(modifier = Modifier.weight(1.5f)) {
            ButtonContainer(
                onButtonClick = { value -> viewModel.handleInput(value) }
            )
        }
    }
}

@Composable
fun Display(expr: String) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect("12345678912345") {
        coroutineScope.launch {
            scrollState.scrollTo(scrollState.maxValue)
        }
    }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp)
            .horizontalScroll(state = scrollState),
        text = expr,
        textAlign = TextAlign.End,
        maxLines = 1,
        style = TextStyle(
            fontSize = 64.sp,
        )
    )
}

@Composable
fun ButtonContainer(
    onButtonClick: (String) -> Unit
) {
    val buttons = listOf<List<String>>(
        listOf("AC", "()", "%", "÷"),
        listOf("7", "8", "9", "×"),
        listOf("4", "5", "6", "—"),
        listOf("1", "2", "3", "+"),
        listOf("0", "•", "⌫", "=")
    )
    val extraButtons = listOf("√", "π", "^", "!")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            extraButtons.forEach {
                CalcExtraButton(text = it) {
                    onButtonClick(it)
                }
            }
        }
        buttons.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach {
                    CalcButton(
                        text = it,
                        color = getButtonColor(it)
                    ) {
                        onButtonClick(it)
                    }
                }
            }

        }
    }
}

@Composable
fun CalcButton(
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .size(92.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(color),
    ) {
        Text(
            text = text,
            style = TextStyle(
                letterSpacing = (-0.025).em,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = FontFamily.Monospace
            )
        )
    }
}

@Composable
fun CalcExtraButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.size(64.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = FontFamily.Monospace
            )
        )
    }
}

@Composable
private fun getButtonColor(text: String): Color {
    return when {
        text == "AC" -> MaterialTheme.colorScheme.primary
        text in listOf("()", "%", "÷", "+", "=", "×", "—") -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.tertiary
    }
}
