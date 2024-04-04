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

class InventoryTurnoverRatioFragment : Fragment() {

    private lateinit var costOfGoodsSoldEditText : TextInputEditText
    private lateinit var averageInventoryEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var description : MaterialTextView
    private lateinit var buttonSolution : Button

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
        costOfGoodsSoldEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextCostOfGoodsSold)
        averageInventoryEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextAverageInventory)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateInventoryTurnoverRatio)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewInventoryTurnoverRatioResult)
        description = view.findViewById(com.calculator.calculatoroptions.R.id.tVDescription)
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution)

        calculateButton.setOnClickListener {
            calculateInventoryTurnoverRatio()
        }

        val inventoryTurnoverRatioDescription = """
            Inventory Turnover Ratio measures how many times a company's inventory is sold and replaced over a specific period. It is a crucial financial metric for assessing how efficiently a company manages its inventory.

            The formula for calculating Inventory Turnover Ratio is:

            Inventory Turnover Ratio = Cost of Goods Sold / Average Inventory

            Where:
            - Cost of Goods Sold (COGS) is the total direct costs of producing goods sold by a company.
            - Average Inventory is the average value of the beginning and ending inventories over a certain period.

            A higher Inventory Turnover Ratio generally indicates efficient inventory management, as it implies that inventory is sold quickly and replaced frequently.

            Let's consider an example:
            Suppose a company has a Cost of Goods Sold of $500,000 and an Average Inventory value of $100,000.

            Inventory Turnover Ratio = $500,000 / $100,000
                                    = 5

            In this example, the Inventory Turnover Ratio is 5, suggesting that the company's inventory is sold and replaced 5 times during the specified period.

            Investors often use this ratio to assess how well a company is managing its inventory levels. However, the ideal ratio varies by industry, so it's essential to compare it with industry benchmarks.
        """.trimIndent()

        description.text = inventoryTurnoverRatioDescription

        buttonSolution.setOnClickListener {
            val costOfGoodsSold = costOfGoodsSoldEditText.text.toString().toDoubleOrNull() ?: 0.0
            val averageInventory = averageInventoryEditText.text.toString().toDoubleOrNull() ?: 0.0

            val inventoryTurnoverRatio = costOfGoodsSold / averageInventory

            resultTextView.text =
                getString(
                    com.calculator.calculatoroptions.R.string.inventory_turnover_ratio_result,
                    inventoryTurnoverRatio
                )
            showExplanationDialog(costOfGoodsSold, averageInventory, inventoryTurnoverRatio)
        }

        return view
    }

    override fun onDestroy() {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }

    private fun calculateInventoryTurnoverRatio() {
        val costOfGoodsSold = costOfGoodsSoldEditText.text.toString().toDoubleOrNull() ?: 0.0
        val averageInventory = averageInventoryEditText.text.toString().toDoubleOrNull() ?: 0.0

        val inventoryTurnoverRatio = costOfGoodsSold / averageInventory

        resultTextView.text =
            getString(
                com.calculator.calculatoroptions.R.string.inventory_turnover_ratio_result,
                inventoryTurnoverRatio
            )
        showExplanationDialog(costOfGoodsSold, averageInventory, inventoryTurnoverRatio)
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
            .setTitle("Inventory Turnover Ratio Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
