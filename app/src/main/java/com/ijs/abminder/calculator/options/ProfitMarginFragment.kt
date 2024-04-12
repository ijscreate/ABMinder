package com.ijs.abminder.calculator.options

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ijs.abminder.R
import com.ijs.abminder.calculator.CalculatorOptionsActivity

class ProfitMarginFragment : Fragment() {

    private lateinit var costPriceEditText : EditText
    private lateinit var sellingPriceEditText : EditText
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
                com.calculator.calculatoroptions.R.layout.fragment_profit_margin,
                container,
                false
            )

        costPriceEditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextCostPrice)
        sellingPriceEditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextSellingPrice)
        calculateButton =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculate)
        resultTextView = rootView.findViewById(com.calculator.calculatoroptions.R.id.textViewResult)
        descriptionTextView =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.tVCalculationOptionDescription)
        explainButton = rootView.findViewById(com.calculator.calculatoroptions.R.id.explainButton)

        calculateButton.setOnClickListener {
            calculateButton.clearFocus()
            calculateProfitMargin()
        }

        explainButton.setOnClickListener {
            showExplanationDialog()
            calculateProfitMargin()
        }

        val description = """
            Profit Margin is calculated using the formula:
            
            Profit Margin = ((Selling Price - Cost Price) / Selling Price) * 100
            
            Where:
            Profit Margin = Profit Percentage
            Selling Price = Price at which the product is sold
            Cost Price = Price at which the product was bought
            
            To calculate Profit Margin, subtract the Cost Price from the Selling Price, divide by the Selling Price, and then multiply by 100.
            
            Example:
            Suppose you buy a product for $80 and sell it for $100.
            
            Profit Margin = (($100 - $80) / $100) * 100
            Profit Margin = 20%
        """.trimIndent()

        descriptionTextView.text = description

        return rootView
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }

    @SuppressLint("StringFormatInvalid")
    private fun calculateProfitMargin() {
        val costPrice = costPriceEditText.text.toString().toDoubleOrNull() ?: 0.0
        val sellingPrice = sellingPriceEditText.text.toString().toDoubleOrNull() ?: 0.0

        val profitMargin = ((sellingPrice - costPrice) / sellingPrice) * 100
        profitMargin.toInt()

        resultTextView.text = getString(R.string.profitmar, profitMargin)
    }

    private fun showExplanationDialog() {
        val costPrice = costPriceEditText.text.toString().toDoubleOrNull() ?: 0.0
        val sellingPrice = sellingPriceEditText.text.toString().toDoubleOrNull() ?: 0.0

        val profitMargin = ((sellingPrice - costPrice) / sellingPrice) * 100

        val explanation = """
            Given:
                Cost Price = $costPrice
                Selling Price = $sellingPrice
                
            Formula:
                Profit Margin = ((Selling Price - Cost Price) / Selling Price) * 100
                
            Solution:
                Profit Margin = (($sellingPrice - $costPrice) / $sellingPrice) * 100
                Profit Margin = $profitMargin%
                
            Therefore, the Profit Margin is $profitMargin%
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Profit Margin")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
