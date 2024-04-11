package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.button.*
import com.google.android.material.dialog.*
import com.google.android.material.textfield.*
import com.google.android.material.textview.*
import com.mtcdb.stem.mathtrix.*
import com.mtcdb.stem.mathtrix.calculator.*

class OperatingCashFlowRatioFragment : Fragment() {

    private lateinit var netOperatingCashFlowEditText : TextInputEditText
    private lateinit var currentLiabilitiesEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var descriptionTextView : TextView
    private lateinit var buttonSolution : Button

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_operating_cash_flow_ratio,
            container,
            false
        )

        // Initialize UI components
        netOperatingCashFlowEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextNetOperatingCashFlow)
        currentLiabilitiesEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextCurrentLiabilities)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateOperatingCashFlowRatio)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewOperatingCashFlowRatioResult)
        descriptionTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.tVDescription)
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution)

        calculateButton.setOnClickListener {
            calculateOperatingCashFlowRatio()
        }

        val description = """
            The Operating Cash Flow Ratio is a liquidity ratio that measures a company's ability to cover its short-term liabilities with its operating cash flow. It is calculated using the formula:
            
            Operating Cash Flow Ratio = Net Operating Cash Flow / Current Liabilities
            
            Where:
            - Net Operating Cash Flow is the excess of cash inflows from operating activities over cash outflows.
            - Current Liabilities are the company's obligations that are expected to be settled within one year.
            
            A higher Operating Cash Flow Ratio indicates a better ability to cover short-term obligations with operating cash flow.
        """.trimIndent()

        descriptionTextView.text = description

        buttonSolution.setOnClickListener {
            val netOperatingCashFlow =
                netOperatingCashFlowEditText.text.toString().toDoubleOrNull() ?: 0.0
            val currentLiabilities =
                currentLiabilitiesEditText.text.toString().toDoubleOrNull() ?: 0.0

            val operatingCashFlowRatio = netOperatingCashFlow / currentLiabilities

            resultTextView.text =
                getString(
                    com.calculator.calculatoroptions.R.string.operating_cash_flow_ratio_result,
                    operatingCashFlowRatio
                )
            showExplanationDialog(netOperatingCashFlow, currentLiabilities, operatingCashFlowRatio)
        }

        return view
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.mtcdb.stem.mathtrix.R.string.calculator)
        super.onDestroy()
    }

    private fun calculateOperatingCashFlowRatio() {
        val netOperatingCashFlow =
            netOperatingCashFlowEditText.text.toString().toDoubleOrNull() ?: 0.0
        val currentLiabilities =
            currentLiabilitiesEditText.text.toString().toDoubleOrNull() ?: 0.0

        val operatingCashFlowRatio = netOperatingCashFlow / currentLiabilities

        resultTextView.text =
            getString(
                com.calculator.calculatoroptions.R.string.operating_cash_flow_ratio_result,
                operatingCashFlowRatio
            )
    }

    private fun showExplanationDialog(
        netOperatingCashFlow : Double,
        currentLiabilities : Double,
        operatingCashFlowRatio : Double,
    ) {
        val explanation = """
            The Operating Cash Flow Ratio is a liquidity ratio that assesses a company's ability to cover its short-term liabilities with its operating cash flow. It is calculated using the formula:
            
            Operating Cash Flow Ratio = Net Operating Cash Flow / Current Liabilities
            
            Given:
                Net Operating Cash Flow = $netOperatingCashFlow
                Current Liabilities = $currentLiabilities
                
            Solution:
                Operating Cash Flow Ratio = $netOperatingCashFlow / $currentLiabilities
                Operating Cash Flow Ratio = $operatingCashFlowRatio
                
            Therefore, the Operating Cash Flow Ratio is $operatingCashFlowRatio.
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Operating Cash Flow Ratio Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
