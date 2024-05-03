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
import kotlin.math.floor
import kotlin.math.round

class CentralTendencyFragment : Fragment() {

    private lateinit var stepTextView : TextView
    private lateinit var stepInputLayout : TextInputLayout
    private lateinit var stepInputEditText : TextInputEditText
    private lateinit var nextButton : Button
    private lateinit var resultTextView : TextView

    private var currentStep = 1
    private var numbers : MutableList<Double> = mutableListOf()
    private var mean : Double? = null
    private var median : Double? = null
    private var mode : Double? = null

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_central_tendency,
            container,
            false
        )

        stepTextView = view.findViewById(R.id.stepTextView)
        stepInputLayout = view.findViewById(R.id.stepInputLayout)
        stepInputEditText = view.findViewById(R.id.stepInputEditText)
        nextButton = view.findViewById(R.id.nextButton)
        resultTextView = view.findViewById(R.id.resultTextView)

        setupStep(currentStep)

        nextButton.setOnClickListener {
            handleNextStep()
        }

        val description = view.findViewById<TextView>(R.id.descriptionTextView)
        val centralTendencyDescription = """
            The Central Tendency calculator is a guide to help you compute the mean, median, and mode of a set of numbers.

            The mean is the average of the numbers, the median is the middle value when the numbers are sorted, and the mode is the number that appears most often.

            Please input the numbers separated by commas, and click the "Next" button to proceed with the calculation steps.

            Example:
            
            Suppose you have the following set of numbers: 5, 7, 3, 9, 5, 7, 3
            
            To begin, enter these numbers separated by commas.
        """.trimIndent()
        description.text = centralTendencyDescription

        return view
    }

    private fun setupStep(step : Int) {
        when (step) {
            1 -> {
                stepTextView.text = getString(R.string.input_numbers_hint)
                stepInputLayout.hint = ""
                stepInputEditText.setText("")
                nextButton.text = getString(R.string.next)
            }

            2 -> {
                stepTextView.text = getString(R.string.calculate_mean_hint)
                nextButton.text = getString(R.string.calculate)
                stepInputLayout.hint = numbers.toString()
                stepInputEditText.setText("")

            }

            3 -> {
                stepTextView.text = getString(R.string.calculate_median_hint)
                nextButton.text = getString(R.string.calculate)
                stepInputLayout.hint = numbers.toString()
                stepInputEditText.setText("")
            }

            4 -> {
                stepTextView.text = getString(R.string.calculate_mode_hint)
                nextButton.text = getString(R.string.calculate)
                stepInputLayout.hint = numbers.toString()
                stepInputEditText.setText("")
            }

            5 -> {
                stepTextView.text = getString(R.string.step_5_result)
                stepInputLayout.visibility = View.GONE
                nextButton.text = getString(R.string.exit)
                stepInputEditText.setText("")
                displayResult()
            }
        }
    }

    private fun handleNextStep() {
        when (currentStep) {
            1 -> {
                val input = stepInputEditText.text.toString().trim()
                val numbersArray = input.split(",").map { it.trim().toDoubleOrNull() }
                if (numbersArray.all { it != null }) {
                    numbers.addAll(numbersArray.filterNotNull())
                    stepInputEditText.setText("")
                    currentStep++
                    setupStep(currentStep)
                } else {
                    showInputErrorToast()
                }
            }
            2 -> {
                if (mean != null) {
                    handleCalculateMean()
                } else {
                    showInputErrorToast()
                }
            }
            3 -> {
                if (median != null) {
                    handleCalculateMedian()
                } else {
                    showInputErrorToast()
                }
            }
            4 -> {
                if (mode != null) {
                    handleCalculateMode()
                } else {
                    showInputErrorToast()
                }
            }
            5 -> {
                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            }
        }
    }



    private fun handleCalculateMean() {
        if (numbers.isNotEmpty()) {
            val sum = numbers.sum()
            mean = sum / numbers.size
            currentStep++
            setupStep(currentStep)
        } else {
            showInputErrorToast()
        }
    }

    private fun handleCalculateMedian() {
        if (numbers.isNotEmpty()) {
            val sortedNumbers = numbers.sorted()
            val middleIndex = sortedNumbers.size / 2
            median = if (sortedNumbers.size % 2 == 0) {
                (sortedNumbers[middleIndex - 1] + sortedNumbers[middleIndex]) / 2
            } else {
                sortedNumbers[middleIndex]
            }
            currentStep++
            setupStep(currentStep)
        } else {
            showInputErrorToast()
        }
    }

    private fun handleCalculateMode() {
        if (numbers.isNotEmpty()) {
            val numCount = numbers.groupingBy { it }.eachCount()
            mode = numCount.maxByOrNull { it.value }?.key
            currentStep++
            setupStep(currentStep)
        } else {
            showInputErrorToast()
        }
    }

    private fun showInputErrorToast() {
        Toast.makeText(requireContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }

    private fun displayResult() {
        val meanValue = mean?.let { round(it * 100.0) / 100.0 }
        val medianValue = median?.let { floor(it * 100.0) / 100.0 }
        val modeValue = mode?.let { floor(it * 100.0) / 100.0 }

        val resultText = StringBuilder()
        resultText.append(getString(R.string.central_tendency_mean, meanValue ?: 0.0))
        resultText.append("\n")
        resultText.append(getString(R.string.central_tendency_median, medianValue ?: 0.0))
        resultText.append("\n")
        resultText.append(getString(R.string.central_tendency_mode, modeValue ?: 0.0))

        resultTextView.text = resultText.toString()
    }

}
