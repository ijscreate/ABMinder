package com.ijs.abminder.calculator.options

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ijs.abminder.R
import com.ijs.abminder.calculator.CalculatorOptionsActivity
import kotlin.math.pow

class TimeValueOfMoneyFragment : Fragment() {

    private lateinit var presentValueEditText : EditText
    private lateinit var compoundingPeriodEditText : EditText
    private lateinit var interestRateEditText : EditText
    private lateinit var timePeriodEditText : EditText
    private lateinit var calculateButton : Button
    private lateinit var resultTextView : TextView
    private lateinit var description : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_time_value_of_money,
            container,
            false
        )

        // Initialize UI components
        presentValueEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextPresentValue)
        compoundingPeriodEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextCompoundingPeriod)
        interestRateEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextInterestRate)
        timePeriodEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextTimePeriod)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateTVM)
        resultTextView = view.findViewById(com.calculator.calculatoroptions.R.id.textViewTVMResult)
        description = view.findViewById(R.id.description)
        val desc = """
            The Time Value of Money (TVM) concept evaluates the worth of money over time, considering the potential earning capacity of investments or interest accrual. It determines how the value of money changes due to factors like inflation and interest rates.
        
            Example:
        
                Suppose you have ₱1,000 to invest in a savings account with an annual interest rate of 5%, compounded quarterly. You want to calculate the future value of your investment after 3 years.
        
            Formula:
        
                The future value (FV) of an investment using TVM formula is calculated as:
        
            FV = PV * (1 + r/n)^(n*t)
        
            Calculation:
        
            - Present Value (PV): ₱1,000
            - Interest Rate (r): 5% per annum
            - Compounding Period (n): Quarterly (4 times a year)
            - Time Period (t): 3 years
        
            Plugging these values into the formula:
        
                FV = ₱1,000 * (1 + 0.05/4)^(4*3)
                FV ≈ ₱1,159.27
        
            Result:
        
            After 3 years, your investment of ₱1,000 will grow to approximately ₱1,159.27, considering quarterly compounding at a 5% annual interest rate.
        """.trimIndent()

        description.text = desc

        calculateButton.setOnClickListener {
            calculateTimeValueOfMoney()
        }

        return view
    }

    private fun calculateTimeValueOfMoney() {
        // Extract values from EditTexts
        val presentValue = presentValueEditText.text.toString().toDoubleOrNull() ?: 0.0
        val compoundingPeriod = compoundingPeriodEditText.text.toString().toDoubleOrNull() ?: 0.0
        val interestRate = interestRateEditText.text.toString().toDoubleOrNull() ?: 0.0
        val timePeriod = timePeriodEditText.text.toString().toDoubleOrNull() ?: 0.0
        val percentInterest = interestRate / 100

        // Calculate time value of money
        val result =
            presentValue * (1 + percentInterest / compoundingPeriod).pow(compoundingPeriod * timePeriod)

        // Display result
        resultTextView.text = String.format(getString(R.string.tvm_result_format), result)
        showExplanationDialog(presentValue, compoundingPeriod, interestRate, timePeriod, result)
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }

    private fun showExplanationDialog(
        presentValue : Double,
        compoundingPeriod : Double,
        interestRate : Double,
        timePeriod : Double,
        result : Double,
    ) {
        // Convert interest rate to decimal form
        val adjustedInterestRate = interestRate / 100

        // Explanation of the Time Value of Money (TVM) calculation
        val explanation = """
        Time Value of Money (TVM) calculates the present or future value of money based on an interest rate and time period.

        Given:
            Present Value: ₱$presentValue
            Number of Compounding Periods Per Year: $compoundingPeriod
            Interest Rate: $interestRate%
            Number of Years: $timePeriod years

        Formula:
            Future Value = Present Value * (1 + Interest Rate / Compounding Period)^Compounding Period * Time Period

        Calculation:
            Future Value = ₱$presentValue * (1 + $adjustedInterestRate / $compoundingPeriod)^(${compoundingPeriod * timePeriod})
            Future Value = ₱$result

        Therefore, the Time Value of Money is ₱$result.
    """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Time Value of Money (TVM) Analysis")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }

}
