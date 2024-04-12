package com.ijs.abminder.calculator.options

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.ijs.abminder.R
import com.ijs.abminder.calculator.CalculatorOptionsActivity

class DepreciationCalculatorFragment : Fragment() {

    private lateinit var costOfAssetEditText : TextInputEditText
    private lateinit var salvageValueEditText : TextInputEditText
    private lateinit var usefulLifeEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var descriptionTextView : TextView
    private lateinit var methodSpinner : Spinner
    private lateinit var solutionButton : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_depreciation, container, false
        )

        // Initialize UI components
        costOfAssetEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextCostOfAsset)
        salvageValueEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextSalvageValue)
        usefulLifeEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextUsefulLife)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateDepreciation)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewDepreciationResult)
        descriptionTextView = view.findViewById(R.id.description)
        solutionButton = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution)
        methodSpinner = view.findViewById(com.calculator.calculatoroptions.R.id.spinnerMethod)

        // Set up method spinner
        val methodList = listOf(
            "Straight-Line",
            "Declining Balance",
            "Sum-of-Years' Digits"
        )
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, methodList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        methodSpinner.adapter = adapter

        methodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent : AdapterView<*>?,
                view : View?,
                position : Int,
                id : Long,
            ) {
                // Handle method selection
            }

            override fun onNothingSelected(parent : AdapterView<*>?) {
                // Do nothing
            }
        }

        calculateButton.setOnClickListener {
            calculateDepreciation()
        }

        solutionButton.setOnClickListener {
            val costOfAsset = costOfAssetEditText.text.toString().toDoubleOrNull() ?: 0.0
            val salvageValue = salvageValueEditText.text.toString().toDoubleOrNull() ?: 0.0
            val usefulLife = usefulLifeEditText.text.toString().toIntOrNull() ?: 0
            val selectedMethod = methodSpinner.selectedItem.toString()

            val depreciationExpense = when (selectedMethod) {
                "Straight-Line" -> calculateStraightLineDepreciation(
                    costOfAsset,
                    salvageValue,
                    usefulLife
                )

                "Declining Balance" -> calculateDecliningBalanceDepreciation(
                    costOfAsset,
                    salvageValue,
                    usefulLife
                )

                "Sum-of-Years' Digits" -> calculateSumOfYearsDigitsDepreciation(
                    costOfAsset,
                    salvageValue,
                    usefulLife
                )

                else -> 0.0
            }

            resultTextView.text = getString(
                com.calculator.calculatoroptions.R.string.depreciation_result,
                depreciationExpense
            )

            showExplanationDialog(
                costOfAsset,
                salvageValue,
                usefulLife.toDouble(),
                depreciationExpense,
                selectedMethod
            )
        }

        val description = """
            The Depreciation Schedule Calculator estimates the depreciation of an asset over its useful life.

            The most commonly used method for calculating depreciation is the Straight-Line Method, which spreads the depreciation expense evenly over the useful life of the asset. The formula to calculate depreciation using the Straight-Line Method is:

            Depreciation Expense = (Initial Cost - Salvage Value) / Useful Life

            Where:
            - Initial Cost is the original cost of the asset.
            - Salvage Value is the estimated value of the asset at the end of its useful life.
            - Useful Life is the expected period over which the asset will be used.

            Let's consider an example to illustrate the calculation:

            Suppose you purchase a machine for ₱100,000 with an estimated salvage value of ₱10,000 after 5 years of use.

            Depreciation Expense = (₱100,000 - ₱10,000) / 5
                                 = ₱18,000 per year

            In this example, the depreciation schedule would show the annual depreciation expense for each year of the asset's useful life.
        """.trimIndent()

        // Set description text
        descriptionTextView.text = description

        return view
    }

    @SuppressLint("StringFormatMatches")
    private fun calculateDepreciation() {
        val costOfAsset = costOfAssetEditText.text.toString().toDoubleOrNull() ?: 0.0
        val salvageValue = salvageValueEditText.text.toString().toDoubleOrNull() ?: 0.0
        val usefulLife = usefulLifeEditText.text.toString().toIntOrNull() ?: 0
        val selectedMethod = methodSpinner.selectedItem.toString()

        val depreciationExpense = when (selectedMethod) {
            "Straight-Line" -> calculateStraightLineDepreciation(
                costOfAsset,
                salvageValue,
                usefulLife
            )

            "Declining Balance" -> calculateDecliningBalanceDepreciation(
                costOfAsset,
                salvageValue,
                usefulLife
            )

            "Sum-of-Years' Digits" -> calculateSumOfYearsDigitsDepreciation(
                costOfAsset,
                salvageValue,
                usefulLife
            )

            else -> 0.0
        }

        resultTextView.text = getString(
            com.calculator.calculatoroptions.R.string.depreciation_result,
            depreciationExpense
        )
    }

    private fun calculateStraightLineDepreciation(
        costOfAsset : Double,
        salvageValue : Double,
        usefulLife : Int,
    ) : Double {
        return (costOfAsset - salvageValue) / usefulLife
    }

    private fun calculateDecliningBalanceDepreciation(
        costOfAsset : Double,
        salvageValue : Double,
        usefulLife : Int,
    ) : Double {
        val rate = 2.0 / usefulLife.toDouble()
        var depreciationExpense = 0.0

        var bookValue = costOfAsset
        for (year in 1..usefulLife) {
            val depreciation = bookValue * rate
            bookValue -= depreciation
            if (bookValue < salvageValue) {
                depreciationExpense += (bookValue - salvageValue)
                break
            }
            depreciationExpense += depreciation
        }

        return depreciationExpense
    }

    private fun calculateSumOfYearsDigitsDepreciation(
        costOfAsset : Double,
        salvageValue : Double,
        usefulLife : Int,
    ) : Double {
        val depreciableValue = costOfAsset - salvageValue
        val sumOfYearsDigits = usefulLife * (usefulLife + 1) / 2.0

        var depreciationExpense = 0.0
        for (year in 1..usefulLife) {
            val depreciationFactor = year.toDouble() / sumOfYearsDigits
            val depreciation = depreciableValue * depreciationFactor
            depreciationExpense += depreciation
        }

        return depreciationExpense
    }

    private fun showExplanationDialog(
        costOfAsset : Double,
        salvageValue : Double,
        usefulLife : Double,
        depreciationExpense : Double,
        selectedMethod : String,
    ) {
        val explanation = buildString {
            appendLine("To calculate depreciation expense using the $selectedMethod method:")
            appendLine("Cost of Asset = $costOfAsset")
            appendLine("Salvage Value = $salvageValue")
            appendLine("Useful Life = $usefulLife years")

            when (selectedMethod) {
                "Straight-Line" -> {
                    appendLine("Depreciation Expense = (Cost of Asset - Salvage Value) / Useful Life")
                    appendLine("                     = ($costOfAsset - $salvageValue) / $usefulLife")
                    appendLine("                     = $depreciationExpense")
                }

                "Declining Balance" -> {
                    appendLine("Declining Balance Rate = 2 / Useful Life = 2 / $usefulLife = ${2.0 / usefulLife}")
                    appendLine("Depreciation Expense is calculated by applying the declining balance rate to the book value each year until the book value reaches the salvage value.")
                }

                "Sum-of-Years' Digits" -> {
                    appendLine("Sum of Years' Digits = n(n+1)/2 = $usefulLife($usefulLife+1)/2 = ${usefulLife * (usefulLife + 1) / 2.0}")
                    appendLine("Depreciation Expense is calculated by applying a depreciation factor (year/Sum of Years' Digits) to the depreciable value (Cost of Asset - Salvage Value) for each year.")
                }
            }

            appendLine("Therefore, the depreciation expense is $depreciationExpense.")
        }

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Depreciation Expense Calculation Explanation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }
}