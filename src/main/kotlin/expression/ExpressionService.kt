package org.example.expression

interface ExpressionService {
    fun perform(expression: String): Double
    fun perform(expression: Expression): Double
}