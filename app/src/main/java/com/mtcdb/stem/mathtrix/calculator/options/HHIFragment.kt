package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.dialog.*
import com.google.android.material.textfield.*
import com.mtcdb.stem.mathtrix.*
import com.mtcdb.stem.mathtrix.calculator.*

class HHIFragment : Fragment() {

    private lateinit var totalMarketSharesEditText : TextInputEditText
    private lateinit var marketShare1EditText : TextInputEditText
    private lateinit var marketShare2EditText : TextInputEditText
    private lateinit var marketShare3EditText : TextInputEditText
    private lateinit var calculateButton : Button
    private lateinit var textViewHHIResult : TextView
    private lateinit var descriptionTextView : TextView
    private lateinit var explanationButton : Button

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_hhi,
            container,
            false
        )

        // Initialize UI components
        totalMarketSharesEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextTotalMarketShares)
        marketShare1EditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextMarketShare1)
        marketShare2EditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextMarketShare2)
        marketShare3EditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextMarketShare3)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateHHI)
        textViewHHIResult =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewHHIResult)
        descriptionTextView = view.findViewById(R.id.description)
        explanationButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonExplanation)

        calculateButton.setOnClickListener {
            calculateHHI()
        }

        explanationButton.setOnClickListener {
            val totalMarketShares =
                totalMarketSharesEditText.text.toString().toDoubleOrNull() ?: 0.0
            val marketShare1 = marketShare1EditText.text.toString().toDoubleOrNull() ?: 0.0
            val marketShare2 = marketShare2EditText.text.toString().toDoubleOrNull() ?: 0.0
            val marketShare3 = marketShare3EditText.text.toString().toDoubleOrNull() ?: 0.0

            val hhi =
                (marketShare1 * marketShare1) + (marketShare2 * marketShare2) + (marketShare3 * marketShare3)
            textViewHHIResult.text =
                getString(com.calculator.calculatoroptions.R.string.hhi_result, hhi)
            showExplanationDialog(totalMarketShares, marketShare1, marketShare2, marketShare3, hhi)

        }
        return view
    }

    private fun calculateHHI() {
        val totalMarketShares = totalMarketSharesEditText.text.toString().toDoubleOrNull() ?: 0.0
        val marketShare1 = marketShare1EditText.text.toString().toDoubleOrNull() ?: 0.0
        val marketShare2 = marketShare2EditText.text.toString().toDoubleOrNull() ?: 0.0
        val marketShare3 = marketShare3EditText.text.toString().toDoubleOrNull() ?: 0.0

        val hhi =
            (marketShare1 * marketShare1) + (marketShare2 * marketShare2) + (marketShare3 * marketShare3)
        textViewHHIResult.text =
            getString(com.calculator.calculatoroptions.R.string.hhi_result, hhi)
    }

    private fun showExplanationDialog(
        totalMarketShares : Double,
        marketShare1 : Double,
        marketShare2 : Double,
        marketShare3 : Double,
        hhi : Double,
    ) {
        val explanation = """
            The Herfindahl-Hirschman Index (HHI) is a measure of market concentration.

            Given:
                Total Market Shares = $totalMarketShares
                Market Share 1 = $marketShare1
                Market Share 2 = $marketShare2
                Market Share 3 = $marketShare3

            Solution:
                HHI = (Market Share 1)² + (Market Share 2)² + (Market Share 3)²
                    = ($marketShare1)² + ($marketShare2)² + ($marketShare3)²
                    = ${marketShare1 * marketShare1} + ${marketShare2 * marketShare2} + ${marketShare3 * marketShare3}
                    = $hhi

            Therefore, the Herfindahl-Hirschman Index (HHI) for the given market shares is $hhi.
        """.trimIndent()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("HHI Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.mtcdb.stem.mathtrix.R.string.calculator)
        super.onDestroy()
    }
}