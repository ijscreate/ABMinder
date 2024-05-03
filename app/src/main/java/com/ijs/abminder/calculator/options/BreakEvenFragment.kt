package com.ijs.abminder.calculator.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ijs.abminder.R
import com.ijs.abminder.calculator.CalculatorOptionsActivity

class BreakEvenFragment : Fragment() {

    private lateinit var stepTextView: TextView
    private lateinit var stepInputLayout: TextInputLayout
    private lateinit var stepInputEditText: TextInputEditText
    private lateinit var nextButton: Button
    private lateinit var resultTextView: TextView

    private var currentStep = 1
    private var fixedCost: Double? = null
    private var variableCost: Double? = null
    private var sellingPrice: Double? = null
    private var breakEvenQuantity: Double? = null
    private var breakEvenSales: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(com.calculator.calculatoroptions.R.layout.fragment_break_even, container, false)

        stepTextView = rootView.findViewById(R.id.stepTextView)
        stepInputLayout = rootView.findViewById(R.id.stepInputLayout)
        stepInputEditText = rootView.findViewById(R.id.stepInputEditText)
        nextButton = rootView.findViewById(R.id.nextButton)
        resultTextView = rootView.findViewById(R.id.resultTextView)

        nextButton.setOnClickListener {
            handleNextStep()
        }

        setupStep(currentStep)

        val description = rootView.findViewById<TextView>(R.id.descriptionTextView)
        val desc = """
            Break-Even Analysis is a financial tool used to determine the point at which total revenue equals total costs, indicating the level of sales needed to cover all expenses.

            Given:
            - Fixed Cost: The total expenses that remain constant regardless of the level of production or sales. This includes rent, salaries, and utilities.
            - Variable Cost: The costs that vary with the level of production or sales. This includes raw materials and direct labor.
            - Selling Price: The price at which a product or service is sold to customers.

            Formula:
            - Break-Even Point (units) = Fixed Cost / (Selling Price - Variable Cost): The number of units that need to be sold to cover all costs and reach the break-even point.
            - Break-Even Point (sales) = Fixed Costs ÷ Contribution Margin: The sales revenue needed to cover all costs and reach the break-even point.

            Example:
            Suppose a company has fixed costs of ₱50,000, variable costs of ₱20 per unit, and sells its product for ₱100 each.

            Using the Break-Even Point (units) formula:
            Break-Even Point (units) = ₱50,000 / (₱100 - ₱20) = 625 units

            Therefore, the company needs to sell 625 units to cover all costs and break even.

            Using the Break-Even Point (sales) formula:
            Break-Even Point (sales) = ₱50,000 / (₱100 - ₱20) / ₱100 = ₱83,333.33

            Therefore, the company needs to generate ₱83,333.33 in sales revenue to cover all costs and break even.
        """.trimIndent()
        description.text = desc

        return rootView
    }

    private fun setupStep(step: Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_fixed_cost)
                stepInputLayout.hint = getString(R.string.fixed_cost)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_variable_cost)
                stepInputLayout.hint = getString(R.string.variable_cost)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_input_selling_price)
                stepInputLayout.hint = getString(R.string.selling_price)
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_calculate)
                stepInputLayout.visibility = View.GONE
                nextButton.text = getString(R.string.calculate)
                nextButton.setOnClickListener {
                    calculateBreakEven()
                }
            }

            5 -> {
                stepTextView.text = getString(R.string.step_5_result)
                stepInputLayout.visibility = View.GONE
                nextButton.text = getString(R.string.exit)
                nextButton.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction().remove(this)
                        .commit()
                }
                displayResult()
            }
        }
    }

    private fun handleNextStep() {
        when (currentStep) {
            1 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    fixedCost = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    variableCost = input
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    sellingPrice = input
                    currentStep = 4
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }
        }
    }

    private fun calculateBreakEven() {
        if (fixedCost != null && variableCost != null && sellingPrice != null) {
            if (variableCost!! == 0.0) {
                resultTextView.text = getString(R.string.invalid_variable_cost)
                return
            }

            breakEvenQuantity = fixedCost!! / (sellingPrice!! - variableCost!!)
            breakEvenSales = fixedCost!! / ((sellingPrice!! - variableCost!!) / sellingPrice!!)

            currentStep = 5
            setupStep(currentStep)
        } else {
            showInputErrorToast()
        }
    }

    private fun displayResult() {
        resultTextView.text = getString(
            R.string.break_even_result,
            breakEvenQuantity,
            breakEvenSales
        )
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }
}
