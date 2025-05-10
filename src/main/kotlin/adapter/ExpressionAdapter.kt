package org.example.adapter

import org.example.expression.*
import kotlin.math.*

class StringExpressionAdapter : ExpressionService {

    private val operatorPrecedence = mapOf(
        "+" to 1, "-" to 1,
        "*" to 2, "/" to 2, "%" to 2,
        "^" to 3,
        "sin" to 4, "cos" to 4, "tan" to 4, "cot" to 4, "abs" to 4,
        "!" to 5,
        "~" to 5,
        "&" to 0, "|" to 0,
        "pierce" to 0, "sheffer" to 0, "xor" to 0, "webb" to 0
    )

    override fun perform(expression: String): Double {
        val parsedExpression = parse(expression)
        return parsedExpression.evaluate()
    }

    fun parse(expression: String): Expression {
        val tokens = tokenize(expression)
        val postfix = infixToPostfix(tokens)
        return buildExpressionTree(postfix)
    }

    private fun tokenize(expression: String): List<String> {
        val regex = Regex("""(\d+\.\d+|\d+|[+\-*/^%!()&|~]|\b(?:sin|cos|tan|cot|abs|pierce|sheffer|xor|webb)\b)""")
        return regex.findAll(expression).map { it.value }.toList()
    }

    private fun infixToPostfix(tokens: List<String>): List<String> {
        val output = mutableListOf<String>()
        val stack = mutableListOf<String>()

        for (token in tokens) {
            when {
                token.isNumber() -> output.add(token)
                token == "(" -> stack.add(token)
                token == ")" -> {
                    while (stack.isNotEmpty() && stack.last() != "(") {
                        output.add(stack.removeAt(stack.lastIndex))
                    }
                    if (stack.isEmpty() || stack.last() != "(") throw IllegalArgumentException("Mismatched parentheses")
                    stack.removeAt(stack.lastIndex)
                }
                token in unaryOps -> {
                    stack.add(token)
                }
                isOperator(token) -> {
                    while (stack.isNotEmpty() &&
                        stack.last() != "(" &&
                        (operatorPrecedence[stack.last()] ?: 0) >= (operatorPrecedence[token] ?: 0)
                    ) {
                        output.add(stack.removeAt(stack.lastIndex))
                    }
                    stack.add(token)
                }
                else -> throw IllegalArgumentException("Unknown token: $token")
            }
        }

        while (stack.isNotEmpty()) {
            if (stack.last() == "(" || stack.last() == ")") throw IllegalArgumentException("Mismatched parentheses")
            output.add(stack.removeAt(stack.lastIndex))
        }

        return output
    }

    private fun buildExpressionTree(postfix: List<String>): Expression {
        val stack = mutableListOf<Expression>()

        for (token in postfix) {
            when {
                token.isNumber() -> stack.add(NumberExpression(token.toDouble()))
                token in unaryOps -> {
                    if (stack.isEmpty()) throw IllegalArgumentException("Insufficient operands for unary operator: $token")
                    val operand = stack.removeAt(stack.lastIndex)
                    stack.add(UnaryExpression(operand, unaryOps[token]!!))
                }
                token in binaryOps -> {
                    if (stack.size < 2) throw IllegalArgumentException("Insufficient operands for binary operator: $token")
                    val right = stack.removeAt(stack.lastIndex)
                    val left = stack.removeAt(stack.lastIndex)
                    stack.add(BinaryExpression(left, right, binaryOps[token]!!))
                }
                else -> throw IllegalArgumentException("Unknown token in postfix: $token")
            }
        }

        if (stack.size != 1) throw IllegalArgumentException("Invalid expression: remaining operands in stack")
        return stack.first()
    }

    private fun String.isNumber() = this.toDoubleOrNull() != null
    private fun isOperator(token: String) = token in operatorPrecedence

    private val binaryOps: Map<String, (Double, Double) -> Double> = mapOf(
        "+" to { a, b -> a + b },
        "-" to { a, b -> a - b },
        "*" to { a, b -> a * b },
        "/" to { a, b -> a / b },
        "%" to { a, b -> a % b },
        "^" to { a, b -> a.pow(b.toInt()) },
        "&" to { a, b -> (a.toInt() and b.toInt()).toDouble() },
        "|" to { a, b -> (a.toInt() or b.toInt()).toDouble() },
        "pierce" to { a, b -> (a.toInt() or b.toInt()).inv().toDouble() },
        "sheffer" to { a, b -> (a.toInt() and b.toInt()).inv().toDouble() },
        "xor" to { a, b -> (a.toInt() xor b.toInt()).toDouble() },
        "webb" to { a, b -> (a.toInt() xor b.toInt().inv()).toDouble() }
    )

    private val unaryOps: Map<String, (Double) -> Double> = mapOf(
        "sin" to { x -> sin(x) },
        "cos" to { x -> cos(x) },
        "tan" to { x -> tan(x) },
        "cot" to { x -> 1.0 / tan(x) },
        "abs" to { x -> abs(x) },
        "!" to { x -> factorial(x) },
        "~" to { x -> x.toInt().inv().toDouble() }
    )

    private fun factorial(x: Double): Double {
        val n = x.toInt()
        require(x == n.toDouble() && n >= 0) { "Factorial only defined for non-negative integers" }
        return (1..n).fold(1.0) { acc, i -> acc * i }
    }

    override fun perform(expression: Expression): Double = expression.evaluate()
}






