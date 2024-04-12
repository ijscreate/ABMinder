package com.ijs.abminder.calculator.options

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.ijs.abminder.R
import com.ijs.abminder.calculator.CalculatorOptionsActivity
import kotlin.math.pow

class AnnuityPaymentCalculatorFragment : Fragment() {

    private lateinit var principalEditText : TextInputEditText
    private lateinit var interestRateEditText : TextInputEditText
    private lateinit var timePeriodEditText : TextInputEditText
    private lateinit var paymentFrequencySpinner : Spinner
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var description : TextView
    private lateinit var buttonSolution : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_annuity_payment,
            container,
            false
        )

        // Initialize UI components
        principalEditText = view.findViewById(R.id.editTextPrincipal)
        interestRateEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextInterestRate)
        timePeriodEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextTimePeriod)
        paymentFrequencySpinner =
            view.findViewById(com.calculator.calculatoroptions.R.id.spinnerPaymentFrequency)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateAnnuityPayment)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewAnnuityPaymentResult)
        description = view.findViewById(R.id.description)
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution)

        // Set up payment frequency spinner
        val paymentFrequencies = arrayOf("Monthly", "Quarterly", "Semi-Annually", "Annually")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, paymentFrequencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        paymentFrequencySpinner.adapter = adapter

        calculateButton.setOnClickListener {
            calculateAnnuityPayment()
        }

        val annuityDescription = """
            The Annuity Payment Calculator determines the regular payments for an annuity based on the principal amount, interest rate, time period, and payment frequency.

            An annuity is a series of equal payments made at regular intervals. The formula to calculate the annuity payment is:

            Annuity Payment = P * (r * (1 + r/m)^(n*m)) / ((1 + r/m)^(n*m) - 1)

            Where:
            P = Principal amount
            r = Interest rate per period (expressed as a decimal)
            n = Total number of periods
            m = Number of payments per period (e.g., 12 for monthly, 4 for quarterly, 2 for semi-annual, 1 for annual)

            Let's consider an example to illustrate the calculation:

            Suppose you have a principal amount of ₱100,000, an annual interest rate of 5%, the annuity is to be paid monthly for 5 years (60 months), and the payment frequency is monthly. Using the formula:

            Annuity Payment = ₱100,000 * (0.05 * (1 + 0.05/12)^(60 * 12)) / ((1 + 0.05/12)^(60 * 12) - 1)

            In this example, the annuity payment would be the amount you need to pay each month to meet the terms of the annuity.
        """.trimIndent()

        description.text = annuityDescription

        buttonSolution.setOnClickListener {
            val principal = principalEditText.text.toString().toDoubleOrNull() ?: 0.0
            val interestRate = interestRateEditText.text.toString().toDoubleOrNull() ?: 0.0
            val timePeriod = timePeriodEditText.text.toString().toIntOrNull() ?: 0
            val paymentFrequency = when (paymentFrequencySpinner.selectedItem.toString()) {
                "Monthly" -> 12
                "Quarterly" -> 4
                "Semi-Annually" -> 2
                "Annually" -> 1
                else -> 12 // Default to monthly
            }

            val interestRatePerPeriod = interestRate / (100 * paymentFrequency)
            val totalPeriods = timePeriod * paymentFrequency

            val annuityPayment =
                principal * (interestRatePerPeriod * (1 + interestRatePerPeriod).pow(totalPeriods)) /
                        ((1 + interestRatePerPeriod).pow(totalPeriods) - 1)

            resultTextView.text = getString(
                com.calculator.calculatoroptions.R.string.annuity_payment_result,
                annuityPayment
            )

            showExplanationDialog(
                principal,
                interestRate,
                timePeriod,
                annuityPayment,
                interestRatePerPeriod,
                totalPeriods,
                paymentFrequency
            )
        }

        return view
    }

    private fun calculateAnnuityPayment() {
        val principal = principalEditText.text.toString().toDoubleOrNull() ?: 0.0
        val interestRate = interestRateEditText.text.toString().toDoubleOrNull() ?: 0.0
        val timePeriod = timePeriodEditText.text.toString().toIntOrNull() ?: 0
        val paymentFrequency = when (paymentFrequencySpinner.selectedItem.toString()) {
            "Monthly" -> 12
            "Quarterly" -> 4
            "Semi-Annually" -> 2
            "Annually" -> 1
            else -> 12 // Default to monthly
        }

        val interestRatePerPeriod = interestRate / (100 * paymentFrequency)
        val totalPeriods = timePeriod * paymentFrequency

        val annuityPayment =
            principal * (interestRatePerPeriod * (1 + interestRatePerPeriod).pow(totalPeriods)) /
                    ((1 + interestRatePerPeriod).pow(totalPeriods) - 1)

        resultTextView.text = getString(
            com.calculator.calculatoroptions.R.string.annuity_payment_result,
            annuityPayment
        )
    }

    private fun showExplanationDialog(
        principal : Double,
        interestRate : Double,
        timePeriod : Int,
        annuityPayment : Double,
        interestRatePerPeriod : Double,
        totalPeriods : Int,
        paymentFrequency : Int,
    ) {
        val explanation = """
            The Annuity Payment Calculator determines the regular payments for an annuity based on the principal amount, interest rate, time period, and payment frequency.

            Given:
                Principal Amount = ₱$principal
                Interest Rate (annual) = $interestRate%
                Time Period (years) = $timePeriod
                Payment Frequency = ${paymentFrequencySpinner.selectedItem}

            Solution:
                Interest Rate per Period = Annual Interest Rate / (100 * Payment Frequency)
                                        = $interestRate / (100 * $paymentFrequency)
                                        = $interestRatePerPeriod

                Total Number of Periods = Time Period * Payment Frequency
                                        = $timePeriod * $paymentFrequency
                                        = $totalPeriods

                Annuity Payment = Principal * (Interest Rate per Period * (1 + Interest Rate per Period)^Total Number of Periods) / ((1 + Interest Rate per Period)^Total Number of Periods - 1)
                                = ₱$principal * ($interestRatePerPeriod * (1 + $interestRatePerPeriod)^$totalPeriods) / ((1 + $interestRatePerPeriod)^$totalPeriods - 1)
                                ≈ ₱${"%.2f".format(annuityPayment)}

            Therefore, the Annuity Payment is approximately ₱${"%.2f".format(annuityPayment)}.
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Annuity Payment Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }
}