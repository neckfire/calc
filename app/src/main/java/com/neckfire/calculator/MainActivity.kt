package com.neckfire.calculator

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.neckfire.calculator.extensions.compareLast
import com.neckfire.calculator.extensions.lastAnSameSymbol
import com.google.android.material.snackbar.Snackbar
import com.neckfire.calculator.databinding.ActivityMainBinding


class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindClicks()
    }

    private fun bindClicks() {
        binding.apply {
            btClear.setOnClickListener {
                clear()
            }

            btOne.setOnClickListener {
                type(it)
            }

            btTwo.setOnClickListener {
                type(it)
            }

            btThree.setOnClickListener {
                type(it)
            }

            btFour.setOnClickListener {
                type(it)
            }

            btFive.setOnClickListener {
                type(it)
            }

            btSix.setOnClickListener {
                type(it)
            }

            btSeven.setOnClickListener {
                type(it)
            }

            btEight.setOnClickListener {
                type(it)
            }

            btNine.setOnClickListener {
                type(it)
            }

            btZero.setOnClickListener {
                type(it)
            }

            btMinus.setOnClickListener {
                arithmeticClick(it)
            }

            btPlus.setOnClickListener {
                arithmeticClick(it)
            }

            btDivide.setOnClickListener {
                arithmeticClick(it)
            }

            btMultiply.setOnClickListener {
                arithmeticClick(it)
            }

            btPercent.setOnClickListener {
                notImplemented(it)
            }

            btParentheses.setOnClickListener {
                notImplemented(it)
            }

            btBack.setOnClickListener {
                backspace()
            }

            btEqual.setOnClickListener {
                calculate()
            }

            btDot.setOnClickListener {
                dotClick(it)
            }
        }
    }

    private fun dotClick(view: View) {
        val button = view as Button
        binding.apply {
            if (etDisplay.text.isNotEmpty() && !etDisplay.text.toString().lastAnSameSymbol()) {
                if (!button.text.toString().compareLast(etDisplay.text.toString())) {
                    type(button)
                }
            }
        }
    }

    private fun calculate() {
        binding.apply {
            val txt: String = etDisplay.text.toString()
            try {
                val result: Double = evaluateExpression(txt)
                etDisplay.text = result.toString()
            } catch (e: Exception) {
                etDisplay.text = e.message
            }
        }
    }

    private fun evaluateExpression(expression: String): Double {
        val parts = expression.split(Regex("[+\\-*/]"))
        var result = try {
            parts.first().toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }

        var i = 1
        while (i < parts.size) {
            val operator = findOperatorIndex(expression, i)
            val operand = try {
                parts[i].toDouble()
            } catch (e: NumberFormatException) {
                0.0
            }
            result = when (expression[operator]) {
                '+' -> result + operand
                '-' -> result - operand
                '*' -> result * operand
                '/' -> result / operand
                else -> throw IllegalArgumentException("Opérateur invalide : ${expression[operator]}")
            }
            i++
        }

        return result
    }

    private fun findOperatorIndex(expression: String, startIndex: Int): Int {
        for (i in startIndex until expression.length) {
            if (expression[i] in "+-*/" && (i == 0 || expression[i - 1] != '*')) {
                return i
            }
        }
        throw IllegalArgumentException("Pas d'opérateur valide trouvé")
    }

    private fun clear() {
        "".also { binding.etDisplay.text = it }
    }

    private fun notImplemented(it: View) =
        Snackbar.make(it, "Not implemented yet!", Snackbar.LENGTH_SHORT).show()

    private fun backspace() {
        binding.etDisplay.text = binding.etDisplay.text.dropLast(1)
    }

    private fun arithmeticClick(it: View?) {
        val button = it as Button
        binding.apply {
            if (etDisplay.text.isNotEmpty() && etDisplay.text.toString().lastAnSameSymbol()) {
                if (!button.text.toString().compareLast(binding.etDisplay.text.toString())) {
                    etDisplay.text = etDisplay.text.toString().dropLast(1)
                    type(button)
                }
            } else {
                type(button)
            }
        }
    }

    private fun type(view: View) {
        val button = view as Button
        "${binding.etDisplay.text}${button.text}".also { binding.etDisplay.text = it }
    }
}