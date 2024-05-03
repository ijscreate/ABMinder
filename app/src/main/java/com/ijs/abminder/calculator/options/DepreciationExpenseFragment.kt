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
import com.google.android.material.textview.MaterialTextView
import com.ijs.abminder.R
import com.ijs.abminder.calculator.CalculatorOptionsActivity

class DepreciationExpenseFragment : Fragment() {

    private lateinit var stepTextView: TextView
    private lateinit var stepInputLayout: TextInputLayout
    private lateinit var stepInputEditText: TextInputEditText
    private lateinit var nextButton: Button
    private lateinit var resultTextView: MaterialTextView
    private lateinit var description: TextView

    private var currentStep = 1
    private var cost: Double? = null
    private var salvageValue: Double? = null
    private var usefulLife: Double? = null
    private var annualDepreciation: Double? = null
    private var depreciatedCost: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.calculator.calculatoroptions.R.layout.fragment_depreciation, container, false)

        // Initialize UI components
        stepTextView = view.findViewById(R.id.stepTextView)
        stepInputLayout = view.findViewById(R.id.stepInputLayout)
        stepInputEditText = view.findViewById(R.id.stepInputEditText)
        nextButton = view.findViewById(R.id.nextButton)
        resultTextView = view.findViewById(com.calculator.calculatoroptions.R.id.textViewDepreciationResult)
        description = view.findViewById(R.id.description)

        nextButton.setOnClickListener {
            handleNextStep()
        }

        setupStep(currentStep)

        val depreciationDescription = """
            The Depreciation Expense Calculator computes the annual depreciation expense based on the cost of an asset, its salvage value, and its useful life.

            The formula for calculating depreciation expense is:

            Depreciation Expense = (Cost - Salvage Value) / Useful Life (in years)

            To use this calculator, follow the step-by-step process:
            1. Enter the cost of the asset.
            2. Enter the salvage value of the asset.
            3. Enter the useful life of the asset in years.
            4. Calculate the depreciation expense.
            5. Calculate the depreciated cost.
            6. View the final result.
        """.trimIndent()

        description.text = depreciationDescription

        return view
    }

    private fun setupStep(step: Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_cost)
                stepInputLayout.hint = getString(R.string.cost)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_salvage_value)
                stepInputLayout.hint = getString(R.string.salvage_value)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_input_useful_life)
                stepInputLayout.hint = getString(R.string.useful_life)
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_calculate_depreciation)
                stepInputLayout.hint = getString(R.string.step_4_depreciation_formula, cost, salvageValue, usefulLife)
                stepInputEditText.setText("")
            }

            5 -> {
                stepTextView.text = getString(R.string.step_5_calculate_depreciated_cost)
                stepInputLayout.hint = getString(R.string.step_5_depreciated_cost_formula, cost, annualDepreciation, usefulLife)
                stepInputEditText.setText("")
            }

            6 -> {
                stepTextView.text = getString(R.string.step_6_result)
                stepInputLayout.visibility = View.GONE
                nextButton.text = getString(R.string.exit)
                nextButton.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction().remove(this)
                        .commit()
                    val activity = requireActivity() as CalculatorOptionsActivity
                    activity.toolbar.title = getString(R.string.calculator)
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
                    cost = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input >= 0) {
                    salvageValue = input
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    usefulLife = input
                    currentStep = 4
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            4 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    val expectedAnnualDepreciation = (cost!! - salvageValue!!) / usefulLife!!
                    if (input == expectedAnnualDepreciation) {
                        annualDepreciation = input
                        currentStep = 5
                        setupStep(currentStep)
                    } else {
                        showIncorrectCalculationToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }

            5 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    val expectedDepreciatedCost = cost!! - (annualDepreciation!! * usefulLife!!)
                    if (input == expectedDepreciatedCost) {
                        depreciatedCost = input
                        currentStep = 6
                        setupStep(currentStep)
                    } else {
                        showIncorrectCalculationToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }
        }
    }

    private fun displayResult() {
        resultTextView.text = getString(
            R.string.depreciation_result,
            annualDepreciation,
            depreciatedCost
        )
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showIncorrectCalculationToast() {
        Toast.makeText(requireContext(), R.string.incorrect_calculation, Toast.LENGTH_SHORT).show()
    }
}