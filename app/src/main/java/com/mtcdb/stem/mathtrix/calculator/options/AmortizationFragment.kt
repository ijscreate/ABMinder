package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.calculator.calculatoroptions.*
import com.google.android.material.dialog.*
import kotlin.math.*

class AmortizationFragment : Fragment() {

    private lateinit var loanAmountEditText : EditText
    private lateinit var interestRateEditText : EditText
    private lateinit var loanTermEditText : EditText
    private lateinit var calculateButton : Button
    private lateinit var resultTextView : TextView
    private lateinit var descriptionTextView : TextView
    private lateinit var explainButton : Button

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val rootView =
            inflater.inflate(
                R.layout.fragment_amortization_schedule,
                container,
                false
            )

        loanAmountEditText =
            rootView.findViewById(R.id.editTextLoanAmount)
        interestRateEditText =
            rootView.findViewById(R.id.editTextInterestRate)
        loanTermEditText =
            rootView.findViewById(R.id.editTextLoanTerm)
        calculateButton =
            rootView.findViewById(R.id.buttonCalculateAmortization)
        resultTextView =
            rootView.findViewById(R.id.textViewAmortizationResult)
        descriptionTextView =
            rootView.findViewById(R.id.tVAmortizationDescription)
        explainButton =
            rootView.findViewById(R.id.explainAmortizationButton)

        calculateButton.setOnClickListener {
            calculateAmortization()
        }

        explainButton.setOnClickListener {
            showAmortizationExplanation()
        }

        // Set up description text
        val description = """
            The Amortization Schedule calculates the monthly installment payments for a loan.
        
            The formula for calculating monthly payments is:
        
            M = P * [r(1+r)^n] / [(1+r)^n-1]
        
            Where:
            - M = Monthly payment
            - P = Principal loan amount
            - r = Monthly interest rate (annual rate / 12 / 100)
            - n = Total number of payments (loan term * 12)
        
            Example:
            Suppose you take out a loan of ₱10,000 with an annual interest rate of 5% for 3 years.
        
            Monthly interest rate (r) = 0.05 / 12
            Total number of payments (n) = 3 * 12
            Monthly payment (M) ≈ ₱299.71
        """.trimIndent()


        descriptionTextView.text = description

        return rootView
    }

    private fun calculateAmortization() {
        val loanAmount = loanAmountEditText.text.toString().toDoubleOrNull() ?: 0.0
        val interestRate = interestRateEditText.text.toString().toDoubleOrNull() ?: 0.0
        val loanTerm = loanTermEditText.text.toString().toDoubleOrNull() ?: 0.0

        val monthlyInterestRate = interestRate / 12 / 100
        val totalPayments = loanTerm * 12
        val denominator = (1 + monthlyInterestRate).pow(totalPayments) - 1

        val monthlyPayment = loanAmount * monthlyInterestRate * (1 + monthlyInterestRate).pow(
            totalPayments
        ) / denominator

        resultTextView.text = String.format("Monthly Payment: $%.2f", monthlyPayment)
    }

    private fun showAmortizationExplanation() {
        val loanAmount = loanAmountEditText.text.toString().toDoubleOrNull() ?: 0.0
        val interestRate = interestRateEditText.text.toString().toDoubleOrNull() ?: 0.0
        val loanTerm = loanTermEditText.text.toString().toDoubleOrNull() ?: 0.0

        val monthlyInterestRate = interestRate / 12 / 100
        val totalPayments = loanTerm * 12
        val denominator = totalPayments.pow(2) - 1

        val monthlyPayment = loanAmount * monthlyInterestRate * (1 + monthlyInterestRate).pow(
            totalPayments
        ) / denominator

        val explanation = """
            Given:
                Loan Amount = $loanAmount
                Annual Interest Rate = $interestRate%
                Loan Term = $loanTerm years
                
            Formula:
                M = P * [r(1+r)^n] / [(1+r)^n-1]
                
            Solution:
                Monthly Interest Rate (r) = $monthlyInterestRate
                Total Number of Payments (n) = $totalPayments
                Monthly Payment (M) = $monthlyPayment
                
            Therefore, the Monthly Payment is $${String.format("%.2f", monthlyPayment)}
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Amortization Schedule")
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
