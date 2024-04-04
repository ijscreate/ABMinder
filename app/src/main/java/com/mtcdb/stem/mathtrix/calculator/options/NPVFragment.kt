package com.mtcdb.stem.mathtrix.calculator.options

import android.annotation.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.dialog.*
import com.mtcdb.stem.mathtrix.*
import kotlin.math.*

class NPVFragment : Fragment() {

    private lateinit var initialInvestmentEditText : EditText
    private lateinit var rateEditText : EditText
    private lateinit var calculateButton : Button
    private lateinit var resultTextView : TextView
    private lateinit var explainButton : Button
    private lateinit var descriptionTextView : TextView
    private lateinit var cashFlow1EditText : EditText
    private lateinit var cashFlow2EditText : EditText
    private lateinit var cashFlow3EditText : EditText
    private lateinit var cashFlow4EditText : EditText
    private lateinit var cashFlow5EditText : EditText
    private lateinit var cashFlow6EditText : EditText
    private lateinit var cashFlow7EditText : EditText
    private lateinit var cashFlow8EditText : EditText
    private lateinit var cashFlow9EditText : EditText
    private lateinit var cashFlow10EditText : EditText
    private lateinit var numberOfYearsEditText : EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val rootView =
            inflater.inflate(
                com.calculator.calculatoroptions.R.layout.fragment_npv,
                container,
                false
            )

        initialInvestmentEditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextInitialInvestment)
        rateEditText = rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextRate)
        calculateButton =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculate)
        resultTextView =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.textViewNPVResult)
        descriptionTextView =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.tVNPVDescription)
        explainButton =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.explainNPVButton)
        cashFlow1EditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextCashFlow)
        cashFlow2EditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextCashFlow2)
        cashFlow3EditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextCashFlow3)
        cashFlow4EditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextCashFlow4)
        cashFlow5EditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextCashFlow5)
        cashFlow6EditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextCashFlow6)
        cashFlow7EditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextCashFlow7)
        cashFlow8EditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextCashFlow8)
        cashFlow9EditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextCashFlow9)
        cashFlow10EditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextCashFlow10)
        numberOfYearsEditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextNumberOfYears)
        cashFlow1EditText.visibility = View.GONE
        cashFlow2EditText.visibility = View.GONE
        cashFlow3EditText.visibility = View.GONE
        cashFlow4EditText.visibility = View.GONE
        cashFlow5EditText.visibility = View.GONE
        cashFlow6EditText.visibility = View.GONE
        cashFlow7EditText.visibility = View.GONE
        cashFlow8EditText.visibility = View.GONE
        cashFlow9EditText.visibility = View.GONE
        cashFlow10EditText.visibility = View.GONE

        calculateButton.setOnClickListener {
            calculateButton.clearFocus()
            calculateNetPresentValue()
        }

        explainButton.setOnClickListener {
            showExplanationDialog()
            calculateNetPresentValue()
        }

        val btnYears = rootView.findViewById<Button>(com.calculator.calculatoroptions.R.id.btnYears)
        btnYears.setOnClickListener {
            val numberOfYears = numberOfYearsEditText.text.toString().toIntOrNull() ?: 0

            hideExcessCashFlowFields(numberOfYears)

            for (i in 0 until numberOfYears) {
                val editTextId = getCashFlowEditTextId(i + 1)
                val editText = rootView.findViewById<EditText>(editTextId)
                editText.visibility = View.VISIBLE
            }
        }


        val description = """
            Net Present Value (NPV) is calculated using the formula:
            
            NPV = Σ[CFt / (1 + r)^t] - C0
            
            Where:
            NPV = Net Present Value
            CFt = Cash flow at time t
            r = Discount rate
            t = Time period
            C0 = Initial investment
            
            To calculate NPV, sum the discounted cash flows and subtract the initial investment.
            
            Example:
            Suppose you have an initial investment of $10,000 and expect cash flows of $3,000, $4,000, $5,000, $6,000, and $7,000 at the end of years 1, 2, 3, 4, and 5 respectively, with a discount rate of 10%.
            
            NPV = (3000 / (1 + 0.1)^1) + (4000 / (1 + 0.1)^2) + (5000 / (1 + 0.1)^3) + (6000 / (1 + 0.1)^4) + (7000 / (1 + 0.1)^5) - 10000
            NPV = 8234.16
            
            Therefore, the Net Present value is 8234.16.
        """.trimIndent()

        descriptionTextView.text = description

        return rootView
    }

    override fun onDestroy() {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }

    private fun hideExcessCashFlowFields(numberOfYears : Int) {
        val cashFlowEditTexts = mutableListOf(
            cashFlow1EditText, cashFlow2EditText, cashFlow3EditText, cashFlow4EditText,
            cashFlow5EditText, cashFlow6EditText, cashFlow7EditText, cashFlow8EditText,
            cashFlow9EditText, cashFlow10EditText
        )

        // Hide EditText fields beyond the specified number of years
        for (i in numberOfYears until cashFlowEditTexts.size) {
            cashFlowEditTexts[i].visibility = View.GONE
        }
    }


    @SuppressLint("StringFormatInvalid")
    private fun calculateNetPresentValue() {

        val initialInvestment = initialInvestmentEditText.text.toString().toDoubleOrNull() ?: 0.0
        val rate = rateEditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf1 = cashFlow1EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf2 = cashFlow2EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf3 = cashFlow3EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf4 = cashFlow4EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf5 = cashFlow5EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf6 = cashFlow6EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf7 = cashFlow7EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf8 = cashFlow8EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf9 = cashFlow9EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf10 = cashFlow10EditText.text.toString().toDoubleOrNull() ?: 0.0

        val rated = rate / 100
        val numberOfYears = numberOfYearsEditText.text.toString().toIntOrNull() ?: 0

        val npv1 = if (numberOfYears >= 1) cf1 / (1 + rated).pow(1) else 0.0
        val npv2 = if (numberOfYears >= 2) cf2 / (1 + rated).pow(2) else 0.0
        val npv3 = if (numberOfYears >= 3) cf3 / (1 + rated).pow(3) else 0.0
        val npv4 = if (numberOfYears >= 4) cf4 / (1 + rated).pow(4) else 0.0
        val npv5 = if (numberOfYears >= 5) cf5 / (1 + rated).pow(5) else 0.0
        val npv6 = if (numberOfYears >= 6) cf6 / (1 + rated).pow(6) else 0.0
        val npv7 = if (numberOfYears >= 7) cf7 / (1 + rated).pow(7) else 0.0
        val npv8 = if (numberOfYears >= 8) cf8 / (1 + rated).pow(8) else 0.0
        val npv9 = if (numberOfYears >= 9) cf9 / (1 + rated).pow(9) else 0.0
        val npv10 = if (numberOfYears >= 10) cf10 / (1 + rated).pow(10) else 0.0

        val npv =
            npv1 + npv2 + npv3 + npv4 + npv5 + npv6 + npv7 + npv8 + npv9 + npv10 - initialInvestment

        resultTextView.text = npv.toString()
    }

    private fun getCashFlowEditTextId(index : Int) : Int {
        return when (index) {
            1 -> com.calculator.calculatoroptions.R.id.editTextCashFlow
            2 -> com.calculator.calculatoroptions.R.id.editTextCashFlow2
            3 -> com.calculator.calculatoroptions.R.id.editTextCashFlow3
            4 -> com.calculator.calculatoroptions.R.id.editTextCashFlow4
            5 -> com.calculator.calculatoroptions.R.id.editTextCashFlow5
            6 -> com.calculator.calculatoroptions.R.id.editTextCashFlow6
            7 -> com.calculator.calculatoroptions.R.id.editTextCashFlow7
            8 -> com.calculator.calculatoroptions.R.id.editTextCashFlow8
            9 -> com.calculator.calculatoroptions.R.id.editTextCashFlow9
            10 -> com.calculator.calculatoroptions.R.id.editTextCashFlow10
            else -> com.calculator.calculatoroptions.R.id.editTextCashFlow
        }
    }

    private fun showExplanationDialog() {
        val initialInvestment = initialInvestmentEditText.text.toString().toDoubleOrNull() ?: 0.0
        val rate = rateEditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf1 = cashFlow1EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf2 = cashFlow2EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf3 = cashFlow3EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf4 = cashFlow4EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf5 = cashFlow5EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf6 = cashFlow6EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf7 = cashFlow7EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf8 = cashFlow8EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf9 = cashFlow9EditText.text.toString().toDoubleOrNull() ?: 0.0
        val cf10 = cashFlow10EditText.text.toString().toDoubleOrNull() ?: 0.0

        val cashFlows = listOf(
            cf1, cf2, cf3, cf4, cf5, cf6, cf7, cf8, cf9, cf10
        )

        val rated = rate / 100

        val numberOfYears = numberOfYearsEditText.text.toString().toIntOrNull() ?: 0
        val npv1 = if (numberOfYears >= 1) cf1 / (1 + rated).pow(1) else 0.0
        val npv2 = if (numberOfYears >= 2) cf2 / (1 + rated).pow(2) else 0.0
        val npv3 = if (numberOfYears >= 3) cf3 / (1 + rated).pow(3) else 0.0
        val npv4 = if (numberOfYears >= 4) cf4 / (1 + rated).pow(4) else 0.0
        val npv5 = if (numberOfYears >= 5) cf5 / (1 + rated).pow(5) else 0.0
        val npv6 = if (numberOfYears >= 6) cf5 / (1 + rated).pow(6) else 0.0
        val npv7 = if (numberOfYears >= 7) cf5 / (1 + rated).pow(7) else 0.0
        val npv8 = if (numberOfYears >= 8) cf5 / (1 + rated).pow(8) else 0.0
        val npv9 = if (numberOfYears >= 9) cf5 / (1 + rated).pow(9) else 0.0
        val npv10 = if (numberOfYears >= 10) cf5 / (1 + rated).pow(10) else 0.0

        val cashFlow = npv1 + npv2 + npv3 + npv4 + npv5 + npv6 + npv7 + npv8 + npv9 + npv10
        val npv =
            npv1 + npv2 + npv3 + npv4 + npv5 + npv6 + npv7 + npv8 + npv9 + npv10 - initialInvestment

        val explanation = """
            Given:
                Initial Investment = $initialInvestment
                Discount Rate = $rate%
                Cash Flows = $cashFlows
                
            Formula:
                NPV = Σ[CFt / (1 + r)^t] - C0
                
            Solution:
                NPV = $cf1 / (1 + $rate% )^1 + $cf2 / (1 + $rate% )^2 + $cf3 / (1 + $rate% )^3 + $cf4 / (1 + $rate% )^4 + $cf5 / (1 + $rate% )^5 - $initialInvestment
                NPV = $npv1 + $npv2 + $npv3 + $npv4 + $npv5 - $initialInvestment
                NPV = $cashFlow - $initialInvestment
                NPV = $npv
               
            Therefore, the Net Present Value is $npv
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Net Present Value")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
