package org.example.decorator

import org.example.expression.BinaryExpression
import org.example.expression.Expression
import org.example.expression.ExpressionService

class ArithmeticDecorator(
    delegate: ExpressionService
) : ExpressionServiceDecorator(delegate) {

    override fun perform(expression: Expression): Double {
        return when (expression) {
            is BinaryExpression -> {
                val op = expression.operation
                if (op === ::plus || op === ::minus || op === ::times || op === ::div || op === ::rem) {
                    op(expression.left.evaluate(), expression.right.evaluate())
                } else {
                    delegate.perform(expression)
                }
            }
            else -> delegate.perform(expression)
        }
    }

    override fun perform(expression: String): Double {
        return delegate.perform(expression)
    }

    companion object {
        fun plus(a: Double, b: Double) = a + b
        fun minus(a: Double, b: Double) = a - b
        fun times(a: Double, b: Double) = a * b
        fun div(a: Double, b: Double) = a / b
        fun rem(a: Double, b: Double) = a % b
    }
}