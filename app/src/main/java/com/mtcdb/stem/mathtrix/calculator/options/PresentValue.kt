package com.mtcdb.stem.mathtrix.calculator.options

import android.annotation.*
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.mtcdb.stem.mathtrix.MainActivity
import com.mtcdb.stem.mathtrix.R
import com.mtcdb.stem.mathtrix.calculator.*
import kotlin.math.pow

class PresentValueFragment : Fragment() {

    private lateinit var cashFlowsEditText : TextInputEditText
    private lateinit var discountRateEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var descriptionTextView : MaterialTextView
    private lateinit var compoundingPeriodSpinner: Spinner
    private lateinit var cashFlowTimingSpinner: Spinner

    private var compoundingPeriod: Int = 1
    private var cashFlowTiming: CashFlowTiming = CashFlowTiming.END_OF_PERIOD

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_present_value,
            container,
            false
        )

        cashFlowsEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextCashFlows)
        discountRateEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextDiscountRate)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculatePresentValue)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewPresentValueResult)
        descriptionTextView = view.findViewById(R.id.description)
        compoundingPeriodSpinner = view.findViewById(com.calculator.calculatoroptions.R.id.spinnerCompoundingPeriod)
        cashFlowTimingSpinner = view.findViewById(com.calculator.calculatoroptions.R.id.spinnerCashFlowTiming)

        calculateButton.setOnClickListener {
            calculatePresentValue()
        }

        cashFlowsEditText.addTextChangedListener(inputTextWatcher)
        discountRateEditText.addTextChangedListener(inputTextWatcher)

        setDescription()
        setupSpinners()

        return view
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.mtcdb.stem.mathtrix.R.string.calculator)
        super.onDestroy()
    }

    private val inputTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            validateInputs()
        }
    }

    private fun validateInputs() {
        val cashFlowsInput = cashFlowsEditText.text.toString()
        val discountRateString = discountRateEditText.text.toString().trim()
        val discountRate = if (discountRateString.isNotEmpty()) {
            try {
                discountRateString.toDoubleOrNull()
            } catch (e: NumberFormatException) {
                null
            }
        } else {
            null
        }

        calculateButton.isEnabled = cashFlowsInput.isNotBlank() && discountRate != null && discountRate >= 0
    }

    private fun calculatePresentValue() {
        val cashFlowsInput = cashFlowsEditText.text.toString()
        val discountRateString = discountRateEditText.text.toString().trim()
        val discountRate = if (discountRateString.isNotEmpty()) {
            try {
                discountRateString.toDoubleOrNull()
            } catch (e: NumberFormatException) {
                resultTextView.text = getString(R.string.invalid_input)
                return
            }
        } else {
            resultTextView.text = getString(R.string.invalid_input)
            return
        }

        val cashFlows = cashFlowsInput.split(",").mapNotNull { it.trim().toDoubleOrNull() }

        if (cashFlows.isEmpty()) {
            resultTextView.text = getString(R.string.invalid_input)
            return
        }

        val presentValue = cashFlows.mapIndexed { index, cashFlow ->
            val periodicRate = (discountRate?.div(100.0) ?: 0.0) / compoundingPeriod
            val periodNumber = if (cashFlowTiming == CashFlowTiming.BEGIN_OF_PERIOD) index else index + 1
            cashFlow / (1 + periodicRate).pow(periodNumber * compoundingPeriod)
        }.sum()

        resultTextView.text = getString(R.string.present_value_result, presentValue)
        if (discountRate != null) {
            showSolutionDialog(cashFlowsInput, discountRate, presentValue)
        }
    }

    private fun showSolutionDialog(
        cashFlows : String,
        discountRate : Double,
        presentValue : Double,
    ) {
        val explanation = """
            Given cash flows: $cashFlows
            Discount Rate: $discountRate%
            Compounding Period: $compoundingPeriod
            Cash Flow Timing: ${cashFlowTiming.displayName}

            Solution:
            """.trimIndent() +
                cashFlows.split(",").mapIndexed { index, cashFlow ->
                    val period =
                        if (cashFlowTiming == CashFlowTiming.BEGIN_OF_PERIOD) index else index + 1
                    "PV${index + 1} = $cashFlow / (1 + $discountRate/100/$compoundingPeriod)^$period\n"
                }.joinToString("") +
                "\nTotal Present Value = $presentValue"

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.present_value_calculation_title)
            .setMessage(explanation)
            .setPositiveButton(R.string.ok, null)
            .show()
    }


    private fun setupSpinners() {
        val compoundingPeriods = arrayOf(1, 2, 4, 12)
        val compoundingPeriodAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            compoundingPeriods
        )
        compoundingPeriodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        compoundingPeriodSpinner.adapter = compoundingPeriodAdapter
        compoundingPeriodSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent : AdapterView<*>?,
                    view : View?,
                    position : Int,
                    id : Long,
                ) {
                    compoundingPeriod = parent?.getItemAtPosition(position) as Int
                }

                override fun onNothingSelected(parent : AdapterView<*>?) {}
            }

        val cashFlowTimings = CashFlowTiming.entries.toTypedArray()
        val cashFlowTimingAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            cashFlowTimings.map { it.displayName }
        )
        cashFlowTimingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cashFlowTimingSpinner.adapter = cashFlowTimingAdapter
        cashFlowTimingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent : AdapterView<*>?,
                view : View?,
                position : Int,
                id : Long,
            ) {
                cashFlowTiming = cashFlowTimings[position]
            }

            override fun onNothingSelected(parent : AdapterView<*>?) {}
        }
    }


    private fun setDescription() {
        val description = """
            The Present Value of Cash Flows is a financial metric that calculates the current value of a series of future cash flows, discounted at a specified rate. The formula for calculating Present Value (PV) is:

            PV = CF1 / (1 + r)^1 + CF2 / (1 + r)^2 + … + CFn / (1 + r)^n
        
            Where:
            - CF1, CF2, …, CFn are the future cash flows.
            - r is the discount rate (annualized).
        
            Let&apos;s consider an example to illustrate the calculation:
        
            Suppose you expect to receive cash flows of ₱10,000, ₱15,000, and ₱20,000 over the next three years, with a discount rate of 5%.
        
            PV = ₱10,000 / (1 + 0.05)^1 + ₱15,000 / (1 + 0.05)^2 + ₱20,000 / (1 + 0.05)^3
               ≈ ₱8,548.54 + ₱12,589.42 + ₱16,410.27
               ≈ ₱37,548.23
        
            In this example, the Present Value of the cash flows is approximately ₱37,548.23.
        """.trimIndent()
        descriptionTextView.text = description
    }

    private enum class CashFlowTiming(val displayName : String) {
        BEGIN_OF_PERIOD("Beginning of Period"),
        END_OF_PERIOD("End of Period")
    }
}