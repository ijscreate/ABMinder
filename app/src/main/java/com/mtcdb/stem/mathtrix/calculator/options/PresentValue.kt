package com.mtcdb.stem.mathtrix.calculator.options

import android.annotation.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.calculator.calculatoroptions.*
import com.google.android.material.textfield.*
import com.google.android.material.textview.*
import kotlin.math.*

class PresentValueFragment : Fragment() {

    private lateinit var cashFlowsEditText : TextInputEditText
    private lateinit var discountRateEditText : TextInputEditText
    private lateinit var calculateButton : Button
    private lateinit var resultTextView : MaterialTextView
    private lateinit var descriptionTextView : MaterialTextView

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(R.layout.fragment_present_value, container, false)

        cashFlowsEditText = view.findViewById(R.id.editTextCashFlows)
        discountRateEditText = view.findViewById(R.id.editTextDiscountRate)
        calculateButton = view.findViewById(R.id.buttonCalculatePresentValue)
        resultTextView = view.findViewById(R.id.textViewPresentValueResult)
        descriptionTextView = view.findViewById(R.id.description)

        calculateButton.setOnClickListener {
            calculatePresentValue()
        }

        setDescription()

        return view
    }

    override fun onDestroy() {
        val mainActivity = requireActivity() as com.mtcdb.stem.mathtrix.MainActivity
        mainActivity.toolbar.title = getString(com.mtcdb.stem.mathtrix.R.string.calculator)
        super.onDestroy()
    }

    @SuppressLint("StringFormatInvalid")
    private fun calculatePresentValue() {
        val cashFlowsInput = cashFlowsEditText.text.toString()
        val discountRate = discountRateEditText.text.toString().toDoubleOrNull()

        if (cashFlowsInput.isBlank() || discountRate == null) {
            resultTextView.text = getString(R.string.invalid_input)
            return
        }

        val cashFlows = cashFlowsInput.split(",").map { it.trim().toDoubleOrNull() }

        if (cashFlows.any { it == null }) {
            resultTextView.text = getString(R.string.invalid_input)
            return
        }

        val presentValue = cashFlows.mapIndexed { index, cashFlow ->
            cashFlow!! / (1 + discountRate).pow(index + 1)
        }.sum()

        resultTextView.text = getString(R.string.present_value_result, presentValue)
    }

    private fun setDescription() {
        val des = """
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
        descriptionTextView.text = des
    }
}
