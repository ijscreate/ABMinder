package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.text.*
import android.text.method.*
import android.view.*
import android.widget.*
import androidx.core.widget.*
import androidx.fragment.app.*
import com.calculator.calculatoroptions.*
import com.google.android.material.dialog.*
import com.mtcdb.stem.mathtrix.calculator.*
import kotlin.math.*

class AmortizationFragment : Fragment() {

    private lateinit var loanAmountEditText : EditText
    private lateinit var interestRateEditText : EditText
    private lateinit var loanTermEditText : EditText
    private lateinit var calculateButton : Button
    private lateinit var resultTextView : TextView
    private lateinit var descriptionTextView : TextView
    private lateinit var explainButton : Button
    private lateinit var tableLayout : TableLayout

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
        tableLayout = rootView.findViewById(R.id.tableLayoutAmortization)

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

        val monthlyPayment =
            loanAmount * monthlyInterestRate * (1 + monthlyInterestRate).pow(totalPayments) / denominator

        resultTextView.text = String.format("Monthly Payment: ₱%.2f", monthlyPayment)

        generateAmortizationTable(loanAmount, monthlyInterestRate, totalPayments, monthlyPayment)
    }

    private fun generateAmortizationTable(
        loanAmount : Double,
        monthlyInterestRate : Double,
        totalPayments : Double,
        monthlyPayment : Double,
    ) {
        tableLayout.removeAllViews() // Clear the table

        // Add table headers
        val headerRow = TableRow(context)
        headerRow.addView(createTableCell("Month"))
        headerRow.addView(createTableCell("Payment"))
        headerRow.addView(createTableCell("Principal"))
        headerRow.addView(createTableCell("Interest"))
        headerRow.addView(createTableCell("Balance"))
        tableLayout.addView(headerRow)

        var balance = loanAmount
        for (month in 1..totalPayments.toInt()) {
            val interest = balance * monthlyInterestRate
            val principal = monthlyPayment - interest
            balance -= principal

            val row = TableRow(context)
            row.addView(createTableCell(month.toString()))
            row.addView(createTableCell(String.format("₱%.2f", monthlyPayment)))
            row.addView(createTableCell(String.format("₱%.2f", principal)))
            row.addView(createTableCell(String.format("₱%.2f", interest)))
            row.addView(createTableCell(String.format("₱%.2f", balance)))
            tableLayout.addView(row)
        }
    }

    private fun createTableCell(text : String) : TextView {
        val cell = TextView(context)
        cell.text = text
        cell.setPadding(16, 16, 16, 16)
        return cell
    }

    private fun showAmortizationExplanation() {
        val loanAmount = loanAmountEditText.text.toString().toDoubleOrNull() ?: 0.0
        val interestRate = interestRateEditText.text.toString().toDoubleOrNull() ?: 0.0
        val loanTerm = loanTermEditText.text.toString().toDoubleOrNull() ?: 0.0

        val monthlyInterestRate = interestRate / 12 / 100
        val totalPayments = loanTerm * 12
        val denominator = (1 + monthlyInterestRate).pow(totalPayments) - 1
        val monthlyPayment = loanAmount * monthlyInterestRate * (1 + monthlyInterestRate).pow(totalPayments) / denominator

        val explanation = """
        Amortization Schedule Explanation
        
        Given:
        - Loan Amount = ₱${String.format("%.2f", loanAmount)}
        - Annual Interest Rate = ${String.format("%.2f", interestRate)}%
        - Loan Term = ${String.format("%.2f", loanTerm)} years
        
        Formula:
        M = P * [r(1+r)^n] / [(1+r)^n-1]
        
        Where:
        - M = Monthly payment
        - P = Principal loan amount
        - r = Monthly interest rate
        - n = Total number of payments
        
        Solution:
        - Monthly Interest Rate (r) = ${String.format("%.4f", monthlyInterestRate)} or ${String.format("%.2f", interestRate / 12)}%
        - Total Number of Payments (n) = ${totalPayments} (${String.format("%.2f", loanTerm)} years x 12 months)
        - Monthly Payment (M) = ₱${String.format("%.2f", monthlyPayment)}
        
        Therefore, the Monthly Payment for the given loan is ₱${String.format("%.2f", monthlyPayment)}
    """.trimIndent()

        // Display explanation in a custom dialog
        val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Amortization Schedule")
            .setMessage(explanation)
            .setPositiveButton("OK", null)

        // Allow scrolling for long explanations
        val dialog = dialogBuilder.create()
        dialog.setView(NestedScrollView(requireContext()).apply {
            addView(TextView(requireContext()).apply {
                text = explanation
                movementMethod = LinkMovementMethod.getInstance()
                setTextIsSelectable(true)
            })
        })
        dialog.show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.mtcdb.stem.mathtrix.R.string.calculator)
        super.onDestroy()
    }
}
