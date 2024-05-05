package com.ijs.abminder.calculator.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ijs.abminder.R
import com.ijs.abminder.calculator.CalculatorOptionsActivity
import kotlin.math.pow

class MortgageFragment : Fragment() {

    private lateinit var stepTextView : TextView
    private lateinit var stepInputLayout : TextInputLayout
    private lateinit var stepInputEditText : TextInputEditText
    private lateinit var nextButton : Button
    private lateinit var resultTextView : TextView
    private lateinit var descriptionTextView : TextView

    private var currentStep = 1
    private var loanAmount : Double? = null
    private var interestRate : Double? = null
    private var loanTerm : Int? = null
    private var interestRatePerMonth : Double? = null
    private var loanTermInMonths : Int? = null
    private var monthlyPayment : Double? = null

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val rootView = inflater.inflate(com.calculator.calculatoroptions.R.layout.fragment_mortrage, container, false)

        stepTextView = rootView.findViewById(R.id.stepTextView)
        stepInputLayout = rootView.findViewById(R.id.stepInputLayout)
        stepInputEditText = rootView.findViewById(R.id.stepInputEditText)
        nextButton = rootView.findViewById(R.id.nextButton)
        resultTextView = rootView.findViewById(R.id.resultTextView)
        descriptionTextView = rootView.findViewById(R.id.descriptionTextView)

        setupStep(currentStep)

        nextButton.setOnClickListener {
            handleNextStep()
        }

        val description = """
            The Mortgage Calculator helps you determine the monthly payment for a mortgage loan.

            The formula for calculating the monthly payment is:

            Monthly Payment = Loan Amount * [(Interest Rate / 12) / (1 - (1 + (Interest Rate / 12))^(-Loan Term in Months))]

            To use this calculator, follow these steps:
            1. Enter the loan amount.
            2. Enter the annual interest rate.
            3. Enter the loan term in years.
            4. Calculate the interest rate per month.
            5. Calculate the loan term in months.
            6. Calculate the monthly payment.
            7. Verify the final result.
        """.trimIndent()
        descriptionTextView.text = description

        return rootView
    }

    private fun setupStep(step : Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_loan_amount)
                stepInputLayout.hint = getString(R.string.loan_amount)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_interest_rate)
                stepInputLayout.hint = getString(R.string.interest_rate)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_input_loan_term)
                stepInputLayout.hint = getString(R.string.loan_term)
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_calculate_interest_rate_per_month)
                stepInputLayout.hint = getString(R.string.interest_rate_per_month)
                stepInputEditText.setText("")
            }

            5 -> {
                stepTextView.text = getString(R.string.step_5_calculate_loan_term_in_months)
                stepInputLayout.hint = getString(R.string.loan_term_in_months)
                stepInputEditText.setText("")
            }

            6 -> {
                stepTextView.text = getString(R.string.step_6_calculate_monthly_payment)
                stepInputLayout.visibility = View.GONE
                nextButton.text = getString(R.string.calculate)
            }

            7 -> {
                stepTextView.text = getString(R.string.step_7_result)
                stepInputLayout.visibility = View.GONE
                nextButton.text = getString(R.string.exit)
                nextButton.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction().remove(this)
                        .commit()
                }
                displayResult()
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
                val input = stepInputEditText.text.toString().toIntOrNull()
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
                    val expectedInterestRatePerMonth = interestRate!! / 12
                    if (input == expectedInterestRatePerMonth) {
                        interestRatePerMonth = input
                        currentStep = 5
                        setupStep(currentStep)
                    } else {
                        showIncorrectInterestRatePerMonthToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }

            5 -> {
                val input = stepInputEditText.text.toString().toIntOrNull()
                if (input != null) {
                    val expectedLoanTermInMonths = loanTerm!! * 12
                    if (input == expectedLoanTermInMonths) {
                        loanTermInMonths = input
                        currentStep = 6
                        setupStep(currentStep)
                    } else {
                        showIncorrectLoanTermInMonthsToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }

            6 -> {
                calculateMonthlyPayment()
                currentStep = 7
                setupStep(currentStep)
            }
        }
    }

    private fun calculateMonthlyPayment() {
        monthlyPayment =
            loanAmount!! * (interestRatePerMonth!! / (1 - (1 + interestRatePerMonth!!).pow(-loanTermInMonths!!)))
    }

    private fun displayResult() {
        resultTextView.text = getString(R.string.monthly_payment_result, monthlyPayment!!)
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showIncorrectInterestRatePerMonthToast() {
        Toast.makeText(
            requireContext(),
            R.string.incorrect_interest_rate_per_month,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showIncorrectLoanTermInMonthsToast() {
        Toast.makeText(
            requireContext(),
            R.string.incorrect_loan_term_in_months,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        (activity).enableRecyclerView()
        super.onDestroy()
    }

}
