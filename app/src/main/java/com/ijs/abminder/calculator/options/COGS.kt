package com.ijs.abminder.calculator.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.ijs.abminder.R
import com.ijs.abminder.calculator.CalculatorOptionsActivity

class COGSFragment : Fragment() {

    private lateinit var beginningInventoryEditText : TextInputEditText
    private lateinit var purchasesEditText : TextInputEditText
    private lateinit var endingInventoryEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var description : TextView
    private lateinit var buttonSolution : Button

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_cogs,
            container,
            false
        )

        // Initialize UI components
        beginningInventoryEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextBeginningInventory)
        purchasesEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextPurchases)
        endingInventoryEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextEndingInventory)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateCOGS)
        resultTextView = view.findViewById(com.calculator.calculatoroptions.R.id.textViewCOGSResult)
        description = view.findViewById(R.id.description)
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution)

        calculateButton.setOnClickListener {
            calculateCOGS()
        }

        val cogsDescription = """
            The Cost of Goods Sold (COGS) calculator computes the cost of goods sold during a specific period.

            COGS is an essential financial metric used to determine the direct costs associated with the production of goods sold by a company. The formula for calculating COGS is:

            COGS = Beginning Inventory + Purchases - Ending Inventory

            Where:
            - Beginning Inventory represents the value of goods held in inventory at the beginning of the accounting period.
            - Purchases represent the cost of acquiring additional inventory during the accounting period.
            - Ending Inventory represents the value of goods held in inventory at the end of the accounting period.

            Let's consider an example to illustrate the calculation:

            Suppose a company had a beginning inventory of ₱50,000, made purchases totaling ₱100,000 during the period, and had an ending inventory of ₱30,000.

            COGS = ₱50,000 + ₱100,000 - ₱30,000
                 = ₱120,000

            In this example, the Cost of Goods Sold is ₱120,000, indicating the direct costs associated with the production of goods sold during the period.
        """.trimIndent()

        description.text = cogsDescription

        buttonSolution.setOnClickListener {
            val beginningInventory =
                beginningInventoryEditText.text.toString().toDoubleOrNull() ?: 0.0
            val purchases = purchasesEditText.text.toString().toDoubleOrNull() ?: 0.0
            val endingInventory = endingInventoryEditText.text.toString().toDoubleOrNull() ?: 0.0

            val cogs = beginningInventory + purchases - endingInventory

            resultTextView.text =
                getString(
                    com.calculator.calculatoroptions.R.string.cogs_result,
                    cogs
                )
            showExplanationDialog(beginningInventory, purchases, endingInventory, cogs)

        }

        return view
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }

    private fun calculateCOGS() {
        val beginningInventory = beginningInventoryEditText.text.toString().toDoubleOrNull() ?: 0.0
        val purchases = purchasesEditText.text.toString().toDoubleOrNull() ?: 0.0
        val endingInventory = endingInventoryEditText.text.toString().toDoubleOrNull() ?: 0.0

        val cogs = beginningInventory + purchases - endingInventory

        resultTextView.text =
            getString(
                com.calculator.calculatoroptions.R.string.cogs_result,
                cogs
            )
    }

    private fun showExplanationDialog(
        beginningInventory : Double,
        purchases : Double,
        endingInventory : Double,
        cogs : Double,
    ) {
        val explanation = """
            The Cost of Goods Sold (COGS) calculator computes the cost of goods sold during a specific period.

            Given:
                Beginning Inventory = ₱$beginningInventory
                Purchases = ₱$purchases
                Ending Inventory = ₱$endingInventory

            Solution:
                COGS = Beginning Inventory + Purchases - Ending Inventory
                     = ₱$beginningInventory + ₱$purchases - ₱$endingInventory
                     = ₱${"%.2f".format(cogs)}

            Therefore, the Cost of Goods Sold (COGS) is ₱${"%.2f".format(cogs)}.
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Cost of Goods Sold (COGS) Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
