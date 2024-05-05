package com.ijs.abminder.calculator.options

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ijs.abminder.R
import com.ijs.abminder.calculator.CalculatorOptionsActivity

class InventoryTurnoverRatioFragment : Fragment() {

    private lateinit var stepTextView : TextView
    private lateinit var stepInputLayout : TextInputLayout
    private lateinit var stepInputEditText : TextInputEditText
    private lateinit var nextButton : Button
    private lateinit var resultTextView : TextView

    private var currentStep = 1
    private var costOfGoodsSold : Double? = null
    private var averageInventory : Double? = null
    private var inventoryTurnoverRatio : Double? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val rootView =
            inflater.inflate(com.calculator.calculatoroptions.R.layout.fragment_inventory_turnover, container, false)

        stepTextView = rootView.findViewById(R.id.stepTextView)
        stepInputLayout = rootView.findViewById(R.id.stepInputLayout)
        stepInputEditText = rootView.findViewById(R.id.stepInputEditText)
        nextButton = rootView.findViewById(R.id.nextButton)
        resultTextView = rootView.findViewById(R.id.resultTextView)

        setupStep(currentStep)

        nextButton.setOnClickListener {
            handleNextStep()
        }

        val description = rootView.findViewById<TextView>(R.id.descriptionTextView)
        val desc = """
            Calculate the Inventory Turnover Ratio using the formula:
            
            Inventory Turnover Ratio = Cost of Goods Sold / Average Inventory
            
            This calculator helps you determine the efficiency of a company's inventory management.
            
            Example:
            
                If the Cost of Goods Sold is ₱1,000,000 and the Average Inventory is ₱200,000, the Inventory Turnover Ratio would be:
                
                Inventory Turnover Ratio = 1,000,000 / 200,000 = 5
                
            Therefore, the Inventory Turnover Ratio is 5, which means the company sells its entire inventory 5 times per year.
    """.trimIndent()
        description.text = desc

        return rootView
    }

    private fun setupStep(step : Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_cost_of_goods_sold)
                stepInputLayout.hint = getString(R.string.cost_of_goods_sold)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_average_inventory)
                stepInputLayout.hint = getString(R.string.average_inventory)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_calculate_inventory_turnover_ratio_hint)
                stepInputLayout.hint = getString(
                    R.string.step_3_calculate_inventory_turnover_ratio,
                    costOfGoodsSold,
                    averageInventory
                )
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_result)
                stepInputLayout.visibility = View.GONE
                nextButton.text = getString(R.string.exit)
                nextButton.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction().remove(this)
                        .commit()
                }
                displayResult()
            }
        }
    }

    private fun handleNextStep() {
        when (currentStep) {
            1 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    costOfGoodsSold = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    averageInventory = input
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    val expectedInventoryTurnoverRatio = costOfGoodsSold!! / averageInventory!!
                    if (input == expectedInventoryTurnoverRatio) {
                        inventoryTurnoverRatio = input
                        currentStep = 4
                        setupStep(currentStep)
                    } else {
                        showInventoryTurnoverRatioCalculationErrorToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }
        }
    }

    private fun displayResult() {
        resultTextView.text =
            getString(R.string.inventory_turnover_ratio_result, inventoryTurnoverRatio!!)
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showInventoryTurnoverRatioCalculationErrorToast() {
        Toast.makeText(
            requireContext(),
            R.string.incorrect_inventory_turnover_ratio_calculation,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        (activity).enableRecyclerView()
        super.onDestroy()
    }
}
