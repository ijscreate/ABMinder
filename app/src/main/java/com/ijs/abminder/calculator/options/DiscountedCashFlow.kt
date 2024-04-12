package com.ijs.abminder.calculator.options

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.ijs.abminder.calculator.CalculatorOptionsActivity
import kotlin.math.pow

class DiscountedCashFlowFragment : Fragment() {

    private lateinit var initialInvestmentEditText : TextInputEditText
    private lateinit var cashFlowsEditText : TextInputEditText
    private lateinit var discountRateEditText : TextInputEditText
    private lateinit var calculateButton : Button
    private lateinit var resultTextView : MaterialTextView
    private lateinit var buttonSolution : Button
    private lateinit var descriptionTextView : MaterialTextView

    @SuppressLint("MissingInflatedId")
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
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution)
        descriptionTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewDescription)

        val desc = """
            The Discounted Cash Flow (DCF) analysis is a financial valuation method used to estimate the present value of future cash flows from an investment. It considers the time value of money by discounting future cash flows back to their present value using a discount rate. The DCF calculation helps investors assess the attractiveness of an investment opportunity by determining its intrinsic value.
        
            Example:
        
            Consider a project with the following cash flows over a 5-year period:
            Year 1: ₱10,000
            Year 2: ₱15,000
            Year 3: ₱20,000
            Year 4: ₱25,000
            Year 5: ₱30,000
        
            Assuming a discount rate of 10% per annum, the DCF calculation would be as follows:
        
            1. Calculate the Present Value (PV) of each cash flow:
               PV1 = ₱10,000 / (1 + 0.10)^1 = ₱9,090.91
               PV2 = ₱15,000 / (1 + 0.10)^2 = ₱12,396.69
               PV3 = ₱20,000 / (1 + 0.10)^3 = ₱14,877.19
               PV4 = ₱25,000 / (1 + 0.10)^4 = ₱17,130.65
               PV5 = ₱30,000 / (1 + 0.10)^5 = ₱18,371.73
        
            2. Sum up the Present Values of all cash flows:
               DCF = PV1 + PV2 + PV3 + PV4 + PV5
                   = ₱9,090.91 + ₱12,396.69 + ₱14,877.19 + ₱17,130.65 + ₱18,371.73
                   = ₱71,867.17
        
            Therefore, the Discounted Cash Flow (DCF) for this project is approximately ₱71,867.17. This represents the present value of the future cash flows generated by the investment, discounted at a rate of 10% per annum.
        """.trimIndent()

        descriptionTextView.text = desc


        // Set up event listeners
        calculateButton.setOnClickListener {
            calculateDiscountedCashFlow()
        }

        buttonSolution.setOnClickListener {
            val initialInvestment =
                initialInvestmentEditText.text.toString().toDoubleOrNull() ?: 0.0
            val discountRate = discountRateEditText.text.toString().toDoubleOrNull() ?: 0.0
            val cashFlowsText = cashFlowsEditText.text.toString()
            val cashFlows = parseCashFlows(cashFlowsText)
            val discountedCashFlow = calculateDCF(discountRate, cashFlows)
            resultTextView.text = discountedCashFlow.toString()
            showExplanationDialog(initialInvestment, discountRate, cashFlows, discountedCashFlow)
        }

        // Format example cash flows
        formatExampleCashFlows(cashFlowsEditText.text.toString())

        return view
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.ijs.abminder.R.string.calculator)
        super.onDestroy()
    }

    private fun calculateDiscountedCashFlow() {
        val discountRate = discountRateEditText.text.toString().toDoubleOrNull() ?: 0.0
        val cashFlowsText = cashFlowsEditText.text.toString()
        val cashFlows = parseCashFlows(cashFlowsText)
        val discountedCashFlow = calculateDCF(discountRate, cashFlows)
        resultTextView.text = discountedCashFlow.toString()
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

        DCF = -Initial Investment + Σ (Cash Flow / (1 + Discount Rate)^(Time Period))

        Given:
            Initial Investment = $initialInvestment
            Discount Rate = $discountRate%
            Cash Flows = ${cashFlows.joinToString(", ")}

        Step-by-step Calculation:

        1. Calculate the Present Value (PV) of each cash flow:
           PV = Cash Flow / (1 + Discount Rate)^(Time Period)

        2. Sum up the Present Values of all cash flows:
           DCF = -Initial Investment + Σ (PV)

        Formulas:
            PV = Cash Flow / (1 + Discount Rate)^(Time Period)
            DCF = -Initial Investment + Σ (Cash Flow / (1 + Discount Rate)^(Time Period))

        Calculation:

        """.trimIndent() + cashFlows.mapIndexed { index, cashFlow ->
            "        PV${index + 1} = ${"%.2f".format(cashFlow)} / (1 + $discountRate)^${index + 1}\n"
        }.joinToString("") + """
        """.trimIndent() + """
                DCF = -$initialInvestment + ${cashFlows.indices.joinToString(" + ") { "PV${it + 1}" }}

        Result:
            DCF = $discountedCashFlow

        Therefore, the Discounted Cash Flow is $discountedCashFlow.
    """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Discounted Cash Flow (DCF) Calculation Explanation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }

}
