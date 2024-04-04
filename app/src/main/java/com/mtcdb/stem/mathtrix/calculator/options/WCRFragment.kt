package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.button.*
import com.google.android.material.dialog.*
import com.google.android.material.textfield.*
import com.google.android.material.textview.*
import com.mtcdb.stem.mathtrix.*

class WorkingCapitalFragment : Fragment() {

    private lateinit var currentAssetsEditText : TextInputEditText
    private lateinit var currentLiabilitiesEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var description : TextView
    private lateinit var buttonSolution : Button

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragament_working_capital,
            container,
            false
        )

        // Initialize UI components
        currentAssetsEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextCurrentAssets)
        currentLiabilitiesEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextCurrentLiabilities)
        calculateButton = view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateWC)
        resultTextView = view.findViewById(com.calculator.calculatoroptions.R.id.textViewWCResult)
        description = view.findViewById(com.calculator.calculatoroptions.R.id.descriptionWC)
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolutionWC)

        calculateButton.setOnClickListener {
            calculateWorkingCapitalRatio()
        }

        val wcDescription = """
            Working Capital Ratio measures a company's ability to cover its short-term liabilities with its short-term assets. It is calculated using the formula:
            
            Working Capital Ratio = Current Assets / Current Liabilities
            
            Current Assets include cash, accounts receivable, and inventory. Current Liabilities include short-term debts and payables.
            
            A ratio above 1 indicates that the company has more assets than liabilities in the short term, suggesting good financial health.
            
            Conversely, a ratio below 1 may indicate potential liquidity issues.
        """.trimIndent()

        description.text = wcDescription

        buttonSolution.setOnClickListener {
            val currentAssets = currentAssetsEditText.text.toString().toDoubleOrNull() ?: 0.0
            val currentLiabilities =
                currentLiabilitiesEditText.text.toString().toDoubleOrNull() ?: 0.0

            val workingCapitalRatio = currentAssets / currentLiabilities

            resultTextView.text =
                getString(com.calculator.calculatoroptions.R.string.wc_result, workingCapitalRatio)
            showExplanationDialog(currentAssets, currentLiabilities, workingCapitalRatio)
        }

        return view
    }

    override fun onDestroy() {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }

    private fun calculateWorkingCapitalRatio() {
        val currentAssets = currentAssetsEditText.text.toString().toDoubleOrNull() ?: 0.0
        val currentLiabilities = currentLiabilitiesEditText.text.toString().toDoubleOrNull() ?: 0.0

        val workingCapitalRatio = currentAssets / currentLiabilities

        resultTextView.text =
            getString(com.calculator.calculatoroptions.R.string.wc_result, workingCapitalRatio)
        showExplanationDialog(currentAssets, currentLiabilities, workingCapitalRatio)
    }

    private fun showExplanationDialog(
        currentAssets : Double,
        currentLiabilities : Double,
        workingCapitalRatio : Double,
    ) {
        val explanation = """
            Working Capital Ratio is calculated using the formula:
            
            Working Capital Ratio = Current Assets / Current Liabilities
            
            Given:
                Current Assets = $currentAssets
                Current Liabilities = $currentLiabilities
                
            Solution:
                Working Capital Ratio = $currentAssets / $currentLiabilities
                Working Capital Ratio = $workingCapitalRatio
                
            Therefore, the Working Capital Ratio is $workingCapitalRatio.
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Working Capital Ratio Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
