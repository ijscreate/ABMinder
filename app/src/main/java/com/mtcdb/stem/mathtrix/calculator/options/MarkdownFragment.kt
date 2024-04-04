package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.button.*
import com.google.android.material.dialog.*
import com.google.android.material.textfield.*
import com.google.android.material.textview.*
import com.mtcdb.stem.mathtrix.*

class MarkdownCalculatorFragment : Fragment() {

    private lateinit var originalPriceEditText : TextInputEditText
    private lateinit var salePriceEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var descriptionTextView : TextView

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_markdown, container, false
        )

        // Initialize UI components
        originalPriceEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextOriginalPrice)
        salePriceEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextSalePrice)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateMarkdown)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewMarkdownResult)
        descriptionTextView = view.findViewById(R.id.description)

        calculateButton.setOnClickListener {
            calculateMarkdown()
        }

        val exp = """
            The Markdown Calculator helps to calculate the markdown or discount for a given original price and sale price.

            Example:
            Suppose you want to buy a product that is originally priced at ₱1000, but it is currently on sale for ₱800. Using the Markdown Calculator, you can determine the markdown or discount percentage applied to the original price to arrive at the sale price.</string>
        """.trimIndent()

        // Set description text
        descriptionTextView.text =
            exp

        return view
    }

    private fun calculateMarkdown() {
        val originalPrice = originalPriceEditText.text.toString().toDoubleOrNull() ?: 0.0
        val salePrice = salePriceEditText.text.toString().toDoubleOrNull() ?: 0.0

        val markdown = originalPrice - salePrice

        resultTextView.text =
            getString(com.calculator.calculatoroptions.R.string.markdown_result, markdown)

        showExplanationDialog(originalPrice, salePrice, markdown)
    }

    private fun showExplanationDialog(
        originalPrice : Double,
        salePrice : Double,
        markdown : Double,
    ) {
        val explanation = """
            To calculate the markdown or discount:
            
            Original Price = $originalPrice
            Sale Price = $salePrice
            
            Markdown = Original Price - Sale Price
                     = $originalPrice - $salePrice
                     = $markdown
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext()).setTitle("Markdown Calculator Explanation")
            .setMessage(explanation).setPositiveButton("OK", null).show()
    }
}
