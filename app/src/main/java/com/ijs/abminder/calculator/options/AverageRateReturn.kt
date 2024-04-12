package com.ijs.abminder.calculator.options

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.calculator.calculatoroptions.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.ijs.abminder.calculator.CalculatorOptionsActivity

class AverageRateOfReturnFragment : Fragment() {

    private lateinit var initialInvestmentEditText : TextInputEditText
    private lateinit var finalValueEditText : TextInputEditText
    private lateinit var calculateButton : Button
    private lateinit var resultTextView : MaterialTextView
    private lateinit var descriptionTextView : MaterialTextView
    private lateinit var solutionButton : Button

    @SuppressLint("StringFormatInvalid")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(R.layout.fragment_average_rate_return, container, false)

        initialInvestmentEditText = view.findViewById(R.id.editTextInitialInvestment)
        finalValueEditText = view.findViewById(R.id.editTextFinalValue)
        calculateButton = view.findViewById(R.id.buttonCalculateAverageRateOfReturn)
        resultTextView = view.findViewById(R.id.textViewAverageRateOfReturnResult)
        descriptionTextView = view.findViewById(R.id.description)
        solutionButton = view.findViewById(R.id.buttonExplanation)

        calculateButton.setOnClickListener {
            calculateAverageRateOfReturn()
        }

        solutionButton.setOnClickListener {
            showExplanationDialog()
        }

        setDescription()

        return view
    }

    @SuppressLint("StringFormatInvalid")
    private fun calculateAverageRateOfReturn() {
        val initialInvestment = initialInvestmentEditText.text.toString().toDoubleOrNull()
        val finalValue = finalValueEditText.text.toString().toDoubleOrNull()

        if (initialInvestment == null || finalValue == null || initialInvestment == 0.0) {
            resultTextView.text = getString(R.string.invalid_input)
            return
        }

        val averageRateOfReturn = ((finalValue - initialInvestment) / initialInvestment) * 100

        resultTextView.text = getString(R.string.average_rate_of_return_result, averageRateOfReturn)
    }

    private fun setDescription() {
        val explanation = """
            The Average Rate of Return is a financial metric that calculates the average return on investment over a specified time period. The formula for Average Rate of Return is:
        
            Average Rate of Return = (Final Value - Initial Investment) / Initial Investment * 100%
        
            Where:
            - Initial Investment is the initial amount invested.
            - Final Value is the final value of the investment.
        
            Let's consider an example to illustrate the calculation:
        
            Suppose you invested ₱10,000 and the investment grew to ₱12,000 over 5 years.
        
            Average Rate of Return = (₱12,000 - ₱10,000) / ₱10,000 * 100%
            = ₱2,000 / ₱10,000 * 100%
            = 20%
        
            In this example, the Average Rate of Return is 20%.
        """.trimIndent()
        descriptionTextView.text = explanation
    }

    private fun showExplanationDialog() {
        val initialInvestment = initialInvestmentEditText.text.toString().toDoubleOrNull()
        val finalValue = finalValueEditText.text.toString().toDoubleOrNull()

        if (initialInvestment == null || finalValue == null || initialInvestment == 0.0) {
            resultTextView.text = getString(R.string.invalid_input)
            return
        }

        val averageRateOfReturn = ((finalValue - initialInvestment) / initialInvestment) * 100

        val explanation = """
            The Average Rate of Return is a financial metric that calculates the average return on investment over a specified time period. The formula for Average Rate of Return is:

            Average Rate of Return = (Final Value - Initial Investment) / Initial Investment * 100%

            Given:
                Initial Investment = ₱$initialInvestment
                Final Value = ₱$finalValue

            Solution:
                Average Rate of Return = (($finalValue - $initialInvestment) / $initialInvestment) * 100%
                                       = $averageRateOfReturn%

            Therefore, the Average Rate of Return is $averageRateOfReturn%.
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Average Rate of Return Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.ijs.abminder.R.string.calculator)
        super.onDestroy()
    }
}