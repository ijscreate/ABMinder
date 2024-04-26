package com.ijs.abminder.calculator

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ezylang.evalex.Expression
import com.ezylang.evalex.parser.ParseException
import com.google.android.material.navigation.NavigationView
import com.ijs.abminder.BaseDrawerActivity
import com.ijs.abminder.R
import com.ijs.abminder.calculator.adapter.CalculationOptionAdapter
import com.ijs.abminder.calculator.data.CalculationOption
import com.ijs.abminder.calculator.options.AmortizationFragment
import com.ijs.abminder.calculator.options.AnnuityPaymentCalculatorFragment
import com.ijs.abminder.calculator.options.AverageCollectionPeriodFragment
import com.ijs.abminder.calculator.options.AverageRateOfReturnFragment
import com.ijs.abminder.calculator.options.BreakEvenFragment
import com.ijs.abminder.calculator.options.COGSFragment
import com.ijs.abminder.calculator.options.CashConversionCycleFragment
import com.ijs.abminder.calculator.options.CentralTendencyFragment
import com.ijs.abminder.calculator.options.CommissionFragment
import com.ijs.abminder.calculator.options.CompoundInterestFragment
import com.ijs.abminder.calculator.options.DTERFragment
import com.ijs.abminder.calculator.options.DepreciationCalculatorFragment
import com.ijs.abminder.calculator.options.EPSFragment
import com.ijs.abminder.calculator.options.EquityMultiplierFragment
import com.ijs.abminder.calculator.options.FutureValueFragment
import com.ijs.abminder.calculator.options.GDPFragment
import com.ijs.abminder.calculator.options.HHIFragment
import com.ijs.abminder.calculator.options.InflationAdjustedReturnFragment
import com.ijs.abminder.calculator.options.InventoryTurnoverRatioFragment
import com.ijs.abminder.calculator.options.MarkdownCalculatorFragment
import com.ijs.abminder.calculator.options.MortgageCalculatorFragment
import com.ijs.abminder.calculator.options.NPVFragment
import com.ijs.abminder.calculator.options.OperatingCashFlowRatioFragment
import com.ijs.abminder.calculator.options.PaybackPeriodFragment
import com.ijs.abminder.calculator.options.PercentageChangeFragment
import com.ijs.abminder.calculator.options.PresentValueFragment
import com.ijs.abminder.calculator.options.ProfitMarginFragment
import com.ijs.abminder.calculator.options.QuickRatioFragment
import com.ijs.abminder.calculator.options.RAROCFragment
import com.ijs.abminder.calculator.options.ROAFragment
import com.ijs.abminder.calculator.options.ROIFragment
import com.ijs.abminder.calculator.options.RuleOf72Fragment
import com.ijs.abminder.calculator.options.SavingsGoalFragment
import com.ijs.abminder.calculator.options.SimpleInterestFragment
import com.ijs.abminder.calculator.options.TimeValueOfMoneyFragment
import com.ijs.abminder.calculator.options.TradeDiscountCalculatorFragment
import com.ijs.abminder.calculator.options.WACCFragment
import com.ijs.abminder.calculator.options.WorkingCapitalFragment
import java.text.DecimalFormat


