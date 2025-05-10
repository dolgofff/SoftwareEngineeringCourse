package org.example.evaluator

import org.example.adapter.StringExpressionAdapter
import org.example.expression.Expression
import org.example.expression.ExpressionService

class Evaluator: ExpressionService {
    private val adapter = StringExpressionAdapter()

    override fun perform(expression: String): Double = adapter.perform(expression)

    override fun perform(expression: Expression): Double = expression.evaluate()
}