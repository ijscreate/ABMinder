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

class CashConversionCycleFragment : Fragment() {

    private lateinit var stepTextView: TextView
    private lateinit var stepInputLayout: TextInputLayout
    private lateinit var stepInputEditText: TextInputEditText
    private lateinit var nextButton: Button
    private lateinit var resultTextView: TextView

    private var currentStep = 1
    private var daysInventory: Double? = null
    private var daysReceivables: Double? = null
    private var daysPayables: Double? = null
    private var inventoryPlusReceivables: Double? = null
    private var cashConversionCycle: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.calculator.calculatoroptions.R.layout.fragment_cash_conversion_cycle, container, false)

        stepTextView = view.findViewById(R.id.stepTextView)
        stepInputLayout = view.findViewById(R.id.stepInputLayout)
        stepInputEditText = view.findViewById(R.id.stepInputEditText)
        nextButton = view.findViewById(R.id.nextButton)
        resultTextView = view.findViewById(R.id.resultTextView)

        nextButton.setOnClickListener {
            handleNextStep()
        }

        setupStep(currentStep)

        val description = view.findViewById<TextView>(R.id.descriptionTextView)
        val desc = """
            The Cash Conversion Cycle (CCC) measures the time it takes for a company to convert its investments in inventory and other resources into cash flows from sales. It assesses the efficiency of a company's working capital management by evaluating how long it takes to convert resources into cash.

            Explanation:
            - Days Inventory Outstanding (DIO): Represents the average number of days it takes for a company to sell its inventory.
            - Days Sales Outstanding (DSO): Indicates the average number of days it takes for a company to collect payment from customers.
            - Days Payable Outstanding (DPO): Reflects the average number of days it takes for a company to pay its suppliers.

            Formula:
            Cash Conversion Cycle (CCC) = Days Inventory Outstanding (DIO) + Days Sales Outstanding (DSO) - Days Payable Outstanding (DPO)

            To use this calculator:
            1. Enter the number of days for Days Inventory Outstanding (DIO), Days Sales Outstanding (DSO), and Days Payable Outstanding (DPO).
            2. Calculate the Cash Conversion Cycle (CCC) using the given formula.
            3. Enter the calculated CCC value and click the "Confirm" button.
            4. The calculator will verify if your calculation is correct.
        """.trimIndent()
        description.text = desc

        return view
    }

    private fun setupStep(step: Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_days_inventory)
                stepInputLayout.hint = getString(R.string.days_inventory)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_days_receivables)
                stepInputLayout.hint = getString(R.string.days_receivables)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_input_days_payables)
                stepInputLayout.hint = getString(R.string.days_payables)
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_calculate_inventory_plus_receivables)
                stepInputLayout.visibility = View.VISIBLE
                stepInputLayout.hint = getString(R.string.step_4_inventory_plus_receivables_hint, daysInventory, daysReceivables)
                stepInputEditText.setText("")
                nextButton.text = getString(R.string.next)
                nextButton.setOnClickListener {
                    handleInventoryPlusReceivablesCalculation()
                }
            }

            5 -> {
                stepTextView.text = getString(R.string.step_5_input_days_payables)
                stepInputLayout.hint = getString(R.string.days_payables)
                stepInputEditText.setText("")
                nextButton.text = getString(R.string.confirm)
                nextButton.setOnClickListener {
                    handleNextStep()
                }
            }

            6 -> {
                stepTextView.text = getString(R.string.step_6_calculate_ccc)
                stepInputLayout.visibility = View.VISIBLE
                stepInputLayout.hint = getString(R.string.step_6_ccc_formula)
                stepInputEditText.setText("")
                nextButton.text = getString(R.string.confirm)
                nextButton.setOnClickListener {
                    handleCalculateCashConversionCycle()
                }
            }

            7 -> {
                stepTextView.text = getString(R.string.step_7_result)
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
                if (input != null && input > 0) {
                    daysInventory = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    daysReceivables = input
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    daysPayables = input
                    currentStep = 4
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            5 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    daysPayables = input
                    currentStep = 6
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }
        }
    }

    private fun handleInventoryPlusReceivablesCalculation() {
        inventoryPlusReceivables = daysInventory!! + daysReceivables!!
        currentStep = 5
        setupStep(currentStep)
    }

    private fun handleCalculateCashConversionCycle() {
        cashConversionCycle = inventoryPlusReceivables!! - daysPayables!!
        if (cashConversionCycle == stepInputEditText.text.toString().toDoubleOrNull()) {
            currentStep = 7
            setupStep(currentStep)
        } else {
            showIncorrectCalculationToast()
        }
    }

    private fun displayResult() {
        resultTextView.text = getString(R.string.cash_conversion_cycle_result, cashConversionCycle)
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showIncorrectCalculationToast() {
        Toast.makeText(requireContext(), R.string.incorrect_calculation, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }
}
