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

class FutureValueFragment : Fragment() {

    private lateinit var stepTextView: TextView
    private lateinit var stepInputLayout: TextInputLayout
    private lateinit var stepInputEditText: TextInputEditText
    private lateinit var nextButton: Button
    private lateinit var resultTextView: TextView

    private var currentStep = 1
    private var presentValue: Double? = null
    private var interestRate: Double? = null
    private var time: Double? = null
    private var futureValue: Double? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(com.calculator.calculatoroptions.R.layout.fragment_future_value, container, false)

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
            Calculate the Future Value (FV) using the formula:
            
            Future Value (FV) = Present Value × (1 + Interest Rate)^Time
            
            This calculator helps you determine the future value of an investment or savings based on the present value, interest rate, and time period.
            
            Example:
            
                If you invest ₱1,000 at an interest rate of 5% per year for 3 years, the future value would be:
                
                Future Value (FV) = 1,000 × (1 + 0.05)^3
                                 = 1,000 × 1.157625
                                 = ₱1,157.63
                
            Therefore, the future value of the investment after 3 years is ₱1,157.63.
        """.trimIndent()
        description.text = desc

        return rootView
    }

    private fun setupStep(step: Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_present_value)
                stepInputLayout.hint = getString(R.string.present_value)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_interest_rate)
                stepInputLayout.hint = getString(R.string.interest_rate_percent)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_input_time)
                stepInputLayout.hint = getString(R.string.time_in_years)
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_calculate_future_value)
                stepInputLayout.hint = getString(R.string.step_4_calculate_future_value_hint, presentValue, interestRate, time)
                stepInputEditText.setText("")
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
                if (input != null) {
                    presentValue = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    interestRate = input / 100.0 // Convert to decimal
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    time = input
                    currentStep = 4
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            4 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    val expectedFutureValue = presentValue!! * (1 + interestRate!!) .pow(time!!)
                    if (input == expectedFutureValue) {
                        futureValue = input
                        currentStep = 5
                        setupStep(currentStep)
                    } else {
                        showFutureValueCalculationErrorToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }
        }
    }

    private fun displayResult() {
        resultTextView.text = getString(R.string.future_value_result, futureValue)
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showFutureValueCalculationErrorToast() {
        Toast.makeText(requireContext(), R.string.incorrect_future_value_calculation, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        (activity).enableRecyclerView()
        super.onDestroy()
    }
}
