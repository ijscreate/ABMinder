package com.ijs.abminder.calculator.options

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.ijs.abminder.calculator.CalculatorOptionsActivity

class EPSFragment : Fragment() {

    private lateinit var netIncomeEditText : TextInputEditText
    private lateinit var preferredDividendsEditText : TextInputEditText
    private lateinit var weightedAverageSharesEditText : TextInputEditText
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
            com.calculator.calculatoroptions.R.layout.fragment_eps,
            container,
            false
        )

        // Initialize UI components
        netIncomeEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextNetIncome)
        preferredDividendsEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextPreferredDividends)
        weightedAverageSharesEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextWeightedAverageShares)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateEPS)
        resultTextView = view.findViewById(com.calculator.calculatoroptions.R.id.textViewEPSResult)
        description = view.findViewById(com.calculator.calculatoroptions.R.id.description)
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution)

        calculateButton.setOnClickListener {
            calculateEarningsPerShare()
        }

        val epsDescription = """
    Earnings Per Share (EPS) is a crucial financial metric that measures the profitability of a company on a per-share basis. Understanding EPS is vital for investors as it indicates how much of the company's earnings are allocated to each outstanding share of common stock.

    The formula for calculating EPS is:
    
    EPS = (Net Income - Dividends on Preferred Stock) / Average Number of Outstanding Shares

    Breaking down the components of the formula:

    - Net Income represents the total profit a company has earned after deducting all operating expenses, taxes, and interest.
    - Dividends on Preferred Stock are the dividends paid to preferred shareholders, which need to be subtracted from net income to find the earnings available for common shareholders.
    - Average Number of Outstanding Shares is the average of the beginning and ending shares outstanding during a specific period. This accounts for any changes in the number of shares over time.

    Let's consider an example to illustrate the calculation:

    Suppose a company has a net income of $1,000,000, dividends on preferred stock of $50,000, and an average of 500,000 outstanding shares.

    EPS = ($1,000,000 - $50,000) / 500,000
        = $1.90 per share

    In this example, the EPS is $1.90, indicating that for every common share, the company has earned $1.90 in profit.

    Investors often use EPS as a key factor in assessing a company's financial health and performance. A higher EPS generally suggests better profitability, but it's essential to analyze EPS trends and consider other financial metrics for a comprehensive evaluation.

    Understanding EPS enables investors to make informed decisions, especially when comparing companies within the same industry or tracking a company's performance over time.
""".trimIndent()

        description.text = epsDescription

        buttonSolution.setOnClickListener {
            val netIncome = netIncomeEditText.text.toString().toDoubleOrNull() ?: 0.0
            val preferredDividends =
                preferredDividendsEditText.text.toString().toDoubleOrNull() ?: 0.0
            val weightedAverageShares =
                weightedAverageSharesEditText.text.toString().toDoubleOrNull() ?: 0.0

            val earningsPerShare = (netIncome - preferredDividends) / weightedAverageShares

            resultTextView.text =
                getString(com.calculator.calculatoroptions.R.string.eps_result, earningsPerShare)
            showExplanationDialog(
                netIncome,
                preferredDividends,
                weightedAverageShares,
                earningsPerShare
            )
        }

        return view
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.ijs.abminder.R.string.calculator)
        super.onDestroy()
    }

    private fun calculateEarningsPerShare() {
        val netIncome = netIncomeEditText.text.toString().toDoubleOrNull() ?: 0.0
        val preferredDividends = preferredDividendsEditText.text.toString().toDoubleOrNull() ?: 0.0
        val weightedAverageShares =
            weightedAverageSharesEditText.text.toString().toDoubleOrNull() ?: 0.0

        val earningsPerShare = (netIncome - preferredDividends) / weightedAverageShares

        resultTextView.text =
            getString(com.calculator.calculatoroptions.R.string.eps_result, earningsPerShare)
    }

    private fun showExplanationDialog(
        netIncome : Double,
        preferredDividends : Double,
        weightedAverageShares : Double,
        earningsPerShare : Double,
    ) {
        val explanation = """
        Earnings Per Share (EPS) is a financial metric that represents the portion of a company's profit allocated to each outstanding share of common stock.

        Formula:
        EPS = (Net Income - Preferred Dividends) / Weighted Average Shares Outstanding

        Step-by-step Calculation:

        1. Subtract Preferred Dividends from Net Income:
           Net Income - Preferred Dividends = $netIncome - $preferredDividends = ${netIncome - preferredDividends}

        2. Divide the result by Weighted Average Shares Outstanding:
           EPS = (Net Income - Preferred Dividends) / Weighted Average Shares Outstanding
               = ${netIncome - preferredDividends} / $weightedAverageShares
               = $earningsPerShare

        Given:
            Net Income = $netIncome
            Preferred Dividends = $preferredDividends
            Weighted Average Shares = $weightedAverageShares

        Therefore, the Earnings Per Share is $earningsPerShare.
    """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Earnings Per Share (EPS) Calculation Explanation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }

}
