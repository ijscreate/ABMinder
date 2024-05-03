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
import com.google.android.material.textview.MaterialTextView
import com.ijs.abminder.R
import com.ijs.abminder.calculator.CalculatorOptionsActivity

class EarningsPerShareFragment : Fragment() {

    private lateinit var stepTextView: MaterialTextView
    private lateinit var stepInputLayout: TextInputLayout
    private lateinit var stepInputEditText: TextInputEditText
    private lateinit var nextButton: Button
    private lateinit var resultTextView: MaterialTextView

    private var currentStep = 1
    private var netIncome: Double? = null
    private var outstandingShares: Double? = null
    private var eps: Double? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(com.calculator.calculatoroptions.R.layout.fragment_eps, container, false)

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
            Calculate Earnings Per Share (EPS) using the formula:
            
            Earnings Per Share (EPS) = Net Income / Number of Outstanding Shares
            
            This calculator guides you through the steps to determine the earnings per share of a company. Please provide the necessary values and perform the required calculations.
            
            Example:
            
                If a company has a net income of ₱100,000 and 50,000 outstanding shares, then the earnings per share would be:
                
                Earnings Per Share (EPS) = 100,000 / 50,000
                                        = ₱2.00
                
            Therefore, the earnings per share of the company is ₱2.00.
        """.trimIndent()
        description.text = desc

        return rootView
    }

    private fun setupStep(step: Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_net_income)
                stepInputLayout.hint = getString(R.string.net_income)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_outstanding_shares)
                stepInputLayout.hint = getString(R.string.outstanding_shares)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_calculate_eps)
                stepInputLayout.hint = getString(R.string.step_3_calculate_eps_hint, netIncome, outstandingShares)
                stepInputEditText.setText("")
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
                if (input != null) {
                    netIncome = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    outstandingShares = input
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    val expectedEPS = netIncome!! / outstandingShares!!
                    if (input == expectedEPS) {
                        eps = input
                        currentStep = 4
                        setupStep(currentStep)
                    } else {
                        showEPSCalculationErrorToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }
        }
    }

    private fun displayResult() {
        resultTextView.text = getString(R.string.earnings_per_share_result, eps)
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showEPSCalculationErrorToast() {
        Toast.makeText(requireContext(), R.string.incorrect_eps_calculation, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        (activity).enableRecyclerView()
        super.onDestroy()
    }
}
