package com.alifmaulanarizqi.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var tvDisplay: TextView
    private lateinit var btnClear: Button
    private lateinit var btnDot: Button
    private var lastIsNumeric: Boolean = false
    private var lastIsDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)
        btnClear = findViewById(R.id.btnClear)
        btnDot = findViewById(R.id.btnDot)

        btnClear.setOnClickListener {
            tvDisplay.text = ""
            lastIsNumeric = false
            lastIsDot = false
        }

        btnDot.setOnClickListener {
            if(lastIsNumeric && !lastIsDot) {
                tvDisplay.append(".")
                lastIsNumeric = false
                lastIsDot = true
            }
        }

    }

    fun onDigit(view: android.view.View) {
        tvDisplay.append((view as Button).text)
        lastIsNumeric = true
        lastIsDot = false
    }

    fun onOperator(view: android.view.View) {
        val btnOperator = view as Button

        // if(lastIsNumeric && !isOperatorAdded(tvDisplay.text.toString()))
        if(lastIsNumeric) {
            tvDisplay.append(btnOperator.text)
            lastIsNumeric = false
            lastIsDot = false
        }
        // else if(btnOperator.text == "-" && !lastIsNumeric && !isOperatorAdded(tvDisplay.text.toString()))
        else if(btnOperator.text == "-" && !lastIsNumeric) {
            tvDisplay.append("-")
        }

    }

    fun onEqual(view: android.view.View) {
        if(lastIsNumeric) {
            val tvValue = tvDisplay.text.toString()
            val splitValue = tvValue.split("(?<=[^\\d.])(?=\\d)|(?<=\\d)(?=[^\\d.])".toRegex())
            var operator: String? = null
            var result = 0.0

            try {
                for(s in splitValue) {
                    if(s == "+" || s == "-" || s == "*" || s == "/")
                        operator = s
                    else {
                        if(operator == null)
                            result = s.toDouble()
                        else {
                            if(operator == "+")
                                result += s.toDouble()
                            else if(operator == "-")
                                result -= s.toDouble()
                            else if(operator == "*")
                                result *= s.toDouble()
                            else if(operator == "/")
                                result /= s.toDouble()

                        }
                    }
                }

                val decimalFormat = DecimalFormat("0.#");
                tvDisplay.text = decimalFormat.format(result).toString()
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }

        }
    }

}