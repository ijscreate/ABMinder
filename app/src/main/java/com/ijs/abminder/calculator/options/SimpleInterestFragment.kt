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

class SimpleInterestFragment : Fragment() {

    private lateinit var stepTextView : TextView
    private lateinit var stepInputLayout : TextInputLayout
    private lateinit var stepInputEditText : TextInputEditText
    private lateinit var nextButton : Button
    private lateinit var resultTextView : TextView

    private var currentStep = 1
    private var principal : Double? = null
    private var rate : Double? = null
    private var time : Double? = null
    private var multiplication : Double? = null
    private var division : Double? = null

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val rootView = inflater.inflate(R.layout.fragment_simple_interest, container, false)

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
            Calculate simple interest using the formula: 
            
            Simple Interest (SI) = (Principal × Rate × Time) / 100
    
            This calculator helps you determine the amount of interest earned or paid on a principal amount based on the rate of interest and time period.
    
            Example:
            
                If you invest ₱10,000 at an interest rate of 5% per annum for 3 years, then the simple interest would be:
    
                Simple Interest (SI = (10,000 × 5 × 3) / 100
                                    = (10,000 × 5 × 3) / 100
                                    = ₱1,500
    
            Therefore, the simple interest earned would be ₱1,500.
        """.trimIndent()
        description.text = desc

        return rootView
    }

    private fun setupStep(step : Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_values)
                stepInputLayout.hint = getString(R.string.principal)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_rate)
                stepInputLayout.hint = getString(R.string.rate)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_input_time)
                stepInputLayout.hint = getString(R.string.time_in_years)
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_multiply)
                stepInputLayout.hint =
                    getString(R.string.step_4_multiply_hint, principal, rate, time)
                stepInputEditText.setText("")
            }

            5 -> {
                stepTextView.text = getString(R.string.step_5_divide)
                stepInputLayout.hint = getString(R.string.step_5_hint, multiplication)
                stepInputEditText.setText("")
            }

            6 -> {
                stepTextView.text = getString(R.string.step_6_result)
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
                    principal = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    rate = input
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
                    val expectedMultiplication = principal!! * rate!! * time!!
                    if (input == expectedMultiplication) {
                        multiplication = input
                        currentStep = 5
                        setupStep(currentStep)
                    } else {
                        showMultiplicationErrorToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }

            5 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    val expectedDivision = multiplication!! / 100.0
                    if (input == expectedDivision) {
                        division = input
                        currentStep = 6
                        setupStep(currentStep)
                    } else {
                        showDivisionErrorToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }
        }
    }

    private fun displayResult() {
        resultTextView.text = getString(R.string.simple_interest_result, division)
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showMultiplicationErrorToast() {
        Toast.makeText(requireContext(), R.string.incorrect_multiplication, Toast.LENGTH_SHORT)
            .show()
    }

    private fun showDivisionErrorToast() {
        Toast.makeText(requireContext(), R.string.incorrect_division, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        (activity).enableRecyclerView()
        super.onDestroy()
    }
}
