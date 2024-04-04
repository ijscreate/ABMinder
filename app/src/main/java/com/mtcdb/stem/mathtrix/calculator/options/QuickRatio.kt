package com.mtcdb.stem.mathtrix.calculator.options

import android.annotation.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.button.*
import com.google.android.material.dialog.*
import com.google.android.material.textfield.*
import com.google.android.material.textview.*
import com.mtcdb.stem.mathtrix.*

class QuickRatioFragment : Fragment() {

    private lateinit var currentAssetsEditText : TextInputEditText
    private lateinit var currentLiabilitiesEditText : TextInputEditText
    private lateinit var inventoryEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var descriptionTextView : TextView
    private lateinit var explanationButton : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_quick_ratio,
            container,
            false
        )

        // Initialize UI components
        currentAssetsEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextCurrentAssets)
        currentLiabilitiesEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextCurrentLiabilities)
        inventoryEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextInventory)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateQuickRatio)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewQuickRatioResult)
        descriptionTextView = view.findViewById(R.id.description)
        explanationButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonExplanation)

        calculateButton.setOnClickListener {
            calculateQuickRatio()
        }

        explanationButton.setOnClickListener {
            showExplanationDialog()
        }

        return view
    }

    private fun calculateQuickRatio() {
        val currentAssets = currentAssetsEditText.text.toString().toDoubleOrNull() ?: 0.0
        val currentLiabilities =
            currentLiabilitiesEditText.text.toString().toDoubleOrNull() ?: 0.0
        val inventory = inventoryEditText.text.toString().toDoubleOrNull() ?: 0.0

        val quickRatio = (currentAssets - inventory) / currentLiabilities

        resultTextView.text =
            getString(com.calculator.calculatoroptions.R.string.quick_ratio_result, quickRatio)
    }

    private fun showExplanationDialog() {
        val currentAssets = currentAssetsEditText.text.toString().toDoubleOrNull() ?: 0.0
        val currentLiabilities = currentLiabilitiesEditText.text.toString().toDoubleOrNull() ?: 0.0
        val inventory = inventoryEditText.text.toString().toDoubleOrNull() ?: 0.0

        val quickRatio = (currentAssets - inventory) / currentLiabilities

        val explanation = """
        Given:
            Current Assets = $currentAssets
            Current Liabilities = $currentLiabilities
            Inventory = $inventory
            
        Formula:
            Quick Ratio = (Current Assets - Inventory) / Current Liabilities
            
        Solution:
            Quick Ratio = ($currentAssets - $inventory) / $currentLiabilities
                        = $quickRatio
            
        Therefore, the Quick Ratio is $quickRatio    
    """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Quick Ratio Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }

}
