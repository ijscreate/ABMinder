package com.ijs.abminder.calculator.options

import android.annotation.SuppressLint
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

class TradeDiscountCalculatorFragment : Fragment() {

    private lateinit var listPriceEditText : TextInputEditText
    private lateinit var discount1EditText : TextInputEditText
    private lateinit var discount2EditText : TextInputEditText
    private lateinit var discount3EditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var descriptionTextView : TextView
    private lateinit var buttonSolution : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_trade_discount,
            container,
            false
        )

        // Initialize UI components
        listPriceEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextListPrice)
        discount1EditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextDiscount1)
        discount2EditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextDiscount2)
        discount3EditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextDiscount3)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateTradeDiscount)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewTradeDiscountResult)
        descriptionTextView = view.findViewById(com.calculator.calculatoroptions.R.id.description)
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution)

        calculateButton.setOnClickListener {
            calculateTradeDiscount()
        }

        buttonSolution.setOnClickListener {
            val listPrice = listPriceEditText.text.toString().toDoubleOrNull() ?: 0.0
            val discount1 = discount1EditText.text.toString().toDoubleOrNull() ?: 0.0
            val discount2 = discount2EditText.text.toString().toDoubleOrNull() ?: 0.0
            val discount3 = discount3EditText.text.toString().toDoubleOrNull() ?: 0.0

            val netPrice =
                listPrice * (1 - (discount1 / 100)) * (1 - (discount2 / 100)) * (1 - (discount3 / 100))

            resultTextView.text =
                getString(
                    com.calculator.calculatoroptions.R.string.trade_discount_result,
                    netPrice
                )
            showExplanationDialog(listPrice, discount1, discount2, discount3, netPrice)
        }

        val description = """
            The Trade Discount Calculator allows you to quickly determine the final price of a product after applying trade discounts.
            
            Simply enter the List Price of the product and the discount rates to calculate the discounted price.
            
            For example, if the List Price is ₱100 and there are trade discounts of 10% and 5% applied successively:
            - Discount 1: 10% of ₱100 = ₱10 discount
            - Discounted Price after Discount 1 = ₱100 - ₱10 = ₱90
            - Discount 2: 5% of ₱90 = ₱4.50 discount
            - Final Discounted Price after Discount 2 = ₱90 - ₱4.50 = ₱85.50
            
            So, the final price after applying both discounts is ₱85.50."""
            .trimIndent()


        descriptionTextView.text = description

        return view
    }

    private fun calculateTradeDiscount() {
        val listPrice = listPriceEditText.text.toString().toDoubleOrNull() ?: 0.0
        val discount1 = discount1EditText.text.toString().toDoubleOrNull() ?: 0.0
        val discount2 = discount2EditText.text.toString().toDoubleOrNull() ?: 0.0
        val discount3 = discount3EditText.text.toString().toDoubleOrNull() ?: 0.0

        val netPrice =
            listPrice * (1 - (discount1 / 100)) * (1 - (discount2 / 100)) * (1 - (discount3 / 100))

        resultTextView.text =
            getString(
                com.calculator.calculatoroptions.R.string.trade_discount_result,
                netPrice
            )
    }

    private fun showExplanationDialog(
        listPrice: Double,
        discount1: Double,
        discount2: Double,
        discount3: Double,
        netPrice: Double,
    ) {
        // Explanation detailing the step-by-step calculation
        val explanation = """
        Given:
            List Price = ₱$listPrice
            Discount Rate 1 = $discount1%
            Discount Rate 2 = $discount2%
            Discount Rate 3 = $discount3%
        
        Solution:
            Step 1: Calculate the discounted price after the first discount
                Discounted Price 1 = List Price - (List Price * Discount Rate 1 / 100)
                                   = ₱$listPrice - (₱$listPrice * $discount1 / 100)
                                   = ₱${listPrice * (1 - discount1 / 100)}
            
            Step 2: Calculate the discounted price after the second discount
                Discounted Price 2 = Discounted Price 1 - (Discounted Price 1 * Discount Rate 2 / 100)
                                   = ₱${listPrice * (1 - discount1 / 100)} - (₱${listPrice * (1 - discount1 / 100)} * $discount2 / 100)
                                   = ₱${(listPrice * (1 - discount1 / 100)) * (1 - discount2 / 100)}
            
            Step 3: Calculate the net price after the third discount
                Net Price = Discounted Price 2 - (Discounted Price 2 * Discount Rate 3 / 100)
                          = ₱${(listPrice * (1 - discount1 / 100)) * (1 - discount2 / 100)} - (₱${(listPrice * (1 - discount1 / 100)) * (1 - discount2 / 100)} * $discount3 / 100)
                          = ₱${netPrice}
        
        Therefore, the Net Price after applying the trade discounts is ₱$netPrice.
    """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Trade Discount Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroy() {
        val activity = requireActivity() as CalculatorOptionsActivity
        activity.toolbar.title = getString(com.ijs.abminder.R.string.calculator)
        super.onDestroy()
    }
}
