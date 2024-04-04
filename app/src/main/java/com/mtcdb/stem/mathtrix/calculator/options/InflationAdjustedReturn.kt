package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.dialog.*
import com.mtcdb.stem.mathtrix.*
import java.text.*

class InflationAdjustedReturnFragment : Fragment() {

    private lateinit var nominalReturnEditText : EditText
    private lateinit var inflationRateEditText : EditText
    private lateinit var calculateButton : Button
    private lateinit var resultTextView : TextView
    private lateinit var explanationButton : Button
    private lateinit var descriptionTextView : TextView

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_inflation_adjusted_return,
            container,
            false
        )

        // Initialize UI components
        nominalReturnEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextNominalReturn)
        inflationRateEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextInflationRate)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateInflationAdjustedReturn)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewInflationAdjustedReturnResult)
        explanationButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonExplanationInflationAdjustedReturn)
        descriptionTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewInflationAdjustedReturnDescription)

        calculateButton.setOnClickListener {
            calculateInflationAdjustedReturn()
        }

        explanationButton.setOnClickListener {
            showExplanationDialog()
        }

        val des = """
            The Inflation-Adjusted Return calculator helps you determine the real return on your investments after accounting for inflation.

            Formula:
            Inflation-Adjusted Return = (1 + (Nominal Return / 100)) / (1 + (Inflation Rate / 100)) - 1
    
            Example:
            If the Nominal Return is 5% and the Inflation Rate is 2%:
            Inflation-Adjusted Return = (1 + (5 / 100)) / (1 + (2 / 100)) - 1
            = 0.0291 or 2.91%
    
            Adjust the nominal return and inflation rate to see how they impact the inflation-adjusted return.
        """.trimIndent()
        descriptionTextView.text =
            des

        return view
    }

    override fun onDestroy() {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }

    private fun calculateInflationAdjustedReturn() {
        val nominalReturn = nominalReturnEditText.text.toString().toDoubleOrNull() ?: 0.0
        val inflationRate = inflationRateEditText.text.toString().toDoubleOrNull() ?: 0.0

        val inflationAdjustedReturn = (1 + (nominalReturn / 100)) / (1 + (inflationRate / 100)) - 1

        val decimalFormat = DecimalFormat("#.##")
        resultTextView.text =
            getString(
                com.calculator.calculatoroptions.R.string.inflation_adjusted_return_result,
                decimalFormat.format(inflationAdjustedReturn * 100)
            )
    }

    private fun showExplanationDialog() {
        val explanation = """
            To calculate the Inflation-Adjusted Return:

            Inflation-Adjusted Return = (1 + (Nominal Return / 100)) / (1 + (Inflation Rate / 100)) - 1

            For example:
            If the Nominal Return is 5% and the Inflation Rate is 2%:

            Inflation-Adjusted Return = (1 + (5 / 100)) / (1 + (2 / 100)) - 1
                                      = 0.0291 or 2.91%

            The Inflation-Adjusted Return helps investors understand the real return on their investments after accounting for inflation.

            Adjust the nominal return and inflation rate to see how they impact the inflation-adjusted return.
        """.trimIndent()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Inflation-Adjusted Return Calculation Explanation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
