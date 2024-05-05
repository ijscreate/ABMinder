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

class MarkdownFragment : Fragment() {

    private lateinit var stepTextView : TextView
    private lateinit var stepInputLayout : TextInputLayout
    private lateinit var stepInputEditText : TextInputEditText
    private lateinit var nextButton : Button
    private lateinit var resultTextView : TextView
    private lateinit var descriptionTextView : TextView

    private var currentStep = 1
    private var sellingPrice : Double? = null
    private var markdownPercent : Double? = null
    private var markdownAmount : Double? = null

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val rootView = inflater.inflate(com.calculator.calculatoroptions.R.layout.fragment_markdown, container, false)

        stepTextView = rootView.findViewById(R.id.stepTextView)
        stepInputLayout = rootView.findViewById(R.id.stepInputLayout)
        stepInputEditText = rootView.findViewById(R.id.stepInputEditText)
        nextButton = rootView.findViewById(R.id.nextButton)
        resultTextView = rootView.findViewById(R.id.resultTextView)
        descriptionTextView = rootView.findViewById(R.id.descriptionTextView)

        setupStep(currentStep)

        nextButton.setOnClickListener {
            handleNextStep()
        }

        val description = """
            The Markdown Calculator helps you determine the markdown amount or markdown percentage given the selling price.

            The formula for calculating the markdown amount is:

            Markdown Amount = Selling Price - Sale Price

            The formula for calculating the markdown percentage is:

            Markdown % = (Markdown Amount / Selling Price) * 100

            To use this calculator, follow these steps:
            1. Enter the selling price of the product.
            2. Enter the markdown amount or markdown percentage.
            3. View the final result.
        """.trimIndent()
        descriptionTextView.text = description

        return rootView
    }

    private fun setupStep(step : Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_selling_price)
                stepInputLayout.hint = getString(R.string.selling_price)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_sale_price)
                stepInputLayout.hint = getString(R.string.sale_price)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_calculate_markdown_amount)
                stepInputLayout.visibility = View.GONE
                displayMarkdownAmount()
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_calculate_markdown_percentage)
                stepInputLayout.visibility = View.GONE
                displayMarkdownPercentage()
            }

            5 -> {
                stepTextView.text = getString(R.string.step_5_result_m)
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
                    sellingPrice = input
                    currentStep = 2
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    if (sellingPrice!! > input) {
                        markdownAmount = sellingPrice!! - input
                        currentStep = 3
                        setupStep(currentStep)
                    } else {
                        showInvalidMarkdownInputToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                currentStep = 4
                setupStep(currentStep)
            }

            4 -> {
                currentStep = 5
                setupStep(currentStep)
            }
        }
    }

    private fun displayMarkdownAmount() {
        resultTextView.text = getString(R.string.markdown_amount, markdownAmount!!)
    }

    private fun displayMarkdownPercentage() {
        markdownPercent = (markdownAmount!! / sellingPrice!!) * 100
        resultTextView.text = getString(R.string.markdown_percentage, markdownPercent!!)
    }

    private fun displayResult() {
        resultTextView.text = getString(
            R.string.markdown_result, markdownAmount!!, markdownPercent!!
        )
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showInvalidMarkdownInputToast() {
        Toast.makeText(requireContext(), R.string.invalid_markdown_input, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        (activity).enableRecyclerView()
        super.onDestroy()
    }
}
