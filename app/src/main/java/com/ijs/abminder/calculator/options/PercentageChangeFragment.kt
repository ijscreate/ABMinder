package com.ijs.abminder.calculator.options

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ijs.abminder.R
import com.ijs.abminder.calculator.CalculatorOptionsActivity

class PercentageChangeFragment : Fragment() {

    private lateinit var originalValueEditText : EditText
    private lateinit var newValueEditText : EditText
    private lateinit var calculateButton : Button
    private lateinit var resultTextView : TextView
    private lateinit var explainButton : Button
    private lateinit var descriptionTextView : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val rootView = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_percentage_change,
            container,
            false
        )

        originalValueEditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextOriginalValue)
        newValueEditText =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.editTextNewValue)
        calculateButton =
            rootView.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculate)
        resultTextView = rootView.findViewById(com.calculator.calculatoroptions.R.id.textViewResult)
        descriptionTextView = rootView.findViewById(R.id.tVCalculationOptionDescription)
        explainButton = rootView.findViewById(com.calculator.calculatoroptions.R.id.explainButton)

        calculateButton.setOnClickListener {
            calculateButton.clearFocus()
            calculatePercentageChange()
        }

        explainButton.setOnClickListener {
            showExplanationDialog()
        }

        val description = """
            Percentage Change is calculated using the formula:
            
            P = ((N - O) / O) * 100
            
            Where:
            N = The final value after the change (new value)
            O = The initial value before the change (original value)
            
            The result is expressed as a percentage, indicating the extent of the increase or decrease.
            
            Example:
            If the price of a product increases from $50 to $60, the percentage change is:
            
            Percentage Change = ((60 - 50) / 50) * 100 = 20%
            
            Therefore, the price has increased by 20%.
        """.trimIndent()

        descriptionTextView.text = description

        return rootView
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }

    @SuppressLint("StringFormatInvalid")
    private fun calculatePercentageChange() {
        val originalValue = originalValueEditText.text.toString().toDoubleOrNull() ?: 0.0
        val newValue = newValueEditText.text.toString().toDoubleOrNull() ?: 0.0

        val percentageChange = ((newValue - originalValue) / originalValue) * 100

        resultTextView.text = String.format("%.2f%%", percentageChange)
    }

    private fun showExplanationDialog() {
        val originalValue = originalValueEditText.text.toString().toDoubleOrNull() ?: 0.0
        val newValue = newValueEditText.text.toString().toDoubleOrNull() ?: 0.0
        val percentageChange = ((newValue - originalValue) / originalValue) * 100

        val explanation = """
            Given:
                Original Value = $originalValue
                New Value = $newValue
                
            Formula:
                Percentage Change = ((N - O / Original Value) * 100)
                
            Solution:
                Percentage Change = (($newValue - $originalValue) / $originalValue) * 100
                Percentage Change = $percentageChange%
        """.trimIndent()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Percentage Change")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
