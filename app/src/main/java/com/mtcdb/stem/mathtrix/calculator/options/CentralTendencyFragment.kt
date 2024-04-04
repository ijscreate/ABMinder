package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.button.*
import com.google.android.material.dialog.*
import com.google.android.material.textfield.*
import com.google.android.material.textview.*

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

    private fun calculateMedian(sortedData : List<Double>) : Double {
        val size = sortedData.size
        return if (size % 2 == 0) {
            (sortedData[size / 2 - 1] + sortedData[size / 2]) / 2.0
        } else {
            sortedData[size / 2]
        }
    }

    // Calculate the mode of the data set
    private fun calculateMode(dataSet : List<Double>) : List<Double> {
        val frequencyMap = mutableMapOf<Double, Int>()
        dataSet.forEach { value ->
            frequencyMap[value] = frequencyMap.getOrDefault(value, 0) + 1
        }

        val maxFrequency = frequencyMap.maxByOrNull { it.value }?.value ?: return emptyList()
        return frequencyMap.filter { it.value == maxFrequency }.keys.toList()
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
        val explanation = """
            Measures of Central Tendency are statistical measures that describe the center of a data set. 
            - Mean: The average of all values in the data set.
            - Median: The middle value when the data set is sorted in ascending order.
            - Mode: The value that appears most frequently in the data set.
            
            These measures help summarize the data and provide insights into its distribution.
        """.trimIndent()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Explanation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
