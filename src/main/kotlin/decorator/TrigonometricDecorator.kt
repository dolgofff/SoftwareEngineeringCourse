package org.example.decorator

import org.example.expression.Expression
import org.example.expression.ExpressionService
import org.example.expression.UnaryExpression

class TrigonometricDecorator(
    delegate: ExpressionService
) : ExpressionServiceDecorator(delegate) {

    override fun perform(expression: Expression): Double {
        return when (expression) {
            is UnaryExpression -> {
                when (expression.operation) {
                    ::sin, ::cos, ::tan, ::cot -> expression.operation(expression.operand.evaluate())
                    else -> delegate.perform(expression)
                }
            }
            else -> delegate.perform(expression)
        }
    }

    companion object {
        fun sin(x: Double): Double = sin(x)
        fun cos(x: Double): Double = cos(x)
        fun tan(x: Double): Double = tan(x)
        fun cot(x: Double): Double = 1.0 / tan(x)
    }
}