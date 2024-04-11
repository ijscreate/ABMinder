package com.mtcdb.stem.mathtrix.calculator.options

import android.annotation.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.button.*
import com.google.android.material.dialog.*
import com.google.android.material.textfield.*
import com.google.android.material.textview.*
import com.mtcdb.stem.mathtrix.*
import com.mtcdb.stem.mathtrix.calculator.*

class RuleOf72Fragment : Fragment() {

    private lateinit var annualRateEditText : TextInputEditText
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
            com.calculator.calculatoroptions.R.layout.rule_of_72,
            container,
            false
        )

        // Initialize UI components
        annualRateEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextAnnualRate)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateRuleOf72)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewRuleOf72Result)
        description = view.findViewById(R.id.description)
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution1)

        calculateButton.setOnClickListener {
            val annualRate = annualRateEditText.text.toString().toDoubleOrNull()
            val yearsToDouble = annualRate?.let { 72 / it }

            if (yearsToDouble != null && yearsToDouble.isFinite() && yearsToDouble > 0) {
                resultTextView.text = getString(
                    com.calculator.calculatoroptions.R.string.rule_of_72_result,
                    yearsToDouble
                )
                showExplanationDialog(annualRate, yearsToDouble)
            } else {
                resultTextView.text =
                    getString(com.calculator.calculatoroptions.R.string.rule_of_72_result_invalid)
            }
        }

        val ruleOf72Description = """
            The Rule of 72 is a quick and simple way to estimate the number of years it takes for an investment to double in value based on a fixed annual rate of return.

            The formula for the Rule of 72 is:

            Years to Double ≈ 72 / Annual Rate of Return

            Adjust the annual rate of return to see how it impacts the number of years to double the investment.
        """.trimIndent()

        description.text = ruleOf72Description

        buttonSolution.setOnClickListener {
            calculateRuleOf72()
        }

        return view
    }

    private fun calculateRuleOf72() {
        val annualRate = annualRateEditText.text.toString().toDoubleOrNull()
        val yearsToDouble = annualRate?.let { 72 / it }

        if (yearsToDouble != null && yearsToDouble.isFinite() && yearsToDouble > 0) {
            resultTextView.text = getString(
                com.calculator.calculatoroptions.R.string.rule_of_72_result,
                yearsToDouble
            )
        } else {
            resultTextView.text =
                getString(com.calculator.calculatoroptions.R.string.rule_of_72_result_invalid)
        }
    }

    private fun showExplanationDialog(
        annualRate : Double,
        yearsToDouble : Double,
    ) {
        val explanation = """
            The Rule of 72 is a quick and simple way to estimate the number of years it takes for an investment to double in value based on a fixed annual rate of return.

            Given:
                Annual Rate of Return = $annualRate%
            
            Solution:
                Years to Double ≈ 72 / Annual Rate of Return
                                ≈ 72 / $annualRate
                                ≈ $yearsToDouble years
            
            Therefore, it takes approximately $yearsToDouble years for the investment to double in value.
        """.trimIndent()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Rule of 72 Calculation")
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
