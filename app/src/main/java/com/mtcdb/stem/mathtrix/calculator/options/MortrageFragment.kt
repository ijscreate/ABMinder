package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.text.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.button.*
import com.google.android.material.dialog.*
import com.google.android.material.textfield.*
import com.google.android.material.textview.*
import kotlin.math.*
import com.mtcdb.stem.mathtrix.R

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

        // Set input validation for edit texts
        principalEditText.addTextChangedListener(inputTextWatcher)
        interestRateEditText.addTextChangedListener(inputTextWatcher)
        loanTermEditText.addTextChangedListener(inputTextWatcher)

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

    private val inputTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            validateInputs()
        }
    }

    private fun validateInputs() {
        val principal = principalEditText.text.toString().toDoubleOrNull()
        val interestRate = interestRateEditText.text.toString().toDoubleOrNull()
        val loanTerm = loanTermEditText.text.toString().toDoubleOrNull()

        calculateButton.isEnabled =
            principal != null && principal > 0 && interestRate != null && interestRate >= 0 && loanTerm != null && loanTerm > 0
    }

    private fun calculateMortgage() {
        val principal = principalEditText.text.toString().toDouble()
        val annualInterestRate = interestRateEditText.text.toString().toDouble()
        val loanTermYears = loanTermEditText.text.toString().toDouble()

        try {
            val monthlyInterestRate = annualInterestRate / 12 / 100
            val totalPayments = loanTermYears * 12

            val monthlyPayment =
                principal * monthlyInterestRate * (1 + monthlyInterestRate).pow(totalPayments) /
                        ((1 + monthlyInterestRate).pow(totalPayments) - 1)

            resultTextView.text =
                getString(R.string.mortgage_result, monthlyPayment)
            showExplanationDialog(principal, annualInterestRate, loanTermYears, monthlyPayment)
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                getString(R.string.error_message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showExplanationDialog(
        principal : Double,
        monthlyInterestRate : Double,
        loanTermYears : Double,
        monthlyPayment : Double,
    ) {
        val explanation = """
    The Mortgage Calculator estimates your monthly mortgage payment based on the principal loan amount, annual interest rate, and loan term.

    Given:
        Principal Loan Amount: ₱$principal
        Annual Interest Rate: $monthlyInterestRate%
        Loan Term (Years): $loanTermYears

    Solution:
        Step 1: Calculate Monthly Interest Rate
            Monthly Interest Rate = Annual Interest Rate / 12 / 100
                                  = $monthlyInterestRate / 12 / 100

        Step 2: Calculate Total Number of Payments
            Total Number of Payments = Loan Term (Years) * 12
                                     = $loanTermYears * 12

        Step 3: Calculate Monthly Mortgage Payment
            To calculate the monthly mortgage payment, we use the following formula:
            
            Monthly Mortgage Payment = Principal Loan Amount * [Monthly Interest Rate * (1 + Monthly Interest Rate)^Total Number of Payments] /
                                       [(1 + Monthly Interest Rate)^Total Number of Payments - 1]
            
            Substituting the given values:
            Monthly Mortgage Payment = ₱$principal * [$monthlyInterestRate * (1 + $monthlyInterestRate)^$loanTermYears] /
                                       [(1 + $monthlyInterestRate)^$loanTermYears - 1]

            After performing the calculations, we find:
            Monthly Mortgage Payment ≈ ₱$monthlyPayment

    Therefore, your estimated monthly mortgage payment is ₱$monthlyPayment.
""".trimIndent()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Mortgage Payment Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
