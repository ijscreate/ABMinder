package com.ijs.abminder.calculator.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.calculator.calculatoroptions.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.ijs.abminder.calculator.CalculatorOptionsActivity
import kotlin.math.pow

class AmortizationFragment : Fragment() {

    private lateinit var stepTextView : TextView
    private lateinit var stepInputLayout : TextInputLayout
    private lateinit var stepInputEditText : TextInputEditText
    private lateinit var nextButton : Button
    private lateinit var tableLayout : TableLayout

    private var currentStep = 1
    private var loanAmount : Double? = null
    private var interestRate : Double? = null
    private var loanTerm : Double? = null
    private var monthlyInterestRate : Double? = null
    private var totalPayments : Double? = null
    private var monthlyPayment : Double? = null

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val rootView = inflater.inflate(R.layout.fragment_amortization_schedule, container, false)

        stepTextView = rootView.findViewById(R.id.stepTextView)
        stepInputLayout = rootView.findViewById(R.id.stepInputLayout)
        stepInputEditText = rootView.findViewById(R.id.stepInputEditText)
        nextButton = rootView.findViewById(R.id.nextButton)
        tableLayout = rootView.findViewById(R.id.tableLayoutAmortization)

        setupStep(currentStep)

        nextButton.setOnClickListener {
            handleNextStep()
        }

        val desc = """
            Calculate an amortization schedule to determine the monthly payments on a loan, including the interest and principal components.
        
            Formula:
                Monthly Payment = (Loan Amount * Monthly Interest Rate) /
                                  (1 - (1 + Monthly Interest Rate)^(-Total Payments))        
            Example:
        
            Suppose you take out a loan of ₱100,000 with an interest rate of 5% per annum for 3 years.
            The monthly payment would be calculated as follows:
        
            1. Calculate the monthly interest rate:
               Monthly Interest Rate = Annual Interest Rate / 12
                                    = 5% / 12
                                    = 0.05 / 12
                                    = 0.004167 (approximately)
        
            2. Calculate the total number of payments:
               Total Payments = Loan Term (in years) * 12
                             = 3 * 12
                             = 36
        
            3. Calculate the monthly payment using the formula:
               Monthly Payment = (Loan Amount * Monthly Interest Rate) /
                                 (1 - (1 + Monthly Interest Rate)^(-Total Payments))
        
                              = (100000 * 0.004167) /
                                 (1 - (1 + 0.004167)^(-36))
        
               This calculation yields the monthly payment amount.
        
            4. Generate the amortization schedule, which breaks down each payment into its
               principal and interest components, along with the remaining balance.
        
               Month |  Payment  |  Principal  |  Interest  |  Balance
               --------------------------------------------------------
               1     |  ₱2963.57  |  ₱588.57     |  ₱2375.00   |  ₱99411.43
               2     |  ₱2963.57  |  ₱592.10     |  ₱2371.47   |  ₱98819.33
               3     |  ₱2963.57  |  ₱595.64     |  ₱2367.93   |  ₱98223.69
               ...   |  ...       |  ...         |  ...       |  ...
               36    |  ₱2963.57  |  ₱2922.85    |  ₱40.72     |  ₱0.00
        
            This amortization schedule provides a detailed breakdown of each payment,
            helping you understand how much of each payment goes towards interest and principal,
            and how the loan balance decreases over time.
        """.trimIndent()
        rootView.findViewById<MaterialTextView>(R.id.descriptionTextView).text = desc

        return rootView
    }

    private fun setupStep(step : Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(com.ijs.abminder.R.string.step_1_input_loan_amount)
                stepInputLayout.hint = getString(R.string.loan_amount)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(com.ijs.abminder.R.string.step_2_input_interest_rate)
                stepInputLayout.hint = getString(R.string.interest_rate)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(com.ijs.abminder.R.string.step_3_input_loan_term)
                stepInputLayout.hint = getString(R.string.loan_term_years)
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text =
                    getString(com.ijs.abminder.R.string.step_4_calculate_monthly_interest_rate)
                stepInputLayout.hint = getString(
                    com.ijs.abminder.R.string.step_4_monthly_interest_rate_hint,
                    interestRate,
                    12
                )
                stepInputEditText.setText("")
            }

            5 -> {
                stepTextView.text =
                    getString(com.ijs.abminder.R.string.step_5_calculate_monthly_payment)
                stepInputLayout.visibility = View.GONE
                calculateMonthlyPayment()
                generateAmortizationTable()
            }
        }
    }

    private fun handleNextStep() {
        when (currentStep) {
            1 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    loanAmount = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    interestRate = input
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    loanTerm = input
                    currentStep = 4
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            4 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    val expectedMonthlyInterestRate = interestRate!! / 12 / 100
                    if (input == expectedMonthlyInterestRate) {
                        monthlyInterestRate = input
                        currentStep = 5
                        setupStep(currentStep)
                    } else {
                        showMonthlyInterestRateErrorToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }
        }
    }

    private fun calculateMonthlyPayment() {
        totalPayments = loanTerm!! * 12
        val denominator = (1 + monthlyInterestRate!!).pow(totalPayments!!) - 1
        monthlyPayment =
            loanAmount!! * monthlyInterestRate!! * (1 + monthlyInterestRate!!).pow(totalPayments!!) / denominator
    }

    private fun generateAmortizationTable() {
        tableLayout.removeAllViews() // Clear the table

        // Add table headers
        val headerRow = TableRow(context)
        headerRow.addView(createTableCell("Month"))
        headerRow.addView(createTableCell("Payment"))
        headerRow.addView(createTableCell("Principal"))
        headerRow.addView(createTableCell("Interest"))
        headerRow.addView(createTableCell("Balance"))
        tableLayout.addView(headerRow)

        var balance = loanAmount!!
        for (month in 1..totalPayments!!.toInt()) {
            val interest = balance * monthlyInterestRate!!
            val principal = monthlyPayment!! - interest
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

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showMonthlyInterestRateErrorToast() {
        Toast.makeText(
            requireContext(),
            com.ijs.abminder.R.string.incorrect_monthly_interest_rate,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.ijs.abminder.R.string.calculator)
        (activity).enableRecyclerView()
        super.onDestroy()
    }
}

