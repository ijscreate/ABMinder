package com.mtcdb.stem.mathtrix.calculator.options

import android.annotation.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.dialog.*
import com.mtcdb.stem.mathtrix.*
import com.mtcdb.stem.mathtrix.calculator.*

class ROIFragment : Fragment() {

    private lateinit var initialInvestmentEditText : EditText
    private lateinit var returnEditText : EditText
    private lateinit var calculateButton : Button
    private lateinit var resultTextView : TextView
    private lateinit var explainButton : Button
    private lateinit var descriptionTextView : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val rootView =
            inflater.inflate(
                com.calculator.calculatoroptions.R.layout.fragment_roi,
                container,
                false
            )

        initialInvestmentEditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextInvestment)
        returnEditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextNetIncome)
        calculateButton =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateROI)
        resultTextView =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.textViewROIResult)
        descriptionTextView =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.tVROIDescription)
        explainButton =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.explainROIButton)

        calculateButton.setOnClickListener {
            calculateButton.clearFocus()
            calculateROI()
        }

        explainButton.setOnClickListener {
            showExplanationDialog()
            calculateROI()
        }

        val description = """
            Return on Investment (ROI) is calculated using the formula:
            
            ROI = ((Return - Initial Investment) / Initial Investment) * 100
            
            Where:
            ROI = Return on Investment
            Return = Total return or profit
            Initial Investment = Initial amount invested
            
            ROI is expressed as a percentage.
            
            Example:
            Suppose you invest ₱10,000 and your investment grows to ₱15,000.
            
            ROI = ((₱15,000 - ₱10,000) / ₱10,000) * 100
            ROI = 50%
            
            Therefore, the Return on Investment is 50%.
        """.trimIndent()


        descriptionTextView.text = description

        return rootView
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.mtcdb.stem.mathtrix.R.string.calculator)
        super.onDestroy()
    }

    @SuppressLint("StringFormatInvalid")
    private fun calculateROI() {
        val initialInvestment = initialInvestmentEditText.text.toString().toDoubleOrNull() ?: 0.0
        val returnAmount = returnEditText.text.toString().toDoubleOrNull() ?: 0.0

        val roi = ((returnAmount - initialInvestment) / initialInvestment) * 100
        roi.toString()

        resultTextView.text = getString(R.string.rOI, roi)
    }

    private fun showExplanationDialog() {
        val initialInvestment = initialInvestmentEditText.text.toString().toDoubleOrNull() ?: 0.0
        val returnAmount = returnEditText.text.toString().toDoubleOrNull() ?: 0.0

        val explanation = """
            Given:
                Initial Investment = $initialInvestment
                Return = $returnAmount
                
            Formula:
                ROI = ((Return - Initial Investment) / Initial Investment) * 100
                
            Solution:
                ROI = (($returnAmount - $initialInvestment) / $initialInvestment) * 100
                ROI = ${(returnAmount - initialInvestment) / initialInvestment * 100}%
               
            Therefore, the Return on Investment is ${(returnAmount - initialInvestment) / initialInvestment * 100}%
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Return on Investment")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
