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

class TradeDiscountCalculatorFragment : Fragment() {

    private lateinit var listPriceEditText : TextInputEditText
    private lateinit var discount1EditText : TextInputEditText
    private lateinit var discount2EditText : TextInputEditText
    private lateinit var discount3EditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var descriptionTextView : TextView

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

        calculateButton.setOnClickListener {
            calculateTradeDiscount()
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
        showExplanationDialog(listPrice, discount1, discount2, discount3, netPrice)
    }

    private fun showExplanationDialog(
        listPrice : Double,
        discount1 : Double,
        discount2 : Double,
        discount3 : Double,
        netPrice : Double,
    ) {
        val explanation = """
            Given:
                List Price = $listPrice
                Discount Rate 1 = $discount1%
                Discount Rate 2 = $discount2%
                Discount Rate 3 = $discount3%
            
            Solution:
                Net Price = List Price * (1 - Discount Rate 1/100) * (1 - Discount Rate 2/100) * (1 - Discount Rate 3/100)
                          = $listPrice * (1 - $discount1/100) * (1 - $discount2/100) * (1 - $discount3/100)
                          = $netPrice
            
            Therefore, the Net Price after applying the trade discounts is $netPrice.
        """.trimIndent()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Trade Discount Calculation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
