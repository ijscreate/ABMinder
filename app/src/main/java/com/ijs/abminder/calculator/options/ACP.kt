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

    private lateinit var stepTextView : TextView
    private lateinit var stepInputLayout : TextInputLayout
    private lateinit var stepInputEditText : TextInputEditText
    private lateinit var nextButton : Button
    private lateinit var resultTextView : TextView

    private var currentStep = 1
    private var netCreditSales : Double? = null
    private var averageAccountsReceivable : Double? = null
    private var averageCollectionPeriod : Double? = null

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
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
            currentStep++
            setupStep(currentStep)
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

    private fun setupStep(step : Int) {
        when (step) {
            1 -> {
                stepTextView.text = "Step 1: Input net credit sales"
                stepInputLayout.hint = "Net credit sales"
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = "Step 2: Input average accounts receivable"
                stepInputLayout.hint = "Average accounts receivable"
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = "Step 3: Calculate ACP"
                stepInputLayout.visibility = View.GONE
                nextButton.text = "Calculate"
                nextButton.setOnClickListener {
                    calculateAverageCollectionPeriod()
                }
            }

            4 -> {
                stepTextView.text = "Step 4: Result"
                stepInputLayout.visibility = View.GONE
                nextButton.text = "Exit"
                nextButton.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction().remove(this)
                        .commit()
                }
                displayResult()
            }
        }
    }

    private fun calculateAverageCollectionPeriod() {
        val input = stepInputEditText.text.toString().toDoubleOrNull()
        if (input != null) {
            if (currentStep == 1) {
                netCreditSales = input
            } else if (currentStep == 2) {
                averageAccountsReceivable = input
            }

            if (currentStep == 2) {
                averageCollectionPeriod = (averageAccountsReceivable!! / netCreditSales!!) * 365
            }

            currentStep++
            setupStep(currentStep)
        } else {
            showInputErrorToast()
        }
    }

    private fun displayResult() {
        resultTextView.text = getString(
            com.calculator.calculatoroptions.R.string.average_collection_period_result,
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
