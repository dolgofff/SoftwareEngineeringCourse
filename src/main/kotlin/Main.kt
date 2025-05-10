package org.example

import org.example.decorator.AdvancedMathDecorator.Companion.abs
import org.example.decorator.AdvancedMathDecorator.Companion.factorial
import org.example.decorator.ArithmeticDecorator.Companion.plus
import org.example.decorator.ArithmeticDecorator.Companion.times
import org.example.decorator.BitwiseDecorator.Companion.bitAnd
import org.example.decorator.BitwiseDecorator.Companion.bitOr
import org.example.evaluator.Evaluator
import org.example.evaluator.ExpressionBuilder
import org.example.expression.BinaryExpression
import org.example.expression.NumberExpression
import org.example.expression.UnaryExpression
import kotlin.math.sin

fun main() {
    val service = ExpressionBuilder(Evaluator())
        .withArithmetic()
        .withTrigonometry()
        .withBitwise()
        .withAdvancedMath()
        .build()

    println("### Тесты с выражениями в виде строки ###")
    val expressions = listOf(
        //дефолт арифметика
        "2 + 3 * 4",
        "10 / 2 - 3",
        "2 ^ 3",
        "17 % 3",
        "5 - 322",

        //тригонометрические функции
        "sin(0)",
        "cos(0)",
        "tan(1)",
        "cot(1)",

        //ещё матан
        "abs(3 - 100)",
        "5!",
        "0!",
        "2^5",

        //побитовые операции
        "5 & 3",
        "5 | 2",
        "~5",
        "5 xor 3",
        "5 pierce 3",
        "5 sheffer 3",
        "5 webb 3",

        //разное
        "abs(5 - 9)",
        "2 * (3 + 4)",
        "(2 + 3) * (4 + 1)",
        "~(5 & 3)",
        "5 + 2!",
        "(2 ^ 3) ^ 2"
    )

    for (expr in expressions) {
        try {
            val result = service.perform(expr)
            println("Выражение: $expr = $result")
        } catch (e: Exception) {
            println("Ошибка при вычислении '$expr': ${e.message}")
        }
    }

    println("\n### Тесты с вручную собранным деревом выражения ###")

    val manualExpr = BinaryExpression(
        BinaryExpression(
            NumberExpression(5.0),
            NumberExpression(3.0),
            ::plus
        ),
        NumberExpression(2.0),
        ::times
    )
    val result = service.perform(manualExpr)
    println("Выражение (5 + 3) * 2 = $result")

    val sinExpr = UnaryExpression(
        NumberExpression(Math.PI / 2),
        ::sin
    )
    val result2 = service.perform(sinExpr)
    println("Выражение sin(pi / 2) = $result2")

    val andExpr = BinaryExpression(
        NumberExpression(5.0),
        NumberExpression(3.0),
        ::bitOr
    )
    val result3 = service.perform(andExpr)
    println("Выражение 5 | 3 = $result3")

    val absExpr = UnaryExpression(
        BinaryExpression(
            NumberExpression(-500.0),
            NumberExpression(8.0),
            ::plus
        ),
        ::abs
    )
    val result4 = service.perform(absExpr)
    println("Выражение abs(-5 + 8) = $result4")

    val factorialExpr = UnaryExpression(
        NumberExpression(5.0),
        ::factorial
    )
    val result5 = service.perform(factorialExpr)
    println("Выражение 5! = $result5")

    val xorExpr = BinaryExpression(
        NumberExpression(5.0),
        NumberExpression(3.0),
        ::bitAnd
    )
    val result6 = service.perform(xorExpr)
    println("Выражение 5 & 3 = $result6")
}

