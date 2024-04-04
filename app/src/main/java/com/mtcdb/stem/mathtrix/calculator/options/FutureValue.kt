package com.mtcdb.stem.mathtrix.calculator.options

import android.annotation.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.calculator.calculatoroptions.*
import com.google.android.material.button.*
import com.google.android.material.dialog.*
import com.google.android.material.textfield.*
import com.google.android.material.textview.*
import kotlin.math.*

class FutureValueFragment : Fragment() {

    private lateinit var principalAmountEditText : TextInputEditText
    private lateinit var interestRateEditText : TextInputEditText
    private lateinit var numberOfPeriodsEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var description : TextView
    private lateinit var buttonSolution : Button

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val view = inflater.inflate(
            R.layout.fragment_future_value,
            container,
            false
        )

        // Initialize UI components
        principalAmountEditText = view.findViewById(R.id.editTextPrincipalAmount)
        interestRateEditText = view.findViewById(R.id.editTextInterestRate)
        numberOfPeriodsEditText = view.findViewById(R.id.editTextNumberOfPeriods)
        calculateButton = view.findViewById(R.id.buttonCalculateFutureValue)
        resultTextView = view.findViewById(R.id.textViewFutureValueResult)
        description = view.findViewById(R.id.description)
        buttonSolution = view.findViewById(R.id.buttonSolution)

        calculateButton.setOnClickListener {
            calculateFutureValue()
        }

        val futureValueDescription = """
            The Future Value of an Investment is a financial metric that calculates the value of an investment at a specified future date, based on a specified rate of return. The formula for calculating Future Value (FV) is:
            
            FV = PV * (1 + r)^n
            
            Where:
            - PV (Principal Amount) represents the initial amount of the investment.
            - r (Interest Rate) represents the annual interest rate (in decimal form).
            - n (Number of Periods) represents the number of compounding periods.
            
            Let's consider an example to illustrate the calculation:
            
            Suppose you invest ₱10,000 at an annual interest rate of 5% for 3 years.
            
            FV = ₱10,000 * (1 + 0.05)^3
               = ₱10,000 * (1.05)^3
               ≈ ₱11,576.25
            
            In this example, the Future Value of the investment after 3 years is approximately ₱11,576.25.
        """.trimIndent()

        description.text = futureValueDescription

        buttonSolution.setOnClickListener {
            calculateFutureValue()
        }

        return view
    }

    @SuppressLint("StringFormatInvalid")
    private fun calculateFutureValue() {
        val principalAmount = principalAmountEditText.text.toString().toDoubleOrNull() ?: 0.0
        val interestRate = interestRateEditText.text.toString().toDoubleOrNull() ?: 0.0
        val numberOfPeriods = numberOfPeriodsEditText.text.toString().toDoubleOrNull() ?: 0.0

        val futureValue = principalAmount * (1 + interestRate).pow(numberOfPeriods)

        resultTextView.text =
            getString(
                R.string.future_value_result,
                futureValue
            )
        showExplanationDialog(principalAmount, interestRate, numberOfPeriods, futureValue)
    }

    private fun showExplanationDialog(
        principalAmount : Double,
        interestRate : Double,
        numberOfPeriods : Double,
        futureValue : Double,
    ) {
        val explanation = """
            The Future Value of an Investment is a financial metric that calculates the value of an investment at a specified future date, based on a specified rate of return.
            
            Given:
                Principal Amount = ₱$principalAmount
                Interest Rate = ${"%.2f".format(interestRate * 100)}%
                Number of Periods = $numberOfPeriods
                
            Solution:
                FV = PV * (1 + r)^n
                   = ₱$principalAmount * (1 + $interestRate)^$numberOfPeriods
                   ≈ ₱${"%.2f".format(futureValue)}
                                  
            Therefore, the Future Value of the investment is approximately ₱${
            "%.2f".format(
                futureValue
            )
        }.
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Future Value of an Investment Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroy() {
        val mainActivity = requireActivity() as com.mtcdb.stem.mathtrix.MainActivity
        mainActivity.toolbar.title = getString(com.mtcdb.stem.mathtrix.R.string.calculator)
        super.onDestroy()
    }
}
