package com.mtcdb.stem.mathtrix.calculator.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.mtcdb.stem.mathtrix.MainActivity
import com.mtcdb.stem.mathtrix.R
import com.mtcdb.stem.mathtrix.calculator.*

class InventoryTurnoverRatioFragment : Fragment() {

    private lateinit var costOfGoodsSoldEditText : TextInputEditText
    private lateinit var averageInventoryEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var description : MaterialTextView
    private lateinit var solution: Button

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_inventory_turnover,
            container,
            false
        )

        // Initialize UI components
        costOfGoodsSoldEditText = view.findViewById(com.calculator.calculatoroptions.R.id.editTextCostOfGoodsSold)
        averageInventoryEditText = view.findViewById(com.calculator.calculatoroptions.R.id.editTextAverageInventory)
        calculateButton = view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateInventoryTurnoverRatio)
        resultTextView = view.findViewById(com.calculator.calculatoroptions.R.id.textViewInventoryTurnoverRatioResult)
        description = view.findViewById(com.calculator.calculatoroptions.R.id.tVDescription)
        solution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution)

        // Set input validation for edit texts
        costOfGoodsSoldEditText.addTextChangedListener { validateInputs() }
        averageInventoryEditText.addTextChangedListener { validateInputs() }

        calculateButton.setOnClickListener { calculateInventoryTurnoverRatio() }
        solution.setOnClickListener {
            val costOfGoodsSold = costOfGoodsSoldEditText.text.toString().toDouble()
            val averageInventory = averageInventoryEditText.text.toString().toDouble()

            try {
                val inventoryTurnoverRatio = costOfGoodsSold / averageInventory
                resultTextView.text = getString(
                    com.calculator.calculatoroptions.R.string.inventory_turnover_ratio_result,
                    inventoryTurnoverRatio
                )
                showExplanationDialog(costOfGoodsSold, averageInventory, inventoryTurnoverRatio)
            } catch (e : Exception) {
                showErrorDialog(e.message ?: getString(R.string.error_message))
            }
        }

        val inventoryTurnoverRatioDescription = """
            The Inventory Turnover Ratio is a financial ratio that measures how efficiently a company manages and sells its inventory. It indicates the number of times a company's inventory is sold and replaced over a specific period, typically a year.

            The formula for calculating the Inventory Turnover Ratio is:

            Inventory Turnover Ratio = Cost of Goods Sold / Average Inventory

            Where:
            - Cost of Goods Sold (COGS) is the total direct costs of producing goods sold by a company during a specific period.
            - Average Inventory is the average value of the beginning and ending inventories over the same period.

            To calculate Average Inventory, you can use the following formula:

            Average Inventory = (Beginning Inventory + Ending Inventory) / 2

            A higher Inventory Turnover Ratio generally indicates efficient inventory management, as it implies that inventory is sold quickly and replaced frequently. However, the ideal ratio varies by industry, so it's essential to compare it with industry benchmarks.
        """.trimIndent()

        description.text = inventoryTurnoverRatioDescription

        return view
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.mtcdb.stem.mathtrix.R.string.calculator)
        super.onDestroy()
    }

    private fun validateInputs() {
        val costOfGoodsSold = costOfGoodsSoldEditText.text.toString().toDoubleOrNull()
        val averageInventory = averageInventoryEditText.text.toString().toDoubleOrNull()

        calculateButton.isEnabled = costOfGoodsSold != null && costOfGoodsSold > 0 &&
                averageInventory != null && averageInventory > 0
    }

    private fun calculateInventoryTurnoverRatio() {
        val costOfGoodsSold = costOfGoodsSoldEditText.text.toString().toDouble()
        val averageInventory = averageInventoryEditText.text.toString().toDouble()

        try {
            val inventoryTurnoverRatio = costOfGoodsSold / averageInventory
            resultTextView.text = getString(
                com.calculator.calculatoroptions.R.string.inventory_turnover_ratio_result,
                inventoryTurnoverRatio
            )
        } catch (e : Exception) {
            showErrorDialog(e.message ?: getString(R.string.error_message))
        }
    }

    private fun showExplanationDialog(
        costOfGoodsSold : Double,
        averageInventory : Double,
        inventoryTurnoverRatio : Double,
    ) {
        val explanation = """
            Inventory Turnover Ratio measures how efficiently a company manages its inventory by indicating how many times inventory is sold and replaced over a specific period.

            The formula for calculating Inventory Turnover Ratio is:

            Inventory Turnover Ratio = Cost of Goods Sold / Average Inventory

            Given:
            Cost of Goods Sold = $costOfGoodsSold
            Average Inventory = $averageInventory

            Solution:
            Inventory Turnover Ratio = $costOfGoodsSold / $averageInventory
            Inventory Turnover Ratio = $inventoryTurnoverRatio

            Therefore, the Inventory Turnover Ratio is $inventoryTurnoverRatio.
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.inventory_turnover_ratio_calculation)
            .setMessage(explanation)
            .setPositiveButton(R.string.ok, null)
            .show()
    }

    private fun showErrorDialog(errorMessage : String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error_title)
            .setMessage(errorMessage)
            .setPositiveButton(R.string.ok, null)
            .show()
    }
}