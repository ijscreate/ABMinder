package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.button.*
import com.google.android.material.dialog.*
import com.google.android.material.textfield.*
import com.google.android.material.textview.*
import kotlin.math.*

class MortgageCalculatorFragment : Fragment() {

    private lateinit var principalEditText : TextInputEditText
    private lateinit var interestRateEditText : TextInputEditText
    private lateinit var loanTermEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var description : TextView
    private lateinit var buttonSolution : Button

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_mortrage,
            container,
            false
        )

        // Initialize UI components
        principalEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextPrincipalMortgage)
        interestRateEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextInterestRateMortgage)
        loanTermEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextLoanTermMortgage)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateMortgage)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewMortgageResult)
        description = view.findViewById(com.calculator.calculatoroptions.R.id.description)
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution)

        calculateButton.setOnClickListener {
            calculateMortgage()
        }

        val mortgageDescription = """
            The Mortgage Calculator helps you estimate your monthly mortgage payment.

            The formula for calculating the monthly mortgage payment is based on the following formula:

            M = P * [r * (1 + r)^n] / [(1 + r)^n - 1]

            Where:
            - M is the monthly mortgage payment
            - P is the principal loan amount
            - r is the monthly interest rate (annual interest rate divided by 12)
            - n is the total number of payments (loan term in years multiplied by 12)

            Adjust the principal loan amount, annual interest rate, and loan term to see how they affect your monthly mortgage payment.
        """.trimIndent()

        description.text = mortgageDescription

        buttonSolution.setOnClickListener {
            calculateMortgage()
        }

        return view
    }

    private fun calculateMortgage() {
        val principal = principalEditText.text.toString().toDoubleOrNull() ?: 0.0
        val annualInterestRate = interestRateEditText.text.toString().toDoubleOrNull() ?: 0.0
        val loanTermYears = loanTermEditText.text.toString().toDoubleOrNull() ?: 0.0

        val monthlyInterestRate = annualInterestRate / 12 / 100
        val totalPayments = loanTermYears * 12

        val monthlyPayment =
            principal * monthlyInterestRate * (1 + monthlyInterestRate).pow(totalPayments) /
                    ((1 + monthlyInterestRate).pow(totalPayments) - 1)

        resultTextView.text =
            getString(
                com.calculator.calculatoroptions.R.string.mortgage_result,
                monthlyPayment
            )
        showExplanationDialog(principal, annualInterestRate, loanTermYears, monthlyPayment)
    }

    private fun showExplanationDialog(
        principal : Double,
        annualInterestRate : Double,
        loanTermYears : Double,
        monthlyPayment : Double,
    ) {
        val explanation = """
            The Mortgage Calculator helps you estimate your monthly mortgage payment.

            Given:
                Principal Loan Amount = $principal
                Annual Interest Rate = $annualInterestRate%
                Loan Term (Years) = $loanTermYears

            Solution:
                Monthly Interest Rate = Annual Interest Rate / 12 / 100
                                       = $annualInterestRate / 12 / 100

                Total Number of Payments = Loan Term (Years) * 12
                                         = $loanTermYears * 12

                Monthly Mortgage Payment = Principal Loan Amount * [Monthly Interest Rate * (1 + Monthly Interest Rate)^Total Number of Payments] /
                                           [(1 + Monthly Interest Rate)^Total Number of Payments - 1]
                                         = $monthlyPayment

            Therefore, your estimated monthly mortgage payment is $monthlyPayment.
        """.trimIndent()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Mortgage Payment Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
