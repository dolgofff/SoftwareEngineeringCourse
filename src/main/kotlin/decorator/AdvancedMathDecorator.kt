package org.example.decorator

import org.example.expression.BinaryExpression
import org.example.expression.Expression
import org.example.expression.ExpressionService
import org.example.expression.UnaryExpression
import kotlin.math.pow

class AdvancedMathDecorator(
    delegate: ExpressionService
) : ExpressionServiceDecorator(delegate) {

    override fun perform(expression: Expression): Double {
        return when (expression) {
            is UnaryExpression -> {
                when (expression.operation) {
                    ::abs, ::factorial -> expression.operation(expression.operand.evaluate())
                    else -> delegate.perform(expression)
                }
            }

            is BinaryExpression -> {
                if (expression.operation == ::powInt) {
                    return expression.operation(expression.left.evaluate(), expression.right.evaluate())
                }
                return delegate.perform(expression)
            }

            else -> delegate.perform(expression)
        }
    }

    companion object {
        fun abs(x: Double): Double = kotlin.math.abs(x)

        fun powInt(base: Double, exponent: Double): Double {
            val n = exponent.toInt()
            require(n >= 0) { "Exponent must be non-negative" }
            return base.pow(n)
        }

        fun factorial(x: Double): Double {
            val n = x.toInt()
            require(n >= 0) { "Factorial only defined for non-negative integers" }
            return (1..n).fold(1.0) { acc, i -> acc * i }
        }
    }
}