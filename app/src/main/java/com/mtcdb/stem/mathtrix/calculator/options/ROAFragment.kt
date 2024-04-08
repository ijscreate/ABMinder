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

class ROAFragment : Fragment() {

    private lateinit var netIncomeEditText : TextInputEditText
    private lateinit var averageTotalAssetsEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var description : TextView
    private lateinit var buttonSolution : Button

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_roa,
            container,
            false
        )

        // Initialize UI components
        netIncomeEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextNetIncomeROA)
        averageTotalAssetsEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextAverageTotalAssetsROA)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateROA)
        resultTextView = view.findViewById(com.calculator.calculatoroptions.R.id.textViewROAResult)
        description = view.findViewById(com.calculator.calculatoroptions.R.id.descriptionROA)
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolutionROA)

        calculateButton.setOnClickListener {
            calculateReturnOnAssets()
        }

        val roaDescription = """
            Return on Assets (ROA) is a financial metric that measures a company's ability to generate earnings from its assets. It provides insight into how efficiently a company utilizes its assets to produce profit.

            The formula for calculating ROA is:

            ROA = (Net Income / Average Total Assets) * 100

            Breaking down the components of the formula:

            - Net Income represents the total profit a company has earned after deducting all operating expenses, taxes, and interest.
            - Average Total Assets is the average value of a company's assets over a specific period. This accounts for any fluctuations in asset values during that time.

            Let's consider an example to illustrate the calculation:

            Suppose a company has a net income of ₱500,000 and average total assets of ₱2,000,000.

            ROA = (₱500,000 / ₱2,000,000) * 100
                = 25%

            In this example, the ROA is 25%, indicating that the company generates 25 centavos in profit for every peso of assets.

            ROA is a key metric for investors, as it helps assess how efficiently a company converts its investments in assets into profits. A higher ROA is generally favorable, indicating better asset utilization.

            Understanding ROA is crucial for comparing companies within the same industry and evaluating a company's historical performance.
        """.trimIndent()


        description.text = roaDescription

        buttonSolution.setOnClickListener {
            val netIncome = netIncomeEditText.text.toString().toDoubleOrNull() ?: 0.0
            val averageTotalAssets =
                averageTotalAssetsEditText.text.toString().toDoubleOrNull() ?: 0.0

            val returnOnAssets = (netIncome / averageTotalAssets) * 100

            resultTextView.text =
                getString(com.calculator.calculatoroptions.R.string.roa_result, returnOnAssets)
            showExplanationDialog(netIncome, averageTotalAssets, returnOnAssets)
        }

        return view
    }

    override fun onDestroy() {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }

    private fun calculateReturnOnAssets() {
        val netIncome = netIncomeEditText.text.toString().toDoubleOrNull() ?: 0.0
        val averageTotalAssets = averageTotalAssetsEditText.text.toString().toDoubleOrNull() ?: 0.0

        val returnOnAssets = (netIncome / averageTotalAssets) * 100

        resultTextView.text =
            getString(com.calculator.calculatoroptions.R.string.roa_result, returnOnAssets)
    }

    private fun showExplanationDialog(
        netIncome : Double,
        averageTotalAssets : Double,
        returnOnAssets : Double,
    ) {
        val explanation = """
            Return on Assets (ROA) is a financial metric that measures a company's ability to generate earnings from its assets. It is calculated using the formula:
            
            ROA = (Net Income / Average Total Assets) * 100
            
            Given:
                Net Income = $netIncome
                Average Total Assets = $averageTotalAssets
                
            Solution:
                ROA = ($netIncome / $averageTotalAssets) * 100
                ROA = $returnOnAssets%
                
            Therefore, the Return on Assets is $returnOnAssets%.
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Return on Assets (ROA) Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
