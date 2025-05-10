package org.example.evaluator

import org.example.decorator.AdvancedMathDecorator
import org.example.decorator.ArithmeticDecorator
import org.example.decorator.BitwiseDecorator
import org.example.decorator.TrigonometricDecorator
import org.example.expression.ExpressionService

class ExpressionBuilder(private var base: ExpressionService) : ExpressionServiceBuilder {

    override fun withArithmetic(): ExpressionServiceBuilder {
        base = ArithmeticDecorator(base)
        return this
    }

    override fun withTrigonometry(): ExpressionServiceBuilder {
        base = TrigonometricDecorator(base)
        return this
    }

    override fun withBitwise(): ExpressionServiceBuilder {
        base = BitwiseDecorator(base)
        return this
    }

    override fun withAdvancedMath(): ExpressionServiceBuilder {
        base = AdvancedMathDecorator(base)
        return this
    }

    override fun build(): ExpressionService = base
}