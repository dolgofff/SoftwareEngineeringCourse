package org.example.evaluator

import org.example.expression.ExpressionService

interface ExpressionServiceBuilder {
    fun withArithmetic(): ExpressionServiceBuilder
    fun withTrigonometry(): ExpressionServiceBuilder
    fun withBitwise(): ExpressionServiceBuilder
    fun withAdvancedMath(): ExpressionServiceBuilder
    fun build(): ExpressionService
}