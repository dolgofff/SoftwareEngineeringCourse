package org.example.expression

sealed interface Expression {
    fun evaluate(): Double
}

data class NumberExpression(val value: Double) : Expression {
    override fun evaluate() = value
}

data class BinaryExpression(
    val left: Expression,
    val right: Expression,
    val operation: (Double, Double) -> Double,
) : Expression {
    override fun evaluate(): Double = operation(left.evaluate(), right.evaluate())
}

data class UnaryExpression(
    val operand: Expression,
    val operation: (Double) -> Double,
) : Expression {
    override fun evaluate(): Double = operation(operand.evaluate())
}