class CalculatorOptionsActivity : BaseDrawerActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : CalculationOptionAdapter
    private lateinit var searchView : SearchView
    lateinit var toolbar : Toolbar
    private lateinit var calculatorFragmentLifecycleObserver : CalculatorFragmentLifecycleObserver
    private lateinit var textViewDisplay : TextView
    private lateinit var speechRecognitionDialog : Dialog
    private lateinit var linearLayout : LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        // Set up the toolbar and drawer
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupDrawer(toolbar)

        speechRecognitionDialog = Dialog(this)
        speechRecognitionDialog.setContentView(R.layout.dialog_speech_recognition)

        searchView = findViewById(R.id.calculatorSearchView)

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setCheckedItem(R.id.nav_item_calculator)

        linearLayout = findViewById(R.id.calculator_linear_layout)

        // Set up search view
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query : String?) : Boolean {
                return false
            }

            override fun onQueryTextChange(newText : String?) : Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        onBackPressedDispatcher.addCallback(this) { finish() }

        calculatorFragmentLifecycleObserver = CalculatorFragmentLifecycleObserver(toolbar)

        recyclerView = findViewById(R.id.recyclerViewCalculationOptions)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize calculation options
        val calculationOptions = listOf(
            CalculationOption(
                "Simple Interest",
                "Calculate the simple interest for a given principal amount, interest rate, and time period."
            ),
            CalculationOption(
                "Compound Interest",
                "Compute interest on the initial principal and accumulated interest."
            ),
            CalculationOption(
                "Percentage Change", "Determine the percentage change between two values."
            ),
            CalculationOption(
                "Profit Margin", "Calculate the profit margin as a percentage of revenue."
            ),
            CalculationOption(
                "Amortization Schedule",
                "Generate a schedule of loan repayments with details on principal and interest."
            ),
            CalculationOption(
                "Net Present Value (NPV)", "Evaluate the profitability of an investment."
            ),
            CalculationOption(
                "Return on Investment (ROI)", "Measure the return on an investment as a percentage."
            ),
            CalculationOption(
                "Break-Even Point", "Find the point where revenue equals costs."
            ),
            CalculationOption(
                "Weighted Average Cost of Capital (WACC)",
                "Calculate the average rate of return a company is expected to provide to its investors."
            ),
            CalculationOption(
                "Time Value of Money (TVM)", "Evaluate the value of money over time."
            ),
            CalculationOption(
                "Earnings Per Share (EPS)",
                "Measure a company's profitability per outstanding share of common stock."
            ),
            CalculationOption(
                "Gross Domestic Product (GDP) Growth Rate",
                "Determine the rate at which a country's economy is growing."
            ),
            CalculationOption(
                "Debt to Equity Ratio", "Assess a company's financial leverage."
            ),
            CalculationOption(
                "Operating Cash Flow Ratio",
                "Evaluate a company's ability to generate cash from its operations."
            ),
            CalculationOption(
                "Inventory Turnover Ratio",
                "Measure how many times a company's inventory is sold and replaced over a period."
            ),
            CalculationOption(
                "Return on Assets (ROA)",
                "Assess how efficiently a company uses its assets to generate earnings."
            ),
            CalculationOption(
                "Average Collection Period",
                "Calculate the average number of days it takes for a company to collect payments."
            ),
            CalculationOption(
                "Payback Period",
                "Determine the time it takes for an investment to generate cash equal to its initial cost."
            ),
            CalculationOption(
                "Risk-Adjusted Return on Capital (RAROC)",
                "Evaluate the return on an investment adjusted for its risk."
            ),
            CalculationOption(
                "Working Capital Ratio", "Assess a company's operational liquidity."
            ),
            CalculationOption(
                "Quick Ratio",
                "Measure a company's ability to meet its short-term obligations with its most liquid assets."
            ),
            CalculationOption(
                "Savings Goal Planner", "Plan and track progress toward a savings goal over time."
            ),
            CalculationOption(
                "Inflation Adjusted Return",
                "Calculate the real return on an investment after adjusting for inflation."
            ),
            CalculationOption(
                "Cost of Goods Sold (COGS)",
                "Calculate the direct costs of producing goods sold by a company."
            ),

            CalculationOption(
                "Equity Multiplier",
                "Assess the financial leverage of a company by measuring its equity multiplier."
            ),

            CalculationOption(
                "Future Value of an Investment",
                "Compute the future value of an investment based on different compounding periods."
            ),
            CalculationOption(
                "Present Value of Cash Flows",
                "Evaluate the current value of future cash flows, considering the time value of money."
            ),
            CalculationOption(
                "Average Rate of Return",
                "Calculate the average rate of return on an investment over a specified time period."
            ),
            CalculationOption(
                "Cash Conversion Cycle (CCC)",
                "Evaluate the time it takes for a company to convert its investments in inventory to cash."
            ),
            CalculationOption(
                "Commission",
                "Calculate the commission earned based on a sale amount and commission rate."
            ),
            CalculationOption(
                "Annuity Payment Calculator",
                "Calculate the periodic payment required to pay off a loan or mortgage with a fixed interest rate."
            ),
            CalculationOption(
                "Rule of 72",
                "Estimate the number of years required to double the value of an investment."
            ),
            CalculationOption(
                "Mortgage", "Estimate your monthly mortgage payment."
            ),
            CalculationOption(
                "Trade Discount", "Calculate the net price after applying trade discounts."
            ),
            CalculationOption(
                "Measures of Central Tendency",
                "Calculate the mean, median, and mode for a given set of data."
            ),
            CalculationOption(
                "Herfindahl-Hirschman Index (HHI)",
                "Calculate the HHI to determine market concentration and competition levels."
            ),
            CalculationOption(
                "Markdown",
                "Calculate the markdown or discount for a given original price and sale price."
            ),
            CalculationOption(
                "Depreciation Expense", "Calculate the depreciation of an asset."
            ),
        ).sortedBy { it.name }

        // Initialize the adapter
        adapter = CalculationOptionAdapter(calculationOptions) { selectedOption ->
            val calculatorFragment = when (selectedOption.name) {
                "Simple Interest" -> SimpleInterestFragment()
                "Compound Interest" -> CompoundInterestFragment()
                "Percentage Change" -> PercentageChangeFragment()
                "Profit Margin" -> ProfitMarginFragment()
                "Amortization Schedule" -> AmortizationFragment()
                "Net Present Value (NPV)" -> NPVFragment()
                "Return on Investment (ROI)" -> ROIFragment()
                "Break-Even Point" -> BreakEvenFragment()
                "Weighted Average Cost of Capital (WACC)" -> WACCFragment()
                "Time Value of Money (TVM)" -> TimeValueOfMoneyFragment()
                "Earnings Per Share (EPS)" -> EPSFragment()
                "Gross Domestic Product (GDP) Growth Rate" -> GDPFragment()
                "Debt to Equity Ratio" -> DTERFragment()
                "Operating Cash Flow Ratio" -> OperatingCashFlowRatioFragment()
                "Inventory Turnover Ratio" -> InventoryTurnoverRatioFragment()
                "Return on Assets (ROA)" -> ROAFragment()
                "Average Collection Period" -> AverageCollectionPeriodFragment()
                "Payback Period" -> PaybackPeriodFragment()
                "Risk-Adjusted Return on Capital (RAROC)" -> RAROCFragment()
                "Working Capital Ratio" -> WorkingCapitalFragment()
                "Savings Goal Planner" -> SavingsGoalFragment()
                "Inflation Adjusted Return" -> InflationAdjustedReturnFragment()
                "Annuity Payment Calculator" -> AnnuityPaymentCalculatorFragment()
                "Cost of Goods Sold (COGS)" -> COGSFragment()
                "Equity Multiplier" -> EquityMultiplierFragment()
                "Future Value of an Investment" -> FutureValueFragment()
                "Present Value of Cash Flows" -> PresentValueFragment()
                "Average Rate of Return" -> AverageRateOfReturnFragment()
                "Cash Conversion Cycle (CCC)" -> CashConversionCycleFragment()
                "Commission" -> CommissionFragment()
                "Rule of 72" -> RuleOf72Fragment()
                "Mortgage" -> MortgageCalculatorFragment()
                "Trade Discount" -> TradeDiscountCalculatorFragment()
                "Measures of Central Tendency" -> CentralTendencyFragment()
                "Herfindahl-Hirschman Index (HHI)" -> HHIFragment()
                "Markdown Calculator" -> MarkdownCalculatorFragment()
                "Depreciation Expense" -> DepreciationCalculatorFragment()
                "Markdown" -> MarkdownCalculatorFragment()
                "Quick Ratio" -> QuickRatioFragment()
                else -> null
            }

            // Check if the calculator fragment is not null
            if (calculatorFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.calculator_container, calculatorFragment).addToBackStack(null)
                    .commit()
                linearLayout.visibility = View.GONE
                searchView.clearFocus()
                toolbar.title = selectedOption.name
            }

            if (calculatorFragment?.isVisible == true && calculatorFragment.isAdded) {
                linearLayout.visibility = View.GONE
            } else {
                linearLayout.visibility = View.VISIBLE
            }
        }
        // Set the adapter on the recycler view
        recyclerView.adapter = adapter
    }

    fun enableRecyclerView() {
        recyclerView.isEnabled = true
        recyclerView.alpha = 1.0f
    }

    private fun showCalculatorDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_calculator_layout, null)
        textViewDisplay = dialogView.findViewById(R.id.textViewDisplay)
        textViewDisplay.text = "0"

        // Define click listeners for all buttons and handle their actions
        val buttonIds = arrayOf(
            R.id.buttonClear,
            R.id.buttonDelete,
            R.id.buttonSignToggle,
            R.id.buttonMultiply,
            R.id.button7,
            R.id.button8,
            R.id.button9,
            R.id.buttonSubtract,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.buttonAdd,
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.buttonDivide,
            R.id.button0,
            R.id.buttonDecimal,
            R.id.buttonEqual,
            R.id.buttonPercentage
        )

        for (buttonId in buttonIds) {
            dialogView.findViewById<Button>(buttonId)
                .setOnClickListener { onButtonClick(it as Button) }
        }

        val dialog = AlertDialog.Builder(this).setTitle("Calculator").setView(dialogView)
            .setCancelable(false).setNegativeButton("Exit", null).create()

        // Show the dialog
        dialog.show()
    }

    private fun onButtonClick(button : Button) {
        val buttonText = button.text.toString()
        val currentValue = textViewDisplay.text.toString()

        when (buttonText) {
            // Clear the display
            "C" -> textViewDisplay.text = "0"

            // Delete the last character
            "⌫" -> {
                if (currentValue.length > 1) {
                    textViewDisplay.text = currentValue.dropLast(1)
                } else {
                    textViewDisplay.text = "0"
                }
            }

            // Toggle the sign of the number
            "+/-" -> {
                if (currentValue.startsWith("-")) {
                    textViewDisplay.text = currentValue.substring(1)
                } else {
                    textViewDisplay.text = buildString {
                        append("-")
                        append(currentValue)
                    }
                }
            }

            // Perform arithmetic operations
            "+", "-", "×", "/", "%" -> {
                // Append the operator to the display
                textViewDisplay.append(buttonText)
            }

            // Calculate the result
            "=" -> {
                val expression = textViewDisplay.text.toString()
                val result = evaluateExpression(expression)
                textViewDisplay.text = result.removeSuffix(".0")
            }

            // Append the digit or decimal point to the display
            else -> {
                if (currentValue == "0" && buttonText != ".") {
                    textViewDisplay.text = buttonText
                } else {
                    textViewDisplay.append(buttonText)
                }
            }
        }
    }

    private fun evaluateExpression(expression : String) : String {
        val mExpression = expression.replace(",", "")

        // Replace decimal values with their double representations
        val decimalPattern = Regex("\\b\\d+\\.\\d+\\b")
        val processedExpression = decimalPattern.replace(mExpression) { match ->
            match.value.toDouble().toString()
        }

        // Replace × with * for multiplication
        val sanitizedExpression = processedExpression
            .replace("×", "*")
            .replace("%", "/100")
            .replace("÷", "/")

        // Input validation
        // val pattern = Regex("^[-+*/()\\d.\\s]+$")
        //if (!pattern.matches(sanitizedExpression)) {
        //    throw IllegalArgumentException("Invalid expression: $expression")
        //}

        return try {
            val exp = Expression(sanitizedExpression).evaluate().value
            val formatter = DecimalFormat("#,###.##")
            formatter.format(exp)
        } catch (e : ParseException) {
            // Handle too many operands error
            "Error: Too many operands"
        }
    }

    override fun onCreateOptionsMenu(menu : Menu?) : Boolean {
        menuInflater.inflate(R.menu.menu_calculator, menu)
        return true
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        return when (item.itemId) {
            R.id.action_show_calculator -> {
                showCalculatorDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // Override onDestroy to clear the toolbar title
    override fun onDestroy() {
        super.onDestroy()
        supportActionBar?.setTitle(R.string.app_name)
    }

    override fun onResume() {
        super.onResume()
        toolbar.title = getString(R.string.calculator)
        navView.setCheckedItem(R.id.nav_item_calculator)
        // Register the observer when the activity is resumed
        adapter.registerFragmentLifecycleObserver(calculatorFragmentLifecycleObserver)
    }

    override fun onPause() {
        super.onPause()
        // Unregister the observer when the activity is paused to avoid memory leaks
        adapter.unregisterFragmentLifecycleObserver(calculatorFragmentLifecycleObserver)
    }
}
