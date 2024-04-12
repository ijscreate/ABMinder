package com.ijs.abminder.calculator.options

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

class CashConversionCycleFragment : Fragment() {

    private lateinit var daysInventoryEditText : TextInputEditText
    private lateinit var daysReceivablesEditText : TextInputEditText
    private lateinit var daysPayablesEditText : TextInputEditText
    private lateinit var calculateButton : Button
    private lateinit var explanationButton : Button
    private lateinit var resultTextView : MaterialTextView
    private lateinit var descriptionTextView : MaterialTextView

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(R.layout.fragment_cash_conversion_cycle, container, false)

        daysInventoryEditText = view.findViewById(R.id.editTextDaysInventory)
        daysReceivablesEditText = view.findViewById(R.id.editTextDaysReceivables)
        daysPayablesEditText = view.findViewById(R.id.editTextDaysPayables)
        calculateButton = view.findViewById(R.id.buttonCalculateCashConversionCycle)
        explanationButton = view.findViewById(R.id.buttonExplanation)
        resultTextView = view.findViewById(R.id.textViewCashConversionCycleResult)
        descriptionTextView = view.findViewById(R.id.description)

        calculateButton.setOnClickListener {
            calculateCashConversionCycle()
        }

        explanationButton.setOnClickListener {
            showExplanationDialog()
        }

        setDescription()

        return view
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.ijs.abminder.R.string.calculator)
        super.onDestroy()
    }

    private fun calculateCashConversionCycle() {
        val daysInventory = daysInventoryEditText.text.toString().toDoubleOrNull()
        val daysReceivables = daysReceivablesEditText.text.toString().toDoubleOrNull()
        val daysPayables = daysPayablesEditText.text.toString().toDoubleOrNull()

        if (daysInventory == null || daysReceivables == null || daysPayables == null) {
            resultTextView.text = getString(R.string.invalid_input)
            return
        }

        val cashConversionCycle = daysInventory + daysReceivables - daysPayables

        resultTextView.text = getString(R.string.cash_conversion_cycle_result, cashConversionCycle)
    }

    private fun setDescription() {
        val description = """
            The Cash Conversion Cycle (CCC) measures the time it takes for a company to convert its investments in inventory and other resources into cash flows from sales. It assesses the efficiency of a company's working capital management by evaluating how long it takes to convert resources into cash.
        
            Explanation:
            - Days Inventory Outstanding (DIO): Represents the average number of days it takes for a company to sell its inventory.
            - Days Sales Outstanding (DSO): Indicates the average number of days it takes for a company to collect payment from customers.
            - Days Payable Outstanding (DPO): Reflects the average number of days it takes for a company to pay its suppliers.
        
            Formula:
            Cash Conversion Cycle (CCC) = DIO + DSO - DPO
        
            To use this calculator:
            1. Enter the number of days for Days Inventory Outstanding (DIO), Days Sales Outstanding (DSO), and Days Payable Outstanding (DPO).
            2. Click on the Calculate button to determine the Cash Conversion Cycle.
        
            For example:
            If a company has:
            - Days Inventory Outstanding (DIO) of 30 days
            - Days Sales Outstanding (DSO) of 45 days
            - Days Payable Outstanding (DPO) of 20 days
        
            The Cash Conversion Cycle (CCC) would be:
            CCC = 30 days (DIO) + 45 days (DSO) - 20 days (DPO) = 55 days
        
            Therefore, it takes the company approximately 55 days to convert its investments in inventory and resources into cash flows from sales.
        """.trimIndent()

        descriptionTextView.text = description
    }

    private fun showExplanationDialog() {
        val daysInventory = daysInventoryEditText.text.toString().toDoubleOrNull() ?: 0.0
        val daysReceivables = daysReceivablesEditText.text.toString().toDoubleOrNull() ?: 0.0
        val daysPayables = daysPayablesEditText.text.toString().toDoubleOrNull() ?: 0.0

        val cashConversionCycle = daysInventory + daysReceivables - daysPayables

        val explanation = """
            The Cash Conversion Cycle (CCC) measures the time it takes for a company to convert its investments in inventory and other resources into cash flows from sales. It assesses the efficiency of a company's working capital management by evaluating how long it takes to convert resources into cash.
                       
            Given:
                Days Inventory Outstanding (DIO) = $daysInventory days
                Days Sales Outstanding (DSO) = $daysReceivables days
                Days Payable Outstanding (DPO) = $daysPayables days
                
            Formula:
                Cash Conversion Cycle (CCC) = DIO + DSO - DPO
                
            Solution:
                Cash Conversion Cycle (CCC) = $daysInventory + $daysReceivables - $daysPayables
                Cash Conversion Cycle (CCC) = $cashConversionCycle days
                            
            Therefore, the Cash Conversion Cycle (CCC) is $cashConversionCycle days.
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Cash Conversion Cycle (CCC) Analysis")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }

}
