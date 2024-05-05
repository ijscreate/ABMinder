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

class InflationAdjustedReturnFragment : Fragment() {

    private lateinit var stepTextView : TextView
    private lateinit var stepInputLayout : TextInputLayout
    private lateinit var stepInputEditText : TextInputEditText
    private lateinit var nextButton : Button
    private lateinit var resultTextView : TextView

    private var currentStep = 1
    private var initialInvestment : Double? = null
    private var finalInvestment : Double? = null
    private var inflation : Double? = null
    private var inflationAdjustedReturn : Double? = null

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val rootView = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_inflation_adjusted_return,
            container,
            false
        )

        stepTextView = rootView.findViewById(R.id.stepTextView)
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
            Calculate the Inflation-Adjusted Return using the formula:
            
            Inflation-Adjusted Return = [(1 + Return) / (1 + Inflation Rate)] - 1
            
            This calculator helps you determine the real return on an investment after accounting for inflation.
            
            Example:
            
                If the Initial Investment was ₱1,000, the Final Investment was ₱1,200, and the Inflation Rate was 5%, the Inflation-Adjusted Return would be:
                
                Inflation-Adjusted Return = [(1 + (1,200 / 1,000)) / (1 + 0.05)] - 1
                                        = 1.2 / 1.05 - 1
                                        = 0.1429 or 14.29%
                
            Therefore, the Inflation-Adjusted Return is 14.29%.
        """.trimIndent()
        description.text = desc

        return rootView
    }

    private fun setupStep(step : Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_initial_investment)
                stepInputLayout.hint = getString(R.string.initial_investment)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_final_investment)
                stepInputLayout.hint = getString(R.string.final_investment)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_input_inflation_rate)
                stepInputLayout.hint = getString(R.string.inflation_rate)
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_calculate_return)
                stepInputLayout.hint = getString(R.string.step_4_calculate_return_hint)
                stepInputEditText.setText("")
            }

            5 -> {
                stepTextView.text = getString(R.string.step_5_calculate_real_return)
                stepInputLayout.hint = getString(R.string.step_5_calculate_real_return_hint)
                stepInputEditText.setText("")
            }

            6 -> {
                stepTextView.text = getString(R.string.step_6_calculate_inflation_adjusted_return)
                stepInputLayout.hint =
                    getString(R.string.step_6_calculate_inflation_adjusted_return_hint)
                stepInputEditText.setText("")
            }

            7 -> {
                stepTextView.text = getString(R.string.step_7_result)
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
                if (input != null) {
                    initialInvestment = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    finalInvestment = input
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input >= 0) {
                    inflation = input / 100 // Convert to decimal
                    currentStep = 4
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            4 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    val return1 = (finalInvestment!! / initialInvestment!!) - 1
                    currentStep = 5
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            5 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                val return1 = (finalInvestment!! / initialInvestment!!) - 1
                if (input != null) {
                    val realReturn = (1 + return1) / (1 + inflation!!)
                    currentStep = 6
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            6 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                val return1 = (finalInvestment!! / initialInvestment!!) - 1
                if (input != null) {
                    val realReturn = (1 + return1) / (1 + inflation!!)
                    val expectedInflationAdjustedReturn = realReturn - 1
                    if (input == expectedInflationAdjustedReturn) {
                        inflationAdjustedReturn = input
                        currentStep = 7
                        setupStep(currentStep)
                    } else {
                        showInflationAdjustedReturnCalculationErrorToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }

            7 -> {
                // Display the result
                displayResult()
            }
        }
    }

    private fun displayResult() {
        resultTextView.text =
            getString(R.string.inflation_adjusted_return_result, inflationAdjustedReturn!! * 100)
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showInflationAdjustedReturnCalculationErrorToast() {
        Toast.makeText(
            requireContext(),
            R.string.incorrect_inflation_adjusted_return_calculation,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        (activity).enableRecyclerView()
        super.onDestroy()
    }
}
