package com.ijs.abminder.calculator.options

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

class DTERFragment : Fragment() {

    private lateinit var totalDebtEditText : TextInputEditText
    private lateinit var equityEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var descriptionTextView : TextView
    private lateinit var buttonSolution : Button

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_dter,
            container,
            false
        )

        // Initialize UI components
        totalDebtEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextTotalDebt)
        equityEditText = view.findViewById(com.calculator.calculatoroptions.R.id.editTextEquity)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateDebtToEquityRatio)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewDebtToEquityRatioResult)
        descriptionTextView = view.findViewById(com.calculator.calculatoroptions.R.id.tVDescription)
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution)

        calculateButton.setOnClickListener {
            calculateDebtToEquityRatio()
        }

        val description = """
            What Is Debt-to-Equity (D/E) Ratio?
            
                    Debt-to-equity (D/E) ratio is used to evaluate a company’s financial leverage and is calculated by dividing a company’s total liabilities by its shareholder equity. D/E ratio is an important metric in corporate finance. It is a measure of the degree to which a company is financing its operations with debt rather than its own resources. Debt-to-equity ratio is a particular type of gearing ratio. 
            It is calculated using the formmula:
                
                Debt/Equity ratio = Total liabilities / Total shareholders equity
                
                    D/E ratio measures how much debt a company has taken on relative to the value of its assets net of liabilities. Debt must be repaid or refinanced, imposes interest expense that typically can’t be deferred, and could impair or destroy the value of equity in the event of a default. As a result, a high D/E ratio is often associated with high investment risk; it means that a company relies primarily on debt financing.    
                
            Important: 
                    Debt-to-equity ratio is most useful when used to compare direct competitors. If a company’s D/E ratio significantly exceeds those of others in its industry, then its stock could be more risky.
        """.trimIndent()

        descriptionTextView.text = description

        buttonSolution.setOnClickListener {
            val totalDebt = totalDebtEditText.text.toString().toDoubleOrNull() ?: 0.0
            val equity = equityEditText.text.toString().toDoubleOrNull() ?: 0.0

            val debtToEquityRatio = totalDebt / equity

            resultTextView.text =
                getString(com.calculator.calculatoroptions.R.string.dter_result, debtToEquityRatio)
            showExplanationDialog(totalDebt, equity, debtToEquityRatio)
        }

        return view
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.ijs.abminder.R.string.calculator)
        super.onDestroy()
    }

    private fun calculateDebtToEquityRatio() {
        val totalDebt = totalDebtEditText.text.toString().toDoubleOrNull() ?: 0.0
        val equity = equityEditText.text.toString().toDoubleOrNull() ?: 0.0

        val debtToEquityRatio = totalDebt / equity

        resultTextView.text =
            getString(com.calculator.calculatoroptions.R.string.dter_result, debtToEquityRatio)
    }

    private fun showExplanationDialog(
        totalDebt : Double,
        equity : Double,
        debtToEquityRatio : Double,
    ) {
        val explanation = """
            Debt-to-equity (D/E) ratio is used to evaluate a company’s financial leverage and is calculated by dividing a company’s total liabilities by its shareholder equity. It is calculated using the formula:
            
            Debt to Equity Ratio = Total Liabilities / Total Shareholders Equity
            
            Given:
                Total Liabilities = $totalDebt
                Total Shareholders Equity = $equity
                
            Solution:
                Debt-to-Equity Ratio = $totalDebt / $equity
                Debt-to-Equity Ratio = $debtToEquityRatio
                
            Therefore, the Debt to Equity Ratio is $debtToEquityRatio.
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Debt to Equity Ratio Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
