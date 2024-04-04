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

class WACCFragment : Fragment() {

    private lateinit var costOfDebtEditText : TextInputEditText
    private lateinit var costOfEquityEditText : TextInputEditText
    private lateinit var debtWeightEditText : TextInputEditText
    private lateinit var equityWeightEditText : TextInputEditText
    private lateinit var taxRateEditText : TextInputEditText
    private lateinit var calculateButton : MaterialButton
    private lateinit var resultTextView : MaterialTextView
    private lateinit var tVDescription : TextView
    private lateinit var buttonSolution : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_wacc,
            container,
            false
        )

        // Initialize UI components
        costOfDebtEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextCostOfDebt)
        costOfEquityEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextCostOfEquity)
        debtWeightEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextDebtWeight)
        equityWeightEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextEquityWeight)
        taxRateEditText = view.findViewById(com.calculator.calculatoroptions.R.id.editTextTaxRate)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateWACC)
        resultTextView = view.findViewById(com.calculator.calculatoroptions.R.id.textViewWACCResult)
        tVDescription = view.findViewById(com.calculator.calculatoroptions.R.id.tVDescription)
        buttonSolution = view.findViewById(com.calculator.calculatoroptions.R.id.buttonSolution)

        calculateButton.setOnClickListener {
            calculateWeightedAverageCostOfCapital()
        }

        buttonSolution.setOnClickListener {

            val costOfDebt = costOfDebtEditText.text.toString().toDoubleOrNull() ?: 0.0
            val costOfEquity = costOfEquityEditText.text.toString().toDoubleOrNull() ?: 0.0
            val taxRate = taxRateEditText.text.toString().toDoubleOrNull() ?: 0.0
            val debtWeight = debtWeightEditText.text.toString().toDoubleOrNull() ?: 0.0
            val equityWeight = equityWeightEditText.text.toString().toDoubleOrNull() ?: 0.0
            val afterTaxCostOfDebt = costOfDebt * (1 - taxRate)
            val v = debtWeight + equityWeight
            val wacc = (debtWeight / v * afterTaxCostOfDebt) + (equityWeight / v * costOfEquity)

            showExplanationDialog(
                costOfDebt,
                costOfEquity,
                taxRate,
                v,
                debtWeight,
                equityWeight,
                wacc
            )
        }

        val description = """
            WACC Formula
                The calculator uses the following basic formula to calculate the weighted average cost of capital:
    
                WACC = (E / V) × Re + (D / V) × Rd × (1 − Tc)

            Where:
                WACC  is the weighted average cost of capital,
                Re  is the cost of equity,
                Rd  is the cost of debt,
                E  is the market value of the company's equity,
                D  is the market value of the company's debt,   
                V = E + D  is the total market value of the company's financing (equity and debt),  
                E/V  is the percentage of equity financing, 
                D/V  is the percentage of debt financing,  
                Tc  is the corporate tax rate.

            Example: Suppose we have the following information about a firm:

                Debt (D) = $5,000
                Equity (E) = $15,000
                Rd = 8%
                Re = 13.5%
                Corporate Tax Rate (Tc) = 20%
                
                In this example, the WACC would be calculated as follows:

                WACC = (E / V) × Re + (D / V) × Rd × (1 − Tc)
                
                WACC = [(15000 / 15000 + 5000) × 0.135] + [(5000 / 15000 + 5000) × 0.08 × (1 − 0.2)]
                
                WACC = 0.10125 + 0.016 = 0.11725 or 11.725%, the WACC for this firm is 11.725%

        """.trimIndent()
        tVDescription.text = description

        return view
    }

    override fun onDestroy() {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }

    private fun calculateWeightedAverageCostOfCapital() {
        val costOfDebt = costOfDebtEditText.text.toString().toDoubleOrNull() ?: 0.0
        val costOfEquity = costOfEquityEditText.text.toString().toDoubleOrNull() ?: 0.0
        val taxRate = taxRateEditText.text.toString().toDoubleOrNull() ?: 0.0
        val debtWeight = debtWeightEditText.text.toString().toDoubleOrNull() ?: 0.0
        val equityWeight = equityWeightEditText.text.toString().toDoubleOrNull() ?: 0.0

        val afterTaxCostOfDebt = costOfDebt * (1 - taxRate)
        val v = debtWeight + equityWeight
        val wacc = (debtWeight / v * afterTaxCostOfDebt) + (equityWeight / v * costOfEquity)

        resultTextView.text = wacc.toString()
    }

    private fun showExplanationDialog(
        costOfDebt : Double,
        costOfEquity : Double,
        taxRate : Double,
        v : Double,
        debtWeight : Double,
        equityWeight : Double,
        wacc : Double,
    ) {
        val explanation = """
            
            Given:
                Cost of Debt = $costOfDebt
                Cost of Equity = $costOfEquity
                Tax Rate = $taxRate
                V = $debtWeight + $equityWeight
                Debt Weight = $debtWeight
                Equity Weight = $equityWeight
                
            Formula:
                After-Tax Cost of Debt = Rd * (1 - Tc)
                WACC = (D / V * After-Tax Cost of Debt) + (E / V * Re)
                
            Solution:
                After-Tax Cost of Debt = $costOfDebt * (1 - $taxRate) = ${costOfDebt * (1 - taxRate)}
                WACC = ($debtWeight / $v * $costOfDebt * (1 - $taxRate)) + ($equityWeight / $v * $costOfEquity)
                WACC = $wacc
                
            Therefore, the Weighted Average Cost of Capital is $wacc%.
        """.trimIndent()

        // Display explanation in a custom dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Weighted Average Cost of Capital (WACC)")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
