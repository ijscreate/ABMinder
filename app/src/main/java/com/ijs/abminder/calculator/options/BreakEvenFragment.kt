package com.ijs.abminder.calculator.options

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

class BreakEvenFragment : Fragment() {

    private lateinit var fixedCostEditText : EditText
    private lateinit var variableCostEditText : EditText
    private lateinit var sellingPriceEditText : EditText
    private lateinit var calculateButton : Button
    private lateinit var resetButton : Button
    private lateinit var resultTextView : TextView
    private lateinit var explainButton : Button
    private lateinit var descriptionTextView : TextView

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val rootView =
            inflater.inflate(
                com.calculator.calculatoroptions.R.layout.fragment_break_even,
                container,
                false
            )

        fixedCostEditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextFixedCost)
        variableCostEditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextVariableCost)
        sellingPriceEditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextSellingPrice)
        calculateButton =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateBreakEven)
        resetButton =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.buttonResetBreakEven)
        resultTextView =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.textViewBreakEvenOutput)
        explainButton =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.explainBreakEvenButton)
        descriptionTextView =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.tVBreakEvenDescription)

        val description = """
            Break-Even Analysis is a financial tool used to determine the point at which total revenue equals total costs, indicating the level of sales needed to cover all expenses.
        
            Given:
            - Fixed Cost: The total expenses that remain constant regardless of the level of production or sales. This includes rent, salaries, and utilities.
            - Variable Cost: The costs that vary with the level of production or sales. This includes raw materials and direct labor.
            - Selling Price: The price at which a product or service is sold to customers.
        
            Formula:
            - Break-Even Point (units) = Fixed Cost / (Selling Price - Variable Cost): The number of units that need to be sold to cover all costs and reach the break-even point.
            - Break-Even Point (sales) = Fixed Costs ÷ Contribution Margin: The sales revenue needed to cover all costs and reach the break-even point.
        
            Example:
            Suppose a company has fixed costs of ₱50,000, variable costs of ₱20 per unit, and sells its product for ₱100 each.
        
            Using the Break-Even Point (units) formula:
            Break-Even Point (units) = ₱50,000 / (₱100 - ₱20) = 625 units
        
            Therefore, the company needs to sell 625 units to cover all costs and break even.
        
            Using the Break-Even Point (sales) formula:
            Break-Even Point (sales) = ₱50,000 / (₱100 - ₱20) / ₱100 = ₱83,333.33
        
            Therefore, the company needs to generate ₱83,333.33 in sales revenue to cover all costs and break even.
        """.trimIndent()


        descriptionTextView.text = description

        calculateButton.setOnClickListener {
            calculateBreakEven()
        }

        resetButton.setOnClickListener {
            resetInputs()
        }

        explainButton.setOnClickListener {
            showExplanationDialog()
        }

        return rootView
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }

    private fun calculateBreakEven() {
        val fixedCost = fixedCostEditText.text.toString().toDoubleOrNull() ?: 0.0
        val variableCost = variableCostEditText.text.toString().toDoubleOrNull() ?: 0.0
        val sellingPrice = sellingPriceEditText.text.toString().toDoubleOrNull() ?: 0.0

        if (variableCost == 0.0) {
            resultTextView.text = getString(R.string.invalid_variable_cost)
            return
        }

        val breakEvenQuantity = fixedCost / (sellingPrice - variableCost)
        resultTextView.text = breakEvenQuantity.toString()
    }

    private fun resetInputs() {
        fixedCostEditText.text.clear()
        variableCostEditText.text.clear()
        sellingPriceEditText.text.clear()
        resultTextView.text = ""
    }

    private fun showExplanationDialog() {
        val fixedCost = fixedCostEditText.text.toString().toDoubleOrNull() ?: 0.0
        val variableCost = variableCostEditText.text.toString().toDoubleOrNull() ?: 0.0
        val sellingPrice = sellingPriceEditText.text.toString().toDoubleOrNull() ?: 0.0

        val breakEvenQuantity = fixedCost / (sellingPrice - variableCost)
        val contributionMargin = (sellingPrice - variableCost) / sellingPrice
        val sales = fixedCost / contributionMargin

        val explanation = """
        Break-Even Analysis helps determine the point at which total revenue equals total costs.
                   
        Given:
            Fixed Cost = $fixedCost
            Variable Cost = $variableCost
            Selling Price = $sellingPrice
            
        Formula:
            Break-Even Point (units) = Fixed Cost / (Selling Price - Variable Cost)
            Break-Even Point (sales) = Fixed costs ÷ Contribution Margin
            
        Solution:
            Break-Even Point (units) = $fixedCost / ($sellingPrice - $variableCost)
            Break-Even Point = $breakEvenQuantity units
            
            Contribution Margin = ($sellingPrice - $variableCost) / $sellingPrice
            Break-Even Point (sales) = $fixedCost ÷ $contributionMargin
            Break-Even Point (sales) = $sales
                        
        Therefore, the Break-Even Point in units is $breakEvenQuantity and $sales in sales.
    """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Break-Even Analysis")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
