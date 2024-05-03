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

class AverageCollectionPeriodFragment : Fragment() {

    private lateinit var stepTextView: TextView
    private lateinit var stepInputLayout: TextInputLayout
    private lateinit var stepInputEditText: TextInputEditText
    private lateinit var nextButton: Button
    private lateinit var resultTextView: TextView

    private var currentStep = 1
    private var netCreditSales: Double? = null
    private var averageAccountsReceivable: Double? = null
    private var averageCollectionPeriod: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_average_collection_period,
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
            Calculate the Average Collection Period (ACP) using the formula: 
            
            ACP = (Average Accounts Receivable / Net Credit Sales) * Number of Days
    
            This calculator helps you determine the average number of days it takes for a company to collect payments from its customers.
    
            Example:
            
                If the Average Accounts Receivable is ₱50,000 and Net Credit Sales is ₱200,000, then the ACP would be:
    
                ACP = (₱50,000 / ₱200,000) * 365
                    ≈ 91.25 days
    
            Therefore, the Average Collection Period is approximately 91.25 days.
        """.trimIndent()
        description.text = desc

        return rootView
    }

    private fun setupStep(step: Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_net_credit_sales)
                stepInputLayout.hint = getString(R.string.net_credit_sales)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_average_accounts_receivable)
                stepInputLayout.hint = getString(R.string.average_accounts_receivable)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_calculate_acp)
                stepInputLayout.visibility = View.GONE
                nextButton.text = getString(R.string.calculate)
                nextButton.setOnClickListener {
                    calculateAverageCollectionPeriod()
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
                if (input != null) {
                    netCreditSales = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    averageAccountsReceivable = input
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                calculateAverageCollectionPeriod()
            }
        }
    }

    private fun calculateAverageCollectionPeriod() {
        if (netCreditSales != null && averageAccountsReceivable != null) {
            averageCollectionPeriod = (averageAccountsReceivable!! / netCreditSales!!) * 365
            currentStep = 4
            setupStep(currentStep)
        } else {
            showInputErrorToast()
        }
    }

    private fun displayResult() {
        resultTextView.text = getString(
            R.string.average_collection_period_result,
            averageCollectionPeriod
        )
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        (activity).enableRecyclerView()
        super.onDestroy()
    }
}
