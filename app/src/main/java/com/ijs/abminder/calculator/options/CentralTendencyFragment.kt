package com.ijs.abminder.calculator.options

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
import com.ijs.abminder.calculator.CalculatorOptionsActivity

class CentralTendencyFragment : Fragment() {

    private lateinit var dataSetEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var descriptionTextView : TextView
    private lateinit var explanationButton : Button

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_central_tendency,
            container,
            false
        )

        // Initialize UI components
        dataSetEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextData)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateCentralTendency)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewCentralTendencyResult)
        descriptionTextView = view.findViewById(com.calculator.calculatoroptions.R.id.description)
        explanationButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonExplanation)

        calculateButton.setOnClickListener {
            calculateMeasuresOfCentralTendency()
        }

        explanationButton.setOnClickListener {
            showExplanationDialog()
        }

        val description = """
            The Measures of Central Tendency Calculator allows you to compute statistical measures that represent the central tendency of a dataset.
            
            Simply input your dataset separated by commas, and the calculator will provide you with the mean, median, and mode.
            
            For example, given the dataset: 10, 15, 20, 20, 25
            - Mean: (10 + 15 + 20 + 20 + 25) / 5 = 18
            - Median: Since there are an odd number of values, the median is the middle value, which is 20.
            - Mode: The mode is 20 since it appears most frequently. 
            """.trimIndent()


        descriptionTextView.text = description

        return view
    }

    private fun calculateMeasuresOfCentralTendency() {
        val dataSetStr = dataSetEditText.text.toString()
        val dataSet = dataSetStr.split(",").map { it.trim().toDoubleOrNull()!!.toDouble() }

        // Check if the data set is valid
        if (dataSet.any { false }) {
            // Invalid data set
            showErrorMessage()
            return
        }

        // Calculate measures of central tendency
        val mean = dataSet.average()
        val median = calculateMedian(dataSet.sorted())
        val mode = calculateMode(dataSet)

        val result = "Mean: $mean\nMedian: $median\nMode: $mode"

        resultTextView.text = result
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.ijs.abminder.R.string.calculator)
        super.onDestroy()
    }

    private fun calculateMedian(sortedData : List<Double>) : Double {
        val size = sortedData.size
        return if (size % 2 == 0) {
            (sortedData[size / 2 - 1] + sortedData[size / 2]) / 2.0
        } else {
            sortedData[size / 2]
        }
    }

    // Calculate the mode of the data set
    private fun calculateMode(dataSet: List<Double>): String {
        val frequencyMap = mutableMapOf<Double, Int>()
        dataSet.forEach { value ->
            frequencyMap[value] = frequencyMap.getOrDefault(value, 0) + 1
        }

        val maxFrequency = frequencyMap.maxByOrNull { it.value }?.value ?: return ""
        val modes = frequencyMap.filter { it.value == maxFrequency }.keys.toList()
        return modes.toString().removeSurrounding("[", "]")
    }


    // Show an error message
    private fun showErrorMessage() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error")
            .setMessage("Invalid data set. Please enter numeric values separated by commas.")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showExplanationDialog() {
        val dataSetStr = dataSetEditText.text.toString()
        val dataSet = dataSetStr.split(",").map { it.trim().toDoubleOrNull() ?: 0.0 }

        val mean = dataSet.average()
        val sortedData = dataSet.sorted()
        val median = calculateMedian(sortedData)
        val mode = calculateMode(dataSet)

        val explanation = """
        Measures of Central Tendency are statistical measures that describe the center of a data set. 
        - Mean: The average of all values in the data set.
        - Median: The middle value when the data set is sorted in ascending order.
        - Mode: The value that appears most frequently in the data set.
        
        Step-by-Step Calculation:
        - Mean: Add up all values and divide by the total count.
            For the dataset $dataSetStr
            Mean = (${dataSet.joinToString(" + ")}) / ${dataSet.size}
                 = ${dataSet.joinToString(" + ")} / ${dataSet.size}
                 = $mean
        - Median: Sort the data, then find the middle value(s).
            For the sorted dataset (${sortedData.joinToString(", ")})
            Median = ${if (dataSet.size % 2 == 0) {
            "(${sortedData[dataSet.size / 2 - 1]} + ${sortedData[dataSet.size / 2]}) / 2"
        } else {
            sortedData[dataSet.size / 2].toString()
        }}
                 = $median
        - Mode: Count the frequency of each value and identify the most frequent one(s).
            For the dataset $dataSetStr
            Mode = $mode
    """.trimIndent()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Explanation - Measures of Central Tendency")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }

}
