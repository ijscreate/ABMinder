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

class CommissionFragment : Fragment() {

    private lateinit var stepTextView: TextView
    private lateinit var stepInputLayout: TextInputLayout
    private lateinit var stepInputEditText: TextInputEditText
    private lateinit var nextButton: Button
    private lateinit var resultTextView: TextView

    private var currentStep = 1
    private var saleAmount: Double? = null
    private var commissionRate: Double? = null
    private var commission: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(com.calculator.calculatoroptions.R.layout.fragment_commision, container, false)

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
            The Commission Calculator computes the commission earned based on a sale amount and commission rate.

            The formula for calculating commission is:

            Commission = Sale Amount * (Commission Rate / 100)

            This calculator helps you determine the commission earned or paid on a sale amount based on the commission rate.

            To use this calculator, follow the steps:
            1. Enter the sale amount.
            2. Enter the commission rate.
            3. Calculate the commission by multiplying the sale amount and commission rate, then dividing the result by 100.
        """.trimIndent()
        description.text = desc

        return rootView
    }

    private fun setupStep(step: Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_sale_amount)
                stepInputLayout.hint = getString(R.string.sale_amount)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_commission_rate)
                stepInputLayout.hint = getString(R.string.commission_rate)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_calculate_commission)
                stepInputLayout.hint = getString(R.string.step_3_commission_formula, saleAmount, commissionRate)
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_divide)
                stepInputLayout.hint = getString(R.string.step_4_divide_hint, commission)
                stepInputEditText.setText("")
            }

            5 -> {
                stepTextView.text = getString(R.string.step_5_result_commission)
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
                    saleAmount = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    commissionRate = input
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    val expectedCommission = saleAmount!! * (commissionRate!! / 100)
                    if (input == expectedCommission) {
                        commission = input
                        currentStep = 4
                        setupStep(currentStep)
                    } else {
                        showIncorrectCalculationToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }

            4 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    val expectedDivision = commission!! / 100.0
                    if (input == expectedDivision) {
                        currentStep = 5
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
        resultTextView.text = getString(R.string.commission_result, commission)
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showIncorrectCalculationToast() {
        Toast.makeText(requireContext(), R.string.incorrect_calculation, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        (activity).enableRecyclerView()
        super.onDestroy()
    }
}
