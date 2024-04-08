package com.mtcdb.stem.mathtrix.calculator.options

import android.annotation.*
import android.os.*
import android.view.*
import androidx.fragment.app.*
import com.google.android.material.button.*
import com.google.android.material.dialog.*
import com.google.android.material.textfield.*
import com.google.android.material.textview.*
import com.mtcdb.stem.mathtrix.*

class GDPFragment : Fragment() {

    private lateinit var initialGDPEditText : TextInputEditText
    private lateinit var finalGDPEditText : TextInputEditText
    private lateinit var initialYearEditText : TextInputEditText
    private lateinit var finalYearEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var description : MaterialTextView
    private lateinit var buttonSolution : MaterialButton

    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_gdp, container, false
        )

        // Initialize UI components
        initialGDPEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextInitialGDP)
        finalGDPEditText = view.findViewById(com.calculator.calculatoroptions.R.id.editTextFinalGDP)
        initialYearEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextInitialGDP)
        finalYearEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextFinalGDP)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateGDP)
        resultTextView = view.findViewById(com.calculator.calculatoroptions.R.id.textViewGDPResult)
        description = view.findViewById(com.calculator.calculatoroptions.R.id.tVDescription)
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution)

        calculateButton.setOnClickListener {
            calculateGDPGrowthRate()
        }

        val gdpDescription = """
            Gross Domestic Product (GDP) Growth Rate measures the annual percentage change in a country's economic output. It provides insights into the economic health and performance of a nation over a specific period.

            The formula for calculating GDP Growth Rate is:

            GDP Growth Rate = ((Final GDP - Initial GDP) / Initial GDP) * 100

            Breaking down the components of the formula:

            - Initial GDP represents the economic output at the beginning of the period.
            - Final GDP represents the economic output at the end of the period.

            Let's consider an example to illustrate the calculation:

            Suppose the Initial GDP is $1,000 billion, and the Final GDP is $1,200 billion over a period of 5 years.

            GDP Growth Rate = ((1,200 - 1,000) / 1,000) * 100 = 20%

            In this example, the GDP Growth Rate is 20%, indicating a positive economic growth of 20% over the specified period.

            Understanding the GDP Growth Rate is crucial for policymakers, economists, and investors to assess the overall economic performance and make informed decisions.
        """.trimIndent()

        description.text = gdpDescription

        buttonSolution.setOnClickListener {
            val initialGDP = initialGDPEditText.text.toString().toDoubleOrNull() ?: 0.0
            val finalGDP = finalGDPEditText.text.toString().toDoubleOrNull() ?: 0.0

            val gdpGrowthRate = ((finalGDP - initialGDP) / initialGDP) * 100

            resultTextView.text =
                getString(com.calculator.calculatoroptions.R.string.gdp_result, gdpGrowthRate)
            showExplanationDialog(initialGDP, finalGDP, gdpGrowthRate)
        }

        return view
    }

    override fun onDestroy() {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }

    private fun calculateGDPGrowthRate() {
        val initialGDP = initialGDPEditText.text.toString().toDoubleOrNull() ?: 0.0
        val finalGDP = finalGDPEditText.text.toString().toDoubleOrNull() ?: 0.0

        val gdpGrowthRate = ((finalGDP - initialGDP) / initialGDP) * 100

        resultTextView.text =
            getString(com.calculator.calculatoroptions.R.string.gdp_result, gdpGrowthRate)
    }

    private fun showExplanationDialog(
        initialGDP : Double,
        finalGDP : Double,
        gdpGrowthRate : Double,
    ) {
        val explanation = """
        Gross Domestic Product (GDP) Growth Rate measures the annual percentage change in a country's economic output. It is calculated using the formula:

        GDP Growth Rate = ((Final GDP - Initial GDP) / Initial GDP) * 100
        
         Given:
            Initial GDP = $initialGDP
            Final GDP = $finalGDP

        Step-by-step Calculation:
        
        1. Subtract the Initial GDP from the Final GDP to find the change in GDP:
           Change in GDP = Final GDP - Initial GDP
                      = $finalGDP - $initialGDP
                      = ${(finalGDP - initialGDP)}

        2. Divide the change in GDP by the Initial GDP to get the relative change:
           Relative Change = Change in GDP / Initial GDP
                          = ${(finalGDP - initialGDP)} / $initialGDP
                          = ${(finalGDP - initialGDP) / initialGDP}

        3. Multiply the relative change by 100 to express it as a percentage:
           GDP Growth Rate = Relative Change * 100
                           = (${(finalGDP - initialGDP) / initialGDP}) * 100
                           = $gdpGrowthRate%

        Therefore, the GDP Growth Rate is $gdpGrowthRate%.
    """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Gross Domestic Product (GDP) Growth Rate Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }

}




