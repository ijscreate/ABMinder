package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.dialog.*
import com.google.android.material.textfield.*
import com.google.android.material.textview.*
import com.mtcdb.stem.mathtrix.*
import kotlin.math.*

class DiscountedCashFlowFragment : Fragment() {

    private lateinit var initialInvestmentEditText : TextInputEditText
    private lateinit var cashFlowsEditText : TextInputEditText
    private lateinit var discountRateEditText : TextInputEditText
    private lateinit var calculateButton : Button
    private lateinit var resultTextView : MaterialTextView

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_discounted_cash_flow,
            container,
            false
        )

        // Initialize UI components
        initialInvestmentEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextInitialInvestmentDCF)
        cashFlowsEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextCashFlowsDCF)
        discountRateEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextDiscountRateDCF)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateDCF)
        resultTextView = view.findViewById(com.calculator.calculatoroptions.R.id.textViewDCFResult)

        // Set up event listeners
        calculateButton.setOnClickListener {
            calculateDiscountedCashFlow()
        }

        // Add a text change listener to cash flows edit text for example formatting
        //cashFlowsEditText.doOnTextChanged { text, _, _, _ ->
        //    formatExampleCashFlows(text.toString())
        //}

        formatExampleCashFlows(cashFlowsEditText.text.toString())

        return view
    }

    override fun onDestroy() {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }

    private fun calculateDiscountedCashFlow() {
        val initialInvestment = initialInvestmentEditText.text.toString().toDoubleOrNull() ?: 0.0
        val discountRate = discountRateEditText.text.toString().toDoubleOrNull() ?: 0.0
        val cashFlowsText = cashFlowsEditText.text.toString()

        val cashFlows = parseCashFlows(cashFlowsText)

        val discountedCashFlow = calculateDCF(discountRate, cashFlows)

        resultTextView.text = discountedCashFlow.toString()
        showExplanationDialog(initialInvestment, discountRate, cashFlows, discountedCashFlow)
    }

    private fun calculateDCF(discountRate : Double, cashFlows : List<Double>) : Double {
        var dcf = 0.0

        for ((index, cashFlow) in cashFlows.withIndex()) {
            val discountedValue = cashFlow / (1 + discountRate).pow(index + 1)
            dcf += discountedValue
        }

        return dcf
    }


    private fun parseCashFlows(cashFlowsText : String) : List<Double> {
        val cashFlowStrings = cashFlowsText.split("\n", ",", ";", "\\s+")
        return cashFlowStrings.map { it.toDoubleOrNull() ?: 0.0 }
    }

    private fun formatExampleCashFlows(inputText : String) {
        if (inputText.isBlank()) return

        // Format example cash flows
        val exampleCashFlows = inputText.split("\n", ",", ";", "\\s+")
            .map { it.toDoubleOrNull() ?: 0.0 }
            .joinToString(separator = "\n", transform = { "%.2f".format(it) })

        cashFlowsEditText.setText(exampleCashFlows)
    }

    private fun showExplanationDialog(
        initialInvestment : Double,
        discountRate : Double,
        cashFlows : List<Double>,
        discountedCashFlow : Double,
    ) {
        val explanation = """
            The Discounted Cash Flow (DCF) is a financial valuation method used to estimate the value of an investment based on its expected future cash flows. It is calculated using the formula:
            
            DCF = -Initial Investment + (Cash Flow / (1 + Discount Rate)^(Time Period))
            
            Given:
                Initial Investment = $initialInvestment
                Discount Rate = $discountRate
                Cash Flows = ${cashFlows.joinToString(", ")}
                
            Formulas:
                DCF = -Initial Investment + (Cash Flow1 / (1 + Discount Rate)^1) + (Cash Flow2 / (1 + Discount Rate)^2) + ...
                
            Solution:
                DCF = -$initialInvestment + ${
            cashFlows.joinToString(" + ") {
                "(%.2f / (1 + $discountRate)^${
                    cashFlows.indexOf(
                        it
                    ) + 1
                })"
            }
        }
                DCF = $discountedCashFlow
                
            Therefore, the Discounted Cash Flow is $discountedCashFlow.
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Discounted Cash Flow (DCF)")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
