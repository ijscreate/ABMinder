package com.ijs.abminder.calculator.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ijs.abminder.R
import com.ijs.abminder.calculator.CalculatorOptionsActivity

class DTERFragment : Fragment() {

    private lateinit var stepTextView: TextView
    private lateinit var stepInputLayout: TextInputLayout
    private lateinit var stepInputEditText: TextInputEditText
    private lateinit var nextButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var descriptionTextView: TextView

    private var currentStep = 1
    private var totalDebt: Double? = null
    private var equity: Double? = null
    private var debtToEquityRatio: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_dter,
            container,
            false
        )

        // Initialize UI components
        stepTextView = view.findViewById(R.id.stepTextView)
        stepInputLayout = view.findViewById(R.id.stepInputLayout)
        stepInputEditText = view.findViewById(R.id.stepInputEditText)
        nextButton = view.findViewById(R.id.nextButton)
        resultTextView = view.findViewById(com.calculator.calculatoroptions.R.id.textViewDebtToEquityRatioResult)
        descriptionTextView = view.findViewById(com.calculator.calculatoroptions.R.id.tVDescription)

        setupStep(currentStep)

        nextButton.setOnClickListener {
            handleNextStep()
        }

        val description = """
            What Is Debt-to-Equity (D/E) Ratio?

            Debt-to-equity (D/E) ratio is used to evaluate a company's financial leverage and is calculated by dividing a company's total liabilities by its shareholder equity. D/E ratio is an important metric in corporate finance. It is a measure of the degree to which a company is financing its operations with debt rather than its own resources. Debt-to-equity ratio is a particular type of gearing ratio.

            It is calculated using the formula:

            Debt/Equity ratio = Total liabilities / Total shareholders equity

            D/E ratio measures how much debt a company has taken on relative to the value of its assets net of liabilities. Debt must be repaid or refinanced, imposes interest expense that typically can't be deferred, and could impair or destroy the value of equity in the event of a default. As a result, a high D/E ratio is often associated with high investment risk; it means that a company relies primarily on debt financing.

            Important:
            Debt-to-equity ratio is most useful when used to compare direct competitors. If a company's D/E ratio significantly exceeds those of others in its industry, then its stock could be more risky.
        """.trimIndent()

        descriptionTextView.text = description

        return view
    }

    private fun setupStep(step: Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_total_debt)
                stepInputLayout.hint = getString(R.string.total_debt)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_equity)
                stepInputLayout.hint = getString(R.string.equity)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_calculate_dter)
                stepInputLayout.hint = getString(R.string.step_3_dter_formula, totalDebt, equity)
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_result)
                stepInputLayout.visibility = View.GONE
                nextButton.text = getString(R.string.exit)
                nextButton.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction().remove(this)
                        .commit()
                    val activity = requireActivity() as CalculatorOptionsActivity
                    activity.toolbar.title = getString(R.string.calculator)
                }
                displayResult()
            }
        }
    }

    private fun handleNextStep() {
        when (currentStep) {
            1 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input >= 0) {
                    totalDebt = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input > 0) {
                    equity = input
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input >= 0) {
                    val expectedDebtToEquityRatio = (totalDebt ?: 0.0) / (equity ?: 0.0)
                    if (input == expectedDebtToEquityRatio) {
                        debtToEquityRatio = input
                        currentStep = 4
                        setupStep(currentStep)
                    } else {
                        showIncorrectCalculationToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }
        }
    }

    private fun displayResult() {
        resultTextView.text = getString(R.string.dter_result, debtToEquityRatio)
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showIncorrectCalculationToast() {
        Toast.makeText(requireContext(), R.string.incorrect_calculation, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }
}