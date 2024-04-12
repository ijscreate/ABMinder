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

class AverageCollectionPeriodFragment : Fragment() {

    private lateinit var netCreditSalesEditText : TextInputEditText
    private lateinit var averageAccountsReceivableEditText : TextInputEditText
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
            com.calculator.calculatoroptions.R.layout.fragment_average_collection_period,
            container,
            false
        )

        // Initialize UI components
        netCreditSalesEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextNetCreditSales)
        averageAccountsReceivableEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextAverageAccountsReceivable)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateAverageCollectionPeriod)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewAverageCollectionPeriodResult)
        description = view.findViewById(com.calculator.calculatoroptions.R.id.description)
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution)

        calculateButton.setOnClickListener {
            calculateAverageCollectionPeriod()
        }

        val acpDescription = """
            The Average Collection Period (ACP) is a financial metric that measures the average number of days it takes for a company to collect payments from its customers.
        
            The formula for calculating ACP is:
        
            ACP = (Average Accounts Receivable / Net Credit Sales) * Number of Days
        
            Where:
            - Average Accounts Receivable is the average amount of money owed to a company by its customers.
            - Net Credit Sales represent the total sales made on credit, excluding cash sales.
            - Number of Days is the period for which the average is calculated (usually 365 days for an annual average).
        
            Example:
            Let's consider a company with the following financial data over a year:
            Average Accounts Receivable = ₱50,000
            Net Credit Sales = ₱200,000
        
            ACP = (₱50,000 / ₱200,000) * 365
                ≈ 91.25 days
        
            In this example, the Average Collection Period is approximately 91.25 days, indicating that, on average, it takes the company around 91 days to collect payments.
        
            A lower ACP is generally favorable as it suggests that a company is efficient in collecting receivables. However, it's essential to compare ACP with industry benchmarks for a more meaningful interpretation.
        """.trimIndent()


        description.text = acpDescription

        buttonSolution.setOnClickListener {
            val netCreditSales = netCreditSalesEditText.text.toString().toDoubleOrNull() ?: 0.0
            val averageAccountsReceivable =
                averageAccountsReceivableEditText.text.toString().toDoubleOrNull() ?: 0.0

            val averageCollectionPeriod =
                (averageAccountsReceivable / netCreditSales) * 365

            resultTextView.text =
                getString(
                    com.calculator.calculatoroptions.R.string.average_collection_period_result,
                    averageCollectionPeriod
                )
            showExplanationDialog(
                netCreditSales,
                averageAccountsReceivable,
                averageCollectionPeriod
            )
        }

        return view
    }

    private fun calculateAverageCollectionPeriod() {
        val netCreditSales = netCreditSalesEditText.text.toString().toDoubleOrNull() ?: 0.0
        val averageAccountsReceivable =
            averageAccountsReceivableEditText.text.toString().toDoubleOrNull() ?: 0.0

        val averageCollectionPeriod =
            (averageAccountsReceivable / netCreditSales) * 365

        resultTextView.text =
            getString(
                com.calculator.calculatoroptions.R.string.average_collection_period_result,
                averageCollectionPeriod
            )
    }

    private fun showExplanationDialog(
        netCreditSales : Double,
        averageAccountsReceivable : Double,
        averageCollectionPeriod : Double,
    ) {
        val explanation = """
            The Average Collection Period (ACP) is a financial metric that measures the average number of days it takes for a company to collect payments from its customers.
            
            The formula for calculating ACP is:
            
            ACP = (Average Accounts Receivable / Net Credit Sales) * Number of Days
            
            Given:
                Net Credit Sales = $netCreditSales
                Average Accounts Receivable = $averageAccountsReceivable
                Number of Days = 365 (annual average)
                
            Solution:
                ACP = ($averageAccountsReceivable / $netCreditSales) * 365
                ACP = $averageCollectionPeriod days
                
            Therefore, the Average Collection Period is $averageCollectionPeriod days.
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Average Collection Period (ACP) Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.ijs.abminder.R.string.calculator)
        super.onDestroy()
    }
}