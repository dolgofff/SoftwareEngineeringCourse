package org.example.decorator

import org.example.expression.BinaryExpression
import org.example.expression.Expression
import org.example.expression.ExpressionService
import org.example.expression.UnaryExpression

class BitwiseDecorator(
    delegate: ExpressionService
) : ExpressionServiceDecorator(delegate) {

    override fun perform(expression: Expression): Double {
        return when (expression) {
            is UnaryExpression -> {
                when (expression.operation) {
                    ::bitNot -> expression.operation(expression.operand.evaluate())
                    else -> delegate.perform(expression)
                }
            }
            is BinaryExpression -> {
                when (expression.operation) {
                    ::bitAnd, ::bitOr, ::webb, ::sheffer, ::pierce -> expression.operation(
                        expression.left.evaluate(),
                        expression.right.evaluate()
                    )
                    else -> delegate.perform(expression)
                }
            }
            else -> delegate.perform(expression)
        }
    }

    companion object {
        fun bitAnd(a: Double, b: Double): Double = (a.toInt() and b.toInt()).toDouble()
        fun bitOr(a: Double, b: Double): Double = (a.toInt() or b.toInt()).toDouble()
        fun bitNot(x: Double): Double = x.toInt().inv().toDouble()

        fun webb(a: Double, b: Double): Double = (a.toInt() xor b.toInt().inv()).toDouble()
        fun sheffer(a: Double, b: Double): Double = (a.toInt() and b.toInt()).inv().toDouble()
        fun pierce(a: Double, b: Double): Double = (a.toInt() or b.toInt()).inv().toDouble()
    }
}