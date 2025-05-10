package org.example.decorator

import org.example.expression.Expression
import org.example.expression.ExpressionService

abstract class ExpressionServiceDecorator(
    protected val delegate: ExpressionService
) : ExpressionService {

    override fun perform(expression: String): Double {
        return delegate.perform(expression)
    }

    override fun perform(expression: Expression): Double {
        return delegate.perform(expression)
    }
}