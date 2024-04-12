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
import com.ijs.abminder.R
import com.ijs.abminder.calculator.CalculatorOptionsActivity

class CommissionFragment : Fragment() {

    private lateinit var saleAmountEditText : TextInputEditText
    private lateinit var commissionRateEditText : TextInputEditText
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
            com.calculator.calculatoroptions.R.layout.fragment_commision,
            container,
            false
        )

        // Initialize UI components
        saleAmountEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextSaleAmount)
        commissionRateEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextCommissionRate)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateCommission)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewCommissionResult)
        description = view.findViewById(R.id.description)
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution)

        calculateButton.setOnClickListener {
            calculateCommission()
        }

        val commissionDescription = """
            The Commission Calculator computes the commission earned based on a sale amount and commission rate.

            The formula for calculating commission is:

            Commission = Sale Amount * (Commission Rate / 100)

            Adjust the sale amount and commission rate to see how they impact the commission earned.
        """.trimIndent()

        description.text = commissionDescription

        buttonSolution.setOnClickListener {
            val saleAmount = saleAmountEditText.text.toString().toDoubleOrNull() ?: 0.0
            val commissionRate = commissionRateEditText.text.toString().toDoubleOrNull() ?: 0.0

            val commission = saleAmount * (commissionRate / 100)

            resultTextView.text =
                getString(com.calculator.calculatoroptions.R.string.commission_result, commission)
            showExplanationDialog(saleAmount, commissionRate, commission)
        }

        return view
    }

    private fun calculateCommission() {
        val saleAmount = saleAmountEditText.text.toString().toDoubleOrNull() ?: 0.0
        val commissionRate = commissionRateEditText.text.toString().toDoubleOrNull() ?: 0.0

        val commission = saleAmount * (commissionRate / 100)

        resultTextView.text =
            getString(com.calculator.calculatoroptions.R.string.commission_result, commission)
    }

    private fun showExplanationDialog(
        saleAmount : Double,
        commissionRate : Double,
        commission : Double,
    ) {
        val explanation = """
            The Commission Calculator computes the commission earned based on a sale amount and commission rate.

            Given:
                Sale Amount = $saleAmount
                Commission Rate = $commissionRate%

            Solution:
                Commission = Sale Amount * (Commission Rate / 100)
                           = $saleAmount * ($commissionRate / 100)
                           = $commission

            Therefore, the commission earned is $commission.
        """.trimIndent()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Commission Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }
}
