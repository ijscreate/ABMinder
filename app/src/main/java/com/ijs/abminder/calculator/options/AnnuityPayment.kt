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
import com.google.android.material.textview.MaterialTextView
import com.ijs.abminder.R
import com.ijs.abminder.calculator.CalculatorOptionsActivity

class AnnuityFragment : Fragment() {

    private lateinit var stepTextView : TextView
    private lateinit var stepInputLayout : TextInputLayout
    private lateinit var stepInputEditText : TextInputEditText
    private lateinit var nextButton : Button
    private lateinit var resultTextView : TextView

    private var payment : Double? = null
    private var rate : Double? = null
    private var time : Double? = null
    private var multiplication : Double? = null
    private var division : Double? = null
    private var currentStep = 1

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val rootView = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_annuity_payment,
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

        val annuityDescription = """
            An annuity is a series of equal payments made at regular intervals. It is a financial product that is used to provide a steady income stream, such as a pension. The present value of an annuity depends on the payment amount, the interest rate, and the number of payments. The formula for calculating the present value of an annuity is:
        
            PV = PMT x [(1 - (1 + r)^-n) / r]
        
            Where:
            - PV is the present value of the annuity
            - PMT is the payment amount
            - r is the interest rate per period
            - n is the number of periods
        
            For example, let's say you want to calculate the present value of an annuity with the following parameters:
            - Payment amount: PHP 100
            - Interest rate: 5% per year
            - Number of payments: 10 years
        
            Using the formula, we can calculate the present value as follows:
        
            PV = PHP 100 x [(1 - (1 + 0.05)^-10) / 0.05]
            PV = PHP 100 x [(1 - 0.6139) / 0.05]
            PV = PHP 100 x [0.4762]
            PV = PHP 47.62
        
            Therefore, the present value of the annuity is PHP 47.62.
        """.trimIndent()
        view?.findViewById<MaterialTextView>(R.id.descriptionTextView)?.text = annuityDescription

        return rootView
    }

    private fun setupStep(step : Int) {

        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.step_1_input_payment)
                stepInputLayout.hint = getString(R.string.payment)
                stepInputEditText.setText("")
            }

            2 -> {
                stepTextView.text = getString(R.string.step_2_input_rate)
                stepInputLayout.hint = getString(R.string.rate)
                stepInputEditText.setText("")
            }

            3 -> {
                stepTextView.text = getString(R.string.step_3_input_time)
                stepInputLayout.hint = getString(R.string.time_in_years)
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.step_4_multiply)
                stepInputLayout.hint = getString(R.string.step_4_multiply_hints, payment, rate)
                stepInputEditText.setText("")
            }

            5 -> {
                stepTextView.text = getString(R.string.step_5_multiply)
                stepInputLayout.hint =
                    getString(R.string.step_5_multiply_hint, multiplication, time)
                stepInputEditText.setText("")
            }

            6 -> {
                stepTextView.text = getString(R.string.step_6_divide)
                stepInputLayout.hint = getString(R.string.step_6_divide_hint, division)
                stepInputEditText.setText("")
            }

            7 -> {
                stepTextView.text = getString(R.string.step_7_result)
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
                    payment = input
                    currentStep = 2
                } else {
                    showInputErrorToast()
                }
            }

            2 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    rate = input
                    currentStep = 3
                } else {
                    showInputErrorToast()
                }
            }

            3 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    time = input
                    currentStep = 4
                } else {
                    showInputErrorToast()
                }
            }

            4 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    val expectedMultiplication = payment!! * rate!!
                    if (input == expectedMultiplication) {
                        multiplication = input
                        currentStep = 5
                    } else {
                        showMultiplicationErrorToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }

            5 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    val expectedMultiplication = multiplication!! * time!!
                    if (input == expectedMultiplication) {
                        division = input
                        currentStep = 6
                    } else {
                        showMultiplicationErrorToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }

            6 -> {
                val input = stepInputEditText.text.toString().toDoubleOrNull()
                if (input != null) {
                    val expectedDivision = division!! / 100.0
                    if (input == expectedDivision) {
                        division = input
                        currentStep = 7
                    } else {
                        showDivisionErrorToast()
                    }
                } else {
                    showInputErrorToast()
                }
            }
        }
        setupStep(currentStep)
    }

    private fun displayResult() {
        resultTextView.text = getString(R.string.annuity_result, division)
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun showMultiplicationErrorToast() {
        Toast.makeText(requireContext(), R.string.incorrect_multiplication, Toast.LENGTH_SHORT)
            .show()
    }

    private fun showDivisionErrorToast() {
        Toast.makeText(requireContext(), R.string.incorrect_division, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        (activity).enableRecyclerView()
        super.onDestroy()
    }
}