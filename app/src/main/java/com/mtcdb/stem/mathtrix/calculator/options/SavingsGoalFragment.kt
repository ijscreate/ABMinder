// SavingsGoalFragment.kt

package com.mtcdb.stem.mathtrix.calculator.options

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.google.android.material.dialog.*
import com.mtcdb.stem.mathtrix.*
import java.text.*

class SavingsGoalFragment : Fragment() {

    private lateinit var goalAmountEditText : EditText
    private lateinit var monthlySavingEditText : EditText
    private lateinit var calculateButton : Button
    private lateinit var resultTextView : TextView
    private lateinit var explanationButton : Button
    private lateinit var descriptionTextView : TextView

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(
            com.calculator.calculatoroptions.R.layout.fragment_savings_goal,
            container,
            false
        )

        // Initialize UI components
        goalAmountEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextGoalAmount)
        monthlySavingEditText =
            view.findViewById(com.calculator.calculatoroptions.R.id.editTextMonthlySaving)
        calculateButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonCalculateSavingsGoal)
        resultTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewSavingsGoalResult)
        explanationButton =
            view.findViewById(com.calculator.calculatoroptions.R.id.buttonExplanationSavingsGoal)
        descriptionTextView =
            view.findViewById(com.calculator.calculatoroptions.R.id.textViewSavingsGoalDescription)

        calculateButton.setOnClickListener {
            calculateSavingsGoal()
        }

        explanationButton.setOnClickListener {
            showExplanationDialog()
        }

        val description = """
            To use the Savings Goal Planner:

            1. Enter your desired savings goal amount in the Savings Goal Amount field.
            2. Input the amount you can save monthly in the Monthly Saving field.
            3. Click on the Calculate button.

            The calculator will then display the result, indicating how many months it will take to reach your savings goal based on your monthly savings.

            For example:
            If your savings goal is ₱1000 and you save ₱100 per month:

            Number of Months = ₱1000 / ₱100 = 10 months

            It's an effective tool for planning your savings strategy and achieving financial goals.

            Adjust the monthly saving amount to see how it affects the time needed to reach your goal.
        """.trimIndent()


        descriptionTextView.text = description

        return view
    }

    override fun onDestroy() {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.toolbar.title = getString(R.string.calculator)
        super.onDestroy()
    }

    private fun calculateSavingsGoal() {
        val goalAmount = goalAmountEditText.text.toString().toDoubleOrNull() ?: 0.0
        val monthlySaving = monthlySavingEditText.text.toString().toDoubleOrNull() ?: 0.0

        val monthsToReachGoal = (goalAmount / monthlySaving).toInt()

        val decimalFormat = DecimalFormat("#.##")
        resultTextView.text = getString(
            com.calculator.calculatoroptions.R.string.savings_goal_result,
            decimalFormat.format(monthlySaving),
            monthsToReachGoal
        )
    }

    private fun showExplanationDialog() {
        val explanation = """
            To calculate the number of months required to reach your savings goal:

            Monthly Saving = Amount Saved per month
            Number of Months = Total Goal Amount / Monthly Saving

            For example:
            If your savings goal is $1000 and you save $100 per month:

            Number of Months = $1000 / $100 = 10 months

            Adjust the monthly saving amount to see how it affects the time needed to reach your goal.
        """.trimIndent()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Savings Goal Calculation Explanation")
            .setMessage(explanation)
            .setPositiveButton("OK", null)
            .show()
    }
}
