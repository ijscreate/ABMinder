package com.ijs.abminder.calculator.options

import android.annotation.SuppressLint
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

class AverageRateOfReturnFragment : Fragment() {

    private lateinit var stepTextView: TextView
    private lateinit var stepInputLayout: TextInputLayout
    private lateinit var stepInputEditText: TextInputEditText
    private lateinit var nextButton: Button
    private lateinit var resultTextView: TextView

    private var currentStep = 1
    private var initialInvestment: Double? = null
    private var finalValue: Double? = null
    private var averageRateOfReturn: Double? = null

    @SuppressLint("StringFormatInvalid", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.calculator.calculatoroptions.R.layout.fragment_average_rate_return, container, false)

        stepTextView = view.findViewById(R.id.stepTextView)
        stepInputLayout = view.findViewById(R.id.stepInputLayout)
        stepInputEditText = view.findViewById(R.id.stepInputEditText)
        nextButton = view.findViewById(com.calculator.calculatoroptions.R.id.nextButton)
        resultTextView = view.findViewById(R.id.resultTextView)

        nextButton.setOnClickListener {
            handleNextStep()
        }

        setupStep(currentStep)

        val description = view.findViewById<TextView>(R.id.descriptionTextView)
        val desc = """
            The Average Rate of Return is a financial metric that calculates the average return on investment over a specified time period. The formula for Average Rate of Return is:

            Average Rate of Return = (Final Value - Initial Investment) / Initial Investment * 100

            Example:
                Suppose you invested ₱10,000 and the investment grew to ₱12,000 over 5 years.

                Average Rate of Return = (₱12,000 - ₱10,000) / ₱10,000 * 100
                = ₱2,000 / ₱10,000 * 100
                = 20%

            Therefore, the Average Rate of Return is 20%.
        """.trimIndent()
        description.text = desc

        return view
    }

    private fun setupStep(step: Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_initial_investment)
                stepInputLayout.hint = getString(R.string.initial_investment)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_final_value)
                stepInputLayout.hint = getString(R.string.final_value)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_calculate)
                stepInputLayout.visibility = View.GONE
                nextButton.text = getString(R.string.calculate)
                nextButton.setOnClickListener {
                    calculateAverageRateOfReturn()
                }
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_result)
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
                    initialInvestment = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    finalValue = input
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }
        }
    }

    private fun calculateAverageRateOfReturn() {
        if (initialInvestment != null && finalValue != null) {
            averageRateOfReturn = ((finalValue!! - initialInvestment!!) / initialInvestment!!) * 100
            currentStep = 4
            setupStep(currentStep)
        } else {
            showInputErrorToast()
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun displayResult() {
        resultTextView.text = getString(com.calculator.calculatoroptions.R.string.average_rate_of_return_result, averageRateOfReturn)
    }

    private fun showInputErrorToast() {
        Toast.makeText(
            requireContext(),
            R.string.invalid_input_please_enter_valid_numbers,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }
}
