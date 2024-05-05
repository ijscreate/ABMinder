package com.ijs.abminder.calculator.options

import android.annotation.SuppressLint
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

class GDPDeflatorFragment : Fragment() {

    private lateinit var stepTextView : TextView
    private lateinit var stepInputLayout : TextInputLayout
    private lateinit var stepInputEditText : TextInputEditText
    private lateinit var nextButton : Button
    private lateinit var resultTextView : TextView

    private var currentStep = 1
    private var nominalGDP : Double? = null
    private var realGDP : Double? = null
    private var gdpDeflator : Double? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val rootView = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_gdp_deflator,
            container,
            false
        )

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
            Calculate the GDP Deflator using the formula:
            
            GDP Deflator = (Nominal GDP / Real GDP) × 100
            
            This calculator helps you determine the GDP deflator, which measures the rate of price change in an economy.
            
            Example:
            
                If the Nominal GDP is ₱1,050,000 and the Real GDP is ₱1,000,000, the GDP Deflator would be:
                
                GDP Deflator = (1,050,000 / 1,000,000) × 100
                             = 105.00
                
            Therefore, the GDP Deflator is 105.00, indicating a 5.00% increase in prices compared to the base year.
        """.trimIndent()
        description.text = desc

        return rootView
    }

    private fun setupStep(step : Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_nominal_gdp)
                stepInputLayout.hint = getString(R.string.nominal_gdp)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_real_gdp)
                stepInputLayout.hint = getString(R.string.real_gdp)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_calculate_gdp_deflator)
                stepInputLayout.hint =
                    getString(R.string.step_3_calculate_gdp_deflator_hint, nominalGDP, realGDP)
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_result)
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
                    nominalGDP = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    realGDP = input
                    currentStep = 3
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    val expectedGDPDeflator = (nominalGDP!! / realGDP!!) * 100
                    if (input == expectedGDPDeflator) {
                        gdpDeflator = input
                        currentStep = 4
                        setupStep(currentStep)
                    } else {
                        showGDPDeflatorCalculationErrorToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }
        }
    }

    private fun displayResult() {
        resultTextView.text = getString(R.string.gdp_deflator_result, gdpDeflator!!)
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showGDPDeflatorCalculationErrorToast() {
        Toast.makeText(
            requireContext(),
            R.string.incorrect_gdp_deflator_calculation,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        (activity).enableRecyclerView()
        super.onDestroy()
    }
}
