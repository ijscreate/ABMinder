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

class GDPGrowthRateFragment : Fragment() {

    private lateinit var stepTextView: TextView
    private lateinit var stepInputLayout: TextInputLayout
    private lateinit var stepInputEditText: TextInputEditText
    private lateinit var nextButton: Button
    private lateinit var resultTextView: TextView

    private var currentStep = 1
    private var previousGDP: Double? = null
    private var currentGDP: Double? = null
    private var gdpGrowthRate: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(com.calculator.calculatoroptions.R.layout.fragment_gdp_growth_rate, container, false)

        stepTextView = rootView.findViewById(R.id.stepTextView)
        stepInputLayout = rootView.findViewById(R.id.stepInputLayout)
        stepInputEditText = rootView.findViewById(R.id.stepInputEditText)
        nextButton = rootView.findViewById(R.id.nextButton)
        resultTextView = rootView.findViewById(R.id.resultTextView)

        setupStep(currentStep)

        nextButton.setOnClickListener {
            handleNextStep()
        }

        val description = rootView.findViewById<TextView>(R.id.descriptionTextView)
        val desc = """
            Calculate the GDP Growth Rate using the formula:
            
            GDP Growth Rate = (Current GDP - Previous GDP) / Previous GDP × 100
            
            This calculator helps you determine the GDP growth rate of a country or region based on the previous GDP and current GDP.
            
            Example:
            
                If the previous GDP was ₱1,000,000 and the current GDP is ₱1,050,000, the GDP growth rate would be:
                
                Step 1: Calculate the difference
                   Current GDP - Previous GDP = 1,050,000 - 1,000,000 = 50,000
                
                Step 2: Divide the difference by the previous GDP
                   50,000 / 1,000,000 = 0.05
                
                Step 3: Multiply by 100 to get the percentage
                   0.05 × 100 = 5.00%
                
            Therefore, the GDP growth rate is 5.00%.
        """.trimIndent()
        description.text = desc

        return rootView
    }

    private fun setupStep(step: Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_previous_gdp)
                stepInputLayout.hint = getString(R.string.previous_gdp)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_current_gdp)
                stepInputLayout.hint = getString(R.string.current_gdp)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_calculate_gdp_difference)
                stepInputLayout.hint = getString(R.string.step_3_calculate_gdp_difference_hint, previousGDP, currentGDP)
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_divide_by_previous_gdp)
                stepInputLayout.hint = getString(R.string.step_4_divide_by_previous_gdp_hint, currentGDP, previousGDP)
                stepInputEditText.setText("")
            }

            5 -> {
                stepTextView.text = getString(R.string.step_5_multiply_by_100)
                stepInputLayout.hint = getString(R.string.step_5_multiply_by_100_hint, gdpGrowthRate)
                stepInputEditText.setText("")
            }

            6 -> {
                stepTextView.text = getString(R.string.step_6_result)
                stepInputLayout.visibility = View.GONE
                nextButton.text = getString(R.string.exit)
                nextButton.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction().remove(this)
                        .commit()
                }
                displayResult()
            }
        }
    }

    private fun handleNextStep() {
        when (currentStep) {
            1 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    previousGDP = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    currentGDP = input
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val gdpDifference = currentGDP!! - previousGDP!!
                stepInputEditText.setText(gdpDifference.toString())
                currentStep = 4
                setupStep(currentStep)
            }

            4 -> {
                val gdpDifference = currentGDP!! - previousGDP!!
                val gdpDifferenceOverPreviousGDP = gdpDifference / previousGDP!!
                stepInputEditText.setText(gdpDifferenceOverPreviousGDP.toString())
                currentStep = 5
                setupStep(currentStep)
            }

            5 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    gdpGrowthRate = input * 100
                    currentStep = 6
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }
        }
    }

    private fun displayResult() {
        resultTextView.text = getString(R.string.gdp_growth_rate_result, gdpGrowthRate!!)
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        (activity).enableRecyclerView()
        super.onDestroy()
    }
}
