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
import kotlin.math.pow

class CompoundInterestFragment : Fragment() {

    private lateinit var stepTextView: TextView
    private lateinit var stepInputLayout: TextInputLayout
    private lateinit var stepInputEditText: TextInputEditText
    private lateinit var nextButton: Button
    private lateinit var resultTextView: TextView

    private var currentStep = 1
    private var principal: Double? = null
    private var rate: Double? = null
    private var time: Double? = null
    private var compoundingPeriods: Double? = null
    private var ratePerPeriod: Double? = null
    private var powerTerm: Double? = null
    private var compoundedInterest: Double? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_compound_interest,
            container,
            false
        )

        stepTextView = rootView.findViewById(com.calculator.calculatoroptions.R.id.stepTextView)
        stepInputLayout = rootView.findViewById(R.id.stepInputLayout)
        stepInputEditText = rootView.findViewById(R.id.stepInputEditText)
        nextButton = rootView.findViewById(R.id.nextButton)
        resultTextView = rootView.findViewById(R.id.resultTextView)

        setupStep(currentStep)

        nextButton.setOnClickListener {
            handleNextStep()
        }

        val description = rootView.findViewById<TextView>(R.id.descriptionTextView)
        val desc = """
            The Compound Interest Calculator computes the total amount (principal + interest) based on the principal amount, interest rate, and time period.

            The formula for calculating compound interest is:

            A = P(1 + r/n)^(nt)

            Where:
            A = Total amount (principal + interest)
            P = Principal amount
            r = Annual interest rate
            n = Number of times interest is compounded per year
            t = Time period in years

            This calculator helps you determine the total amount, including the interest earned, based on the principal, rate, and time period.

            To use this calculator, follow the steps:
            1. Enter the principal amount.
            2. Enter the annual interest rate.
            3. Enter the number of times interest is compounded per year.
            4. Enter the time period in years.
            5. Calculate the rate per period.
            6. Calculate the power term.
            7. Calculate the compounded interest.
            8. Display the final result.
        """.trimIndent()
        description.text = desc

        return rootView
    }

    private fun setupStep(step: Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_principal)
                stepInputLayout.hint = getString(R.string.principal)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_rate)
                stepInputLayout.hint = getString(R.string.rate)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_input_compounding_periods)
                stepInputLayout.hint = getString(R.string.compounding_periods)
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_input_time)
                stepInputLayout.hint = getString(R.string.time_in_years)
                stepInputEditText.setText("")
            }

            5 -> {
                stepTextView.text = getString(R.string.step_5_calculate_rate_per_period)
                stepInputLayout.hint =
                    getString(R.string.step_5_rate_per_period_formula, rate, compoundingPeriods)
                stepInputEditText.setText("")
            }

            6 -> {
                stepTextView.text = getString(R.string.step_6_calculate_power_term)
                stepInputLayout.hint =
                    getString(R.string.step_6_power_term_formula, time, ratePerPeriod, compoundingPeriods)
                stepInputEditText.setText("")
            }

            7 -> {
                stepTextView.text = getString(R.string.step_7_calculate_compounded_interest)
                stepInputLayout.hint =
                    getString(R.string.step_7_compound_interest_formula, principal, powerTerm)
                stepInputEditText.setText("")
            }

            8 -> {
                stepTextView.text = getString(R.string.step_8_result_compound)
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
                    principal = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    rate = input
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    compoundingPeriods = input
                    currentStep = 4
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            4 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    time = input
                    currentStep = 5
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            5 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    val expectedRatePerPeriod = rate!! / compoundingPeriods!!
                    if (input == expectedRatePerPeriod) {
                        ratePerPeriod = input
                        currentStep = 6
                        setupStep(currentStep)
                    } else {
                        showIncorrectCalculationToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }

            6 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    val expectedPowerTerm = (1 + ratePerPeriod!!).pow(time!! * compoundingPeriods!!)
                    if (input == expectedPowerTerm) {
                        powerTerm = input
                        currentStep = 7
                        setupStep(currentStep)
                    } else {
                        showIncorrectCalculationToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }

            7 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    val expectedCompoundedInterest = principal!! * (powerTerm!! - 1)
                    if (input == expectedCompoundedInterest) {
                        compoundedInterest = input
                        currentStep = 8
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
        val totalAmount = (principal ?: 0.0) + (compoundedInterest ?: 0.0)
        resultTextView.text = getString(R.string.compound_interest_result, totalAmount)
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showIncorrectCalculationToast() {
        Toast.makeText(requireContext(), R.string.incorrect_calculation, Toast.LENGTH_SHORT).show()
    }
}
