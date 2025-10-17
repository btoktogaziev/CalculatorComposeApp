package com.example.calculatorcomposeapp.domain

import java.util.Stack
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sqrt

// with explanations: https://github.com/btoktogaziev/CalculatorApp/blob/main/app/src/main/java/com/example/calculatorapp/Calculator.kt#L121C1-L126C32
object Calculator {
    private val opPriority = mapOf(
        "+" to 1,
        "-" to 1,
        "*" to 2,
        "/" to 2,
        "^" to 3,
        "" to 4,
        "!" to 5,
        "%" to 5,
        "(" to 0,
    )

    private val unaryOps = setOf("!", "sqrt", "%")

    fun calculateInfixWithTwoStack(input: String): Double {
        val expression = input
            .replace("×", "*")
            .replace("÷", "/")
            .replace("—", "-")
            .replace("π", PI.toString())
            .replace("•", ".")

        val symbols = toSymbol(expression)
        val values = Stack<Double>()
        val operators = Stack<String>()

        symbols.forEach { symbol ->
            when {
                symbol.toDoubleOrNull() != null -> values.push(symbol.toDouble())
                symbol == "-u" -> operators.push(symbol)
                symbol == "(" -> operators.push(symbol)
                symbol == ")" -> {
                    while (operators.isNotEmpty() && operators.peek() != "(") {
                        applyOperations(operators.pop(), values)
                    }
                    if (operators.isNotEmpty() && operators.peek() == "(") {
                        operators.pop()
                    }
                    if (operators.isNotEmpty() && operators.peek() == "sqrt") {
                        applyOperations(operators.pop(), values)
                    }
                }

                symbol in unaryOps -> operators.push(symbol)

                else -> {
                    while (operators.isNotEmpty() && opPriority[operators.peek()]!! >= opPriority[symbol]!!) {
                        applyOperations(operators.pop(), values)
                    }
                    operators.push(symbol)
                }
            }
        }
        while (operators.isNotEmpty()) {
            applyOperations(operators.pop(), values)
        }
        return values.pop()
    }

    private fun toSymbol(expr: String): List<String> {
        val chars = mutableListOf<String>()

        var i = 0
        while (i < expr.length) {
            when (val c = expr[i]) {
                in '0'..'9', '.' -> {
                    val start = i
                    while (i < expr.length && (expr[i].isDigit() || expr[i] == '.')) i++
                    chars.add(expr.substring(start, i))
                }

                'π' -> {
                    chars.add(PI.toString())
                }

                '√' -> {
                    chars.add("sqrt")
                    i++
                    if (i >= expr.length || (expr[i] != '(' && !expr[i].isDigit())) {
                        throw IllegalArgumentException("Invalid expression after sqrt")
                    }
                }

                '+', '-', '*', '/', '^', '!', '%', '(', ')' -> {
                    if (c == '-' && (chars.isEmpty() || chars.last() in listOf(
                            "(",
                            "+",
                            "-",
                            "*",
                            "/",
                            "^"
                        ))
                    ) {
                        chars.add("-u")
                    } else {
                        chars.add(c.toString())
                    }
                    i++
                }

                else -> throw IllegalArgumentException("Unknown symbol: $c")
            }
        }
        return chars
    }

    private fun applyOperations(op: String, values: Stack<Double>) {
        when (op) {
            "-u" -> {
                if (values.isEmpty()) throw IllegalArgumentException("Invalid expression: no operand for unary minus")
                values.push(-values.pop())
            }

            "!" -> {
                val n = values.pop()
                if (n < 0 || n % 1 != 0.0) throw IllegalArgumentException("Invalid factorial")
                values.push((1..n.toInt()).fold(1L) { acc, i -> acc * i }.toDouble())
            }

            "sqrt" -> {
                if (values.isEmpty()) throw IllegalArgumentException("Invalid expression: no operand for sqrt")
                val value = values.pop()
                if (value < 0) throw IllegalArgumentException("Square root of negative number")
                values.push(sqrt(value))
            }

            "%" -> {
                values.push(values.pop() / 100)
            }

            "+" -> {
                values.push(values.popTwo().let { it.first + it.second })
            }

            "-" -> {
                values.push(values.popTwo().let { it.first - it.second })
            }

            "*" -> {
                values.push(values.popTwo().let { it.first * it.second })
            }

            "/" -> {
                values.popTwo().let {
                    if (it.second == 0.0) throw ArithmeticException("Division by zero")
                    values.push(it.first / it.second)
                }
            }

            "^" -> {
                values.push(values.popTwo().let { it.first.pow(it.second) })
            }

            else -> throw IllegalArgumentException("Unknown operator: $op")
        }
    }

    private fun Stack<Double>.popTwo(): Pair<Double, Double> {
        val b = pop()
        val a = pop()
        return a to b
    }
}
