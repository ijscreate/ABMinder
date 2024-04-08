package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.calculator.calculatoroptions.*
import com.google.android.material.button.*
import com.google.android.material.dialog.*
import com.google.android.material.textfield.*
import com.google.android.material.textview.*

class EquityMultiplierFragment : Fragment() {

    private lateinit var totalAssetsEditText : TextInputEditText
    private lateinit var totalEquityEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var description : TextView
    private lateinit var buttonSolution : Button

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val view = inflater.inflate(
            R.layout.fragment_equity_multiplier,
            container,
            false
        )

        // Initialize UI components
        totalAssetsEditText = view.findViewById(R.id.editTextTotalAssets)
        totalEquityEditText = view.findViewById(R.id.editTextTotalEquity)
        calculateButton = view.findViewById(R.id.buttonCalculateEquityMultiplier)
        resultTextView = view.findViewById(R.id.textViewEquityMultiplierResult)
        description = view.findViewById(R.id.description)
        buttonSolution = view.findViewById(R.id.buttonSolution)

        calculateButton.setOnClickListener {
            calculateEquityMultiplier()
        }

        val equityMultiplierDescription = """
            The Equity Multiplier is a financial metric used to assess the financial leverage of a company by measuring its equity multiplier. The formula for calculating the Equity Multiplier is:
            
            Equity Multiplier = Total Assets / Total Equity
            
            Where:
            - Total Assets represent the total assets owned by the company.
            - Total Equity represents the total equity or net worth of the company.
            
            Let's consider an example to illustrate the calculation:
            
            Suppose a company has total assets of ₱500,000 and total equity of ₱200,000.
            
            Equity Multiplier = ₱500,000 / ₱200,000
                               = 2.5
                               
            In this example, the Equity Multiplier is 2.5, indicating that for every peso of equity, the company has ₱2.5 of assets.
        """.trimIndent()

        description.text = equityMultiplierDescription

        buttonSolution.setOnClickListener {
            val totalAssets = totalAssetsEditText.text.toString().toDoubleOrNull() ?: 0.0
            val totalEquity = totalEquityEditText.text.toString().toDoubleOrNull() ?: 0.0

            val equityMultiplier = totalAssets / totalEquity

            resultTextView.text =
                getString(
                    R.string.equity_multiplier_result,
                    equityMultiplier
                )
            showExplanationDialog(totalAssets, totalEquity, equityMultiplier)
        }

        return view
    }

    private fun calculateEquityMultiplier() {
        val totalAssets = totalAssetsEditText.text.toString().toDoubleOrNull() ?: 0.0
        val totalEquity = totalEquityEditText.text.toString().toDoubleOrNull() ?: 0.0

        val equityMultiplier = totalAssets / totalEquity

        resultTextView.text =
            getString(
                R.string.equity_multiplier_result,
                equityMultiplier
            )
    }

    override fun onDestroy() {
        val mainActivity = requireActivity() as com.mtcdb.stem.mathtrix.MainActivity
        mainActivity.toolbar.title = getString(com.mtcdb.stem.mathtrix.R.string.calculator)
        super.onDestroy()
    }

    private fun showExplanationDialog(
        totalAssets : Double,
        totalEquity : Double,
        equityMultiplier : Double,
    ) {
        val explanation = """
            The Equity Multiplier is a financial metric used to assess the financial leverage of a company by measuring its equity multiplier.
            
            Given:
                Total Assets = ₱$totalAssets
                Total Equity = ₱$totalEquity
                
            Solution:
                Equity Multiplier = Total Assets / Total Equity
                                  = ₱$totalAssets / ₱$totalEquity
                                  = ${"%.2f".format(equityMultiplier)}
                                  
            Therefore, the Equity Multiplier is ${"%.2f".format(equityMultiplier)}.
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Equity Multiplier Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
