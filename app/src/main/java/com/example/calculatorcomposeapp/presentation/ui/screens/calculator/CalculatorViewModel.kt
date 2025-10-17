package com.example.calculatorcomposeapp.presentation.ui.screens.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculatorcomposeapp.domain.Calculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {

    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state.asStateFlow()

    private var expression = ""
    private var openBrackets = 0

    fun handleInput(value: String) {
        val replacedValue = value
            .replace("AC", "clear")
            .replace("•", ".")
            .replace("⌫", "delete")
            .replace("—", "-")
        viewModelScope.launch {
            when (replacedValue) {
                in "0".."9", ".", "+", "-", "×", "÷", "^", "%", "!", "π" -> {
                    if (replacedValue == "." && !canAddDot()) return@launch
                    if (replacedValue == "π" && expression.isNotEmpty() && expression.last()
                            .isDigitOrClosingBracket()
                    ) {
                        expression += "×"
                    }
                    expression += replacedValue
                }

                "√" -> {
                    if (expression.isNotEmpty() && expression.last().isDigitOrClosingBracket())
                        expression += "×"
                    expression += "√("
                    openBrackets++
                }

                "()" -> {
                    if (shouldAddOpeningBracket()) {
                        expression += "("
                        openBrackets++
                    } else if (openBrackets > 0) {
                        expression += ")"
                        openBrackets--
                    }
                }

                "delete" -> {
                    if (expression.endsWith("√(")) {
                        expression = expression.dropLast(2)
                        openBrackets--
                    } else if (expression.isNotEmpty()) {
                        val lastChar = expression.last()
                        expression = expression.dropLast(1)
                        if (lastChar == '(') openBrackets--
                        if (lastChar == ')') openBrackets++
                    }
                }

                "clear" -> {
                    expression = ""
                    openBrackets = 0
                    _state.update { it.copy(displayText = "0") }
                    return@launch
                }

                "=" -> {
                    try {
                        val finalExpr = expression + ")".repeat(openBrackets)
                        val result = Calculator.calculateInfixWithTwoStack(finalExpr)
                        expression =
                            if (result % 1 == 0.0) result.toLong().toString() else result.toString()
                                .trimEnd('0').trimEnd('.')
                        _state.update {
                            it.copy(displayText = expression)
                        }
                        openBrackets = 0
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(displayText = e.toString())
                        }
                        expression = ""
                        openBrackets = 0
                    }
                    return@launch
                }
            }
            _state.update {
                it.copy(displayText = expression.ifEmpty { "0" })
            }
        }
    }

    private fun canAddDot(): Boolean {
        val lastChar = expression.takeLastWhile { it.isDigit() || it == '.' }
        return '.' !in lastChar
    }

    private fun shouldAddOpeningBracket(): Boolean {
        return expression.isEmpty()
                || expression.last() in "+-×÷^("
                || expression.endsWith("√(")
    }

    private fun Char.isDigitOrClosingBracket(): Boolean {
        return isDigit() || this == ')'
    }
}


data class CalculatorState(
    val displayText: String = "0",
)