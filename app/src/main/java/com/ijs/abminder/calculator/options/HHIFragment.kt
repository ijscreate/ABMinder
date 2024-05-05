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
import kotlin.math.pow

class HHIFragment : Fragment() {

    private lateinit var stepTextView: TextView
    private lateinit var stepInputLayout: TextInputLayout
    private lateinit var stepInputEditText: TextInputEditText
    private lateinit var nextButton: Button
    private lateinit var resultTextView: TextView

    private var currentStep = 1
    private lateinit var marketShares: MutableList<Double>
    private var hhi: Double? = null
    private var numFirms: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(com.calculator.calculatoroptions.R.layout.fragment_hhi, container, false)

        stepTextView = rootView.findViewById(R.id.stepTextView)
        stepInputLayout = rootView.findViewById(R.id.stepInputLayout)
        stepInputEditText = rootView.findViewById(R.id.stepInputEditText)
        nextButton = rootView.findViewById(R.id.nextButton)
        resultTextView = rootView.findViewById(R.id.resultTextView)

        marketShares = mutableListOf()

        setupStep(currentStep)

        nextButton.setOnClickListener {
            handleNextStep()
        }

        val description = rootView.findViewById<TextView>(R.id.descriptionTextView)
        val desc = """
            Calculate the Herfindahl-Hirschman Index (HHI) using the formula:
            
            HHI = Σ (s_i^2)
            
            where s_i is the market share of the i-th firm.
            
            This calculator helps you determine the HHI of a market based on the market shares of the firms.
            
            Example:
            
                If there are 3 firms in a market with the following market shares:
                Firm A: 40%
                Firm B: 35%
                Firm C: 25%
                
                The HHI would be calculated as:
                
                HHI = (0.40)^2 + (0.35)^2 + (0.25)^2
                    = 0.1600 + 0.1225 + 0.0625
                    = 0.3450
                
            Therefore, the HHI of the market is 0.3450, which indicates a moderately concentrated market.
        """.trimIndent()
        description.text = desc

        return rootView
    }

    private fun setupStep(step: Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_number_of_firms)
                stepInputLayout.hint = getString(R.string.number_of_firms)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_market_shares)
                stepInputLayout.hint = getString(R.string.market_share_of_firm, marketShares.size + 1)
                stepInputEditText.setText("")
            }

            3 -> {
                if (marketShares.size < numFirms) {
                    setupStep(2)
                    return
                }
                stepTextView.text = getString(R.string.step_3_calculate_hhi)
                stepInputLayout.hint = getString(R.string.step_3_calculate_hhi_hint, marketShares.joinToString(", "))
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
                val input = stepInputEditText.text.toString().toIntOrNull()
                if (input != null && input > 0) {
                    numFirms = input
                    marketShares = MutableList(numFirms) { 0.0 }
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null && input >= 0 && input <= 100) {
                    marketShares[marketShares.size - 1] = input / 100
                    if (marketShares.size < numFirms) {
                        currentStep = 2
                        setupStep(currentStep)
                    } else {
                        currentStep = 3
                        setupStep(currentStep)
                    }
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    val expectedHHI = marketShares.sumOf { it.pow(2) }
                    if (input == expectedHHI) {
                        hhi = input
                        currentStep = 4
                        setupStep(currentStep)
                    } else {
                        showHHICalculationErrorToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }
        }
    }

    private fun displayResult() {
        resultTextView.text = getString(R.string.hhi_result, hhi!!)
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showHHICalculationErrorToast() {
        Toast.makeText(requireContext(), R.string.incorrect_hhi_calculation, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        (activity).enableRecyclerView()
        super.onDestroy()
    }
}
