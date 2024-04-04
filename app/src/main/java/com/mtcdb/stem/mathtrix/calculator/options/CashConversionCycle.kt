package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.calculator.calculatoroptions.*
import com.google.android.material.dialog.*
import com.google.android.material.textfield.*
import com.google.android.material.textview.*

class CashConversionCycleFragment : Fragment() {

    private lateinit var daysInventoryEditText : TextInputEditText
    private lateinit var daysReceivablesEditText : TextInputEditText
    private lateinit var daysPayablesEditText : TextInputEditText
    private lateinit var calculateButton : Button
    private lateinit var explanationButton : Button
    private lateinit var resultTextView : MaterialTextView
    private lateinit var descriptionTextView : MaterialTextView

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(R.layout.fragment_cash_conversion_cycle, container, false)

        daysInventoryEditText = view.findViewById(R.id.editTextDaysInventory)
        daysReceivablesEditText = view.findViewById(R.id.editTextDaysReceivables)
        daysPayablesEditText = view.findViewById(R.id.editTextDaysPayables)
        calculateButton = view.findViewById(R.id.buttonCalculateCashConversionCycle)
        explanationButton = view.findViewById(R.id.buttonExplanation)
        resultTextView = view.findViewById(R.id.textViewCashConversionCycleResult)
        descriptionTextView = view.findViewById(R.id.description)

        calculateButton.setOnClickListener {
            calculateCashConversionCycle()
        }

        explanationButton.setOnClickListener {
            showExplanationDialog()
        }

        setDescription()

        return view
    }

    private fun calculateCashConversionCycle() {
        val daysInventory = daysInventoryEditText.text.toString().toDoubleOrNull()
        val daysReceivables = daysReceivablesEditText.text.toString().toDoubleOrNull()
        val daysPayables = daysPayablesEditText.text.toString().toDoubleOrNull()

        if (daysInventory == null || daysReceivables == null || daysPayables == null) {
            resultTextView.text = getString(R.string.invalid_input)
            return
        }

        val cashConversionCycle = daysInventory + daysReceivables - daysPayables

        resultTextView.text = getString(R.string.cash_conversion_cycle_result, cashConversionCycle)
    }

    private fun setDescription() {
        descriptionTextView.text = getString(R.string.cash_conversion_cycle_description)
    }

    private fun showExplanationDialog() {
        val explanation = getString(R.string.cash_conversion_cycle_explanation)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.explanation))
            .setMessage(explanation)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
