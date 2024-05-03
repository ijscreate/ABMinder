package com.ijs.abminder.calculator.options

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

class COGSFragment : Fragment() {

    private lateinit var stepTextView: TextView
    private lateinit var stepInputLayout: TextInputLayout
    private lateinit var stepInputEditText: TextInputEditText
    private lateinit var nextButton: Button
    private lateinit var resultTextView: TextView

    private var currentStep = 1
    private var beginningInventory: Double? = null
    private var purchases: Double? = null
    private var endingInventory: Double? = null
    private var cogs: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.calculator.calculatoroptions.R.layout.fragment_cogs, container, false)

        stepTextView = view.findViewById(R.id.stepTextView)
        stepInputLayout = view.findViewById(R.id.stepInputLayout)
        stepInputEditText = view.findViewById(R.id.stepInputEditText)
        nextButton = view.findViewById(R.id.nextButton)
        resultTextView = view.findViewById(R.id.resultTextView)

        setupStep(currentStep)

        nextButton.setOnClickListener {
            handleNextStep()
        }

        val description = view.findViewById<TextView>(R.id.descriptionTextView)
        val cogsDescription = """
            The Cost of Goods Sold (COGS) calculator is a guide to help you compute the cost of goods sold during a specific period.

            COGS is an essential financial metric used to determine the direct costs associated with the production of goods sold by a company. The formula for calculating COGS is:

            COGS = Beginning Inventory + Purchases - Ending Inventory

            Where:
            - Beginning Inventory represents the value of goods held in inventory at the beginning of the accounting period.
            - Purchases represent the cost of acquiring additional inventory during the accounting period.
            - Ending Inventory represents the value of goods held in inventory at the end of the accounting period.

            Please input the values for the beginning inventory, purchases, and ending inventory, then calculate the COGS yourself. If your calculation is incorrect, you won't be able to proceed to the next step.

            Let's consider an example to illustrate the calculation:

            Suppose a company had a beginning inventory of ₱50,000, made purchases totaling ₱100,000 during the period, and had an ending inventory of ₱30,000.

            COGS = ₱50,000 + ₱100,000 - ₱30,000
                 = ₱120,000

            In this example, the Cost of Goods Sold is ₱120,000, indicating the direct costs associated with the production of goods sold during the period.
        """.trimIndent()
        description.text = cogsDescription

        return view
    }

    private fun setupStep(step: Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_beginning_inventory)
                stepInputLayout.hint = getString(R.string.beginning_inventory)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_purchases)
                stepInputLayout.hint = getString(R.string.purchases)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_input_ending_inventory)
                stepInputLayout.hint = getString(R.string.ending_inventory)
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_calculate)
                stepInputLayout.visibility = View.GONE
                nextButton.text = getString(R.string.calculate)
                nextButton.setOnClickListener {
                    handleCalculation()
                }
            }

            5 -> {
                stepTextView.text = getString(R.string.step_5_result)
                stepInputLayout.visibility = View.GONE
                nextButton.text = getString(R.string.exit)
                nextButton.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction().remove(this)
                        .commit()
                }
                val activity = requireActivity() as CalculatorOptionsActivity
                activity.toolbar.title = getString(R.string.calculator)
                displayResult()
            }
        }
    }

    private fun handleNextStep() {
        when (currentStep) {
            1 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    beginningInventory = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    purchases = input
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    endingInventory = input
                    currentStep = 4
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }
        }
    }

    private fun handleCalculation() {
        val beginningInventory = beginningInventory ?: 0.0
        val purchases = purchases ?: 0.0
        val endingInventory = endingInventory ?: 0.0

        val userCOGS = beginningInventory + purchases - endingInventory
        cogs = userCOGS

        val correctCOGS = calculateCorrectCOGS(beginningInventory, purchases, endingInventory)
        if (userCOGS == correctCOGS) {
            currentStep = 5
            setupStep(currentStep)
        } else {
            showIncorrectCalculationToast()
        }
    }

    private fun displayResult() {
        resultTextView.text = getString(
            R.string.cogs_result,
            cogs ?: 0.0
        )
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showIncorrectCalculationToast() {
        Toast.makeText(requireContext(), R.string.incorrect_calculation, Toast.LENGTH_SHORT).show()
    }

    private fun calculateCorrectCOGS(
        beginningInventory: Double,
        purchases: Double,
        endingInventory: Double
    ): Double {
        return beginningInventory + purchases - endingInventory
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }
}
