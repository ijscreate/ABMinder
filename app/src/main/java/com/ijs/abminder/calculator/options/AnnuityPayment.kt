package com.ijs.abminder.calculator.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.ijs.abminder.R
import kotlin.math.pow

class AnnuityPaymentCalculatorFragment : Fragment() {

    private lateinit var principalEditText : TextInputEditText
    private lateinit var interestRateEditText : TextInputEditText
    private lateinit var timePeriodEditText : TextInputEditText
    private lateinit var paymentFrequencySpinner : Spinner
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var description : MaterialTextView
    private lateinit var buttonSolution : Button

    private var currentStep = 1

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_annuity_payment,
            container,
            false
        )

        // Initialize UI components
        //principalEditText = view.findViewById(com.calculator.calculatoroptions.R.id.editTextPrincipal)
        //interestRateEditText = view.findViewById(com.calculator.calculatoroptions.R.id.editTextInterestRate)
        //timePeriodEditText = view.findViewById(com.calculator.calculatoroptions.R.id.editTextTimePeriod)
        //paymentFrequencySpinner = view.findViewById(R.id.spinnerPaymentFrequency)
        //calculateButton = view.findViewById(R.id.buttonCalculateAnnuityPayme)
        //resultTextView = view.findViewById(R.id.textViewAnnuityPaymentResult)
        // description = view.findViewById(R.id.description)
        // buttonSolution = view.findViewById(R.id.buttonSolution)

        setupStep(currentStep)

        calculateButton.setOnClickListener {
            handleNextStep()
        }

        return view
    }

    private fun setupStep(step : Int) {
        when (step) {
            1 -> {
                description.text = getString(R.string.step_1_description)
            }

            2 -> {
                description.text = getString(R.string.step_2_description)
            }

            3 -> {
                description.text = getString(R.string.step_3_description)
            }

            4 -> {
                description.text = getString(R.string.step_4_description)
            }

            5 -> {
                description.text = getString(R.string.step_5_description)
                calculateAnnuityPayment()
            }
        }
    }

    private fun handleNextStep() {
        when (currentStep) {
            1 -> {
                val principal = principalEditText.text.toString().toDoubleOrNull()
                if (principal != null && principal > 0) {
                    currentStep++
                    setupStep(currentStep)
                } else {
                    showErrorToast()
                }
            }

            2 -> {
                val interestRate = interestRateEditText.text.toString().toDoubleOrNull()
                if (interestRate != null && interestRate > 0) {
                    currentStep++
                    setupStep(currentStep)
                } else {
                    showErrorToast()
                }
            }

            3 -> {
                val timePeriod = timePeriodEditText.text.toString().toIntOrNull()
                if (timePeriod != null && timePeriod > 0) {
                    currentStep++
                    setupStep(currentStep)
                } else {
                    showErrorToast()
                }
            }

            4 -> {
                currentStep++
                setupStep(currentStep)
            }
        }
    }

    private fun calculateAnnuityPayment() {
        val principal = principalEditText.text.toString().toDouble()
        val interestRate = interestRateEditText.text.toString().toDouble() / 100
        val timePeriod = timePeriodEditText.text.toString().toInt()
        val paymentFrequency = when (paymentFrequencySpinner.selectedItem.toString()) {
            "Monthly" -> 12
            "Quarterly" -> 4
            "Semi-Annually" -> 2
            "Annually" -> 1
            else -> 12 // Default to monthly
        }

        val interestRatePerPeriod = interestRate / paymentFrequency
        val totalPeriods = timePeriod * paymentFrequency

        val annuityPayment =
            principal * (interestRatePerPeriod * (1 + interestRatePerPeriod).pow(totalPeriods)) /
                    ((1 + interestRatePerPeriod).pow(totalPeriods) - 1)

        resultTextView.text = getString(R.string.annuity_payment_result, annuityPayment)
    }

    private fun showErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }
}