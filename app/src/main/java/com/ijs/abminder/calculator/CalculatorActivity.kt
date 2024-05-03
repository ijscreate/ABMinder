package com.ijs.abminder.calculator

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.ezylang.evalex.Expression
import com.ezylang.evalex.parser.ParseException
import com.google.android.material.navigation.NavigationView
import com.ijs.abminder.BaseDrawerActivity
import com.ijs.abminder.R
import com.ijs.abminder.calculator.adapter.CalculationOptionAdapter
import java.text.DecimalFormat


class CalculatorOptionsActivity : BaseDrawerActivity() {

    private lateinit var adapter : CalculationOptionAdapter
    lateinit var toolbar : Toolbar
    private lateinit var textViewDisplay : TextView
    private lateinit var speechRecognitionDialog : Dialog

    @SuppressLint("MissingInflatedId", "InflateParams")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        supportFragmentManager.beginTransaction().replace(R.id.calculator_container, RecyclerView())
            .commit()


        // Set up the toolbar and drawer
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupDrawer(toolbar)

        speechRecognitionDialog = Dialog(this)
        speechRecognitionDialog.setContentView(R.layout.dialog_speech_recognition)

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setCheckedItem(R.id.nav_item_calculator)

    }

    fun enableRecyclerView() {
    }

    private fun showCalculatorDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_calculator_layout, null)
        textViewDisplay = dialogView.findViewById(R.id.textViewDisplay)
        textViewDisplay.text = "0"

        // Define click listeners for all buttons and handle their actions
        val buttonIds = arrayOf(
            R.id.buttonClear,
            R.id.buttonDelete,
            R.id.buttonSignToggle,
            R.id.buttonMultiply,
            R.id.button7,
            R.id.button8,
            R.id.button9,
            R.id.buttonSubtract,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.buttonAdd,
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.buttonDivide,
            R.id.button0,
            R.id.buttonDecimal,
            R.id.buttonEqual,
            R.id.buttonPercentage
        )

        for (buttonId in buttonIds) {
            dialogView.findViewById<Button>(buttonId)
                .setOnClickListener { onButtonClick(it as Button) }
        }

        val dialog = AlertDialog.Builder(this).setTitle("Calculator").setView(dialogView)
            .setCancelable(false).setNegativeButton("Exit", null).create()

        // Show the dialog
        dialog.show()
    }

    private fun onButtonClick(button : Button) {
        val buttonText = button.text.toString()
        val currentValue = textViewDisplay.text.toString()

        when (buttonText) {
            // Clear the display
            "C" -> textViewDisplay.text = "0"

            // Delete the last character
            "⌫" -> {
                if (currentValue.length > 1) {
                    textViewDisplay.text = currentValue.dropLast(1)
                } else {
                    textViewDisplay.text = "0"
                }
            }

            // Toggle the sign of the number
            "+/-" -> {
                if (currentValue.startsWith("-")) {
                    textViewDisplay.text = currentValue.substring(1)
                } else {
                    textViewDisplay.text = buildString {
                        append("-")
                        append(currentValue)
                    }
                }
            }

            // Perform arithmetic operations
            "+", "-", "×", "/", "%" -> {
                // Append the operator to the display
                textViewDisplay.append(buttonText)
            }

            // Calculate the result
            "=" -> {
                val expression = textViewDisplay.text.toString()
                val result = evaluateExpression(expression)
                textViewDisplay.text = result.removeSuffix(".0")
            }

            // Append the digit or decimal point to the display
            else -> {
                if (currentValue == "0" && buttonText != ".") {
                    textViewDisplay.text = buttonText
                } else {
                    textViewDisplay.append(buttonText)
                }
            }
        }
    }

    private fun evaluateExpression(expression : String) : String {
        val mExpression = expression.replace(",", "")

        // Replace decimal values with their double representations
        val decimalPattern = Regex("\\b\\d+\\.\\d+\\b")
        val processedExpression = decimalPattern.replace(mExpression) { match ->
            match.value.toDouble().toString()
        }

        // Replace × with * for multiplication
        val sanitizedExpression =
            processedExpression.replace("×", "*").replace("%", "/100").replace("÷", "/")

        // Input validation
        // val pattern = Regex("^[-+*/()\\d.\\s]+$")
        //if (!pattern.matches(sanitizedExpression)) {
        //    throw IllegalArgumentException("Invalid expression: $expression")
        //}

        return try {
            val exp = Expression(sanitizedExpression).evaluate().value
            val formatter = DecimalFormat("#,###.##")
            formatter.format(exp)
        } catch (e : ParseException) {
            // Handle too many operands error
            "Error: Too many operands"
        }
    }

    override fun onCreateOptionsMenu(menu : Menu?) : Boolean {
        menuInflater.inflate(R.menu.menu_calculator, menu)
        return true
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        return when (item.itemId) {
            R.id.action_show_calculator -> {
                showCalculatorDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // Override onDestroy to clear the toolbar title
    override fun onDestroy() {
        super.onDestroy()
        supportActionBar?.setTitle(R.string.app_name)
    }

    override fun onResume() {
        super.onResume()
        toolbar.title = getString(R.string.calculator)
        navView.setCheckedItem(R.id.nav_item_calculator)
    }
}
