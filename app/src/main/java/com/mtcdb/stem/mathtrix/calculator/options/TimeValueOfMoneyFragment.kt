package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.dialog.*
import com.mtcdb.stem.mathtrix.*
import kotlin.math.*

class TimeValueOfMoneyFragment : Fragment() {

    private lateinit var presentValueEditText : EditText
    private lateinit var compoundingPeriodEditText : EditText
    private lateinit var interestRateEditText : EditText
    private lateinit var timePeriodEditText : EditText
    private lateinit var calculateButton : Button
    private lateinit var resultTextView : TextView

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
        val mainActivity = requireActivity() as MainActivity
        mainActivity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }

    private fun showExplanationDialog(
        presentValue : Double,
        compoundingPeriod : Double,
        interestRate : Double,
        timePeriod : Double,
        result : Double,
    ) {
        interestRate / 100

        val explanation = """
            Time Value of Money (TVM) calculates the present or future value of money based on an interest rate and time period.

            Given:
                Present Value = $presentValue
                Number of Compounding Period Per Year = $compoundingPeriod
                Interest Rate = $interestRate%
                Number of Years = $timePeriod years

            Formula:
                Future Value = Present Value * (1 + Interest Rate / Compounding Period)^Compounding Period * Time Period

            Calculation:
                Future Value = $presentValue * (1 + $interestRate / $compoundingPeriod)^$compoundingPeriod * $timePeriod
                Future Value = $result

            Therefore, the Time Value of Money is $result.
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Time Value of Money (TVM) Analysis")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
