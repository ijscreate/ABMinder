package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.dialog.*
import com.mtcdb.stem.mathtrix.*

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
        val mainActivity = requireActivity() as MainActivity
        mainActivity.toolbar.title = getString(R.string.calculator)
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
