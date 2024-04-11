package com.mtcdb.stem.mathtrix.calculator.options

import android.annotation.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.dialog.*
import com.mtcdb.stem.mathtrix.*
import com.mtcdb.stem.mathtrix.calculator.*
import kotlin.math.*

class CompoundInterestFragment : Fragment() {

    private lateinit var principalEditText: EditText
    private lateinit var rateEditText: EditText
    private lateinit var timeEditText: EditText
    private lateinit var calculateButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var explainButton: Button
    private lateinit var descriptionTextView: TextView
    private lateinit var compoundingPeriods: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val rootView =
            inflater.inflate(
                com.calculator.calculatoroptions.R.layout.fragment_compound_interest,
                container,
                false
            )

        principalEditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextPrincipal)
        rateEditText = rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextRate)
        timeEditText = rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextTime)
        calculateButton =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculate)
        resultTextView = rootView.findViewById(com.calculator.calculatoroptions.R.id.textViewResult)
        descriptionTextView = rootView.findViewById(R.id.tVCalculationOptionDescription)
        explainButton = rootView.findViewById(com.calculator.calculatoroptions.R.id.explainButton)
        compoundingPeriods =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextCompoundingPeriods)

        calculateButton.setOnClickListener {
            calculateButton.clearFocus()
            calculateCompoundInterest()
        }

        explainButton.setOnClickListener {
            showExplanationDialog()
            calculateCompoundInterest()
        }

        val description = """
            Compound Interest is calculated using the formula:
            
            A = P * (1 + (R / N))^(N * T)
            
            Where:
            A = Amount after interest
            P = Principal amount (initial amount of money)
            R = Annual interest rate (as a decimal)
            N = Number of times that interest is compounded per year
            T = Time the money is invested or borrowed for, in years
            
            To calculate Compound Interest, use the formula to find the total amount after interest and then subtract the principal amount.
            
            Example:
            Suppose you invest ₱1,000 at an annual interest rate of 5% compounded quarterly for 2 years.
            
            Given:
            Principal Amount (P) = ₱1,000
            Annual Interest Rate (R) = 5% or 0.05 (as a decimal)
            Number of Compounding Periods per Year (N) = 4 (quarterly)
            Time (T) = 2 years
            
            Calculation:
            A = 1000 * (1 + (0.05 / 4))^(4 * 2)
            A = 1000 * (1 + 0.0125)^8
            A ≈ 1000 * 1.1041
            A ≈ ₱1104.06
            
            Therefore, the Compound Interest earned is approximately ₱104.06.
        """.trimIndent()


        descriptionTextView.text = description

        return rootView
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.mtcdb.stem.mathtrix.R.string.calculator)
        super.onDestroy()
    }

    @SuppressLint("StringFormatInvalid")
    private fun calculateCompoundInterest() {
        val principal = principalEditText.text.toString().toDoubleOrNull() ?: 0.0
        val rate = rateEditText.text.toString().toDoubleOrNull() ?: 0.0
        val time = timeEditText.text.toString().toDoubleOrNull() ?: 0.0
        val periods = compoundingPeriods.text.toString().toDoubleOrNull() ?: 0.0
        val rated = rate / 100
        val perTime = periods * time

        val compoundInterest = principal * (1 + (rated / periods)).pow(perTime)
        val totalInterestEarned = compoundInterest - principal

        resultTextView.text = getString(
            com.calculator.calculatoroptions.R.string.compound_interest_result,
            compoundInterest,
            totalInterestEarned
        )
    }

    private fun showExplanationDialog() {
        val principal = principalEditText.text.toString().toDoubleOrNull() ?: 0.0
        val rate = rateEditText.text.toString().toDoubleOrNull() ?: 0.0
        val time = timeEditText.text.toString().toDoubleOrNull() ?: 0.0
        val periods = compoundingPeriods.text.toString().toDoubleOrNull() ?: 0.0
        val rated = rate / 100
        val perTime = periods * time

        // Calculate compound interest using the formula A = P * (1 + (R / N))^(N * T)
        val compoundInterest = principal * (1 + (rated / periods)).pow(perTime)
        val totalInterestEarned = compoundInterest - principal

        // Explanation of the calculation step by step
        val explanation = """
        Given:
            Principal Amount = $principal
            Annual Interest Rate = $rate%
            Time = $time years
            Compounding Periods per Year = $periods
            
        Formula:
            A = P * (1 + (R / N))^(N * T)
            
        Explanation:
            1. Divide the annual interest rate by the number of compounding periods per year to get the periodic interest rate:
               R / N = $rate / $periods = ${rate / periods}
            2. Multiply the number of compounding periods per year by the time to get the total number of compounding periods:
               N * T = $periods * $time = $perTime
            3. Add 1 to the periodic interest rate and raise it to the power of the total number of compounding periods:
               (1 + (R / N))^($periods * $time) = ${(1 + (rated / periods))}^$perTime
            4. Multiply the principal amount by the result to find the compound interest:
               A = $principal * ${(1 + (rated / periods)).pow(perTime)} = $compoundInterest
               A = $compoundInterest
            
        Therefore, the Total Compound Interest is $compoundInterest
        Total Interest Earned = $totalInterestEarned
    """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Compound Interest")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}