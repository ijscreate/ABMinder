package com.mtcdb.stem.mathtrix.learn.lessons

import android.annotation.*
import android.os.*
import android.view.*
import androidx.fragment.app.*
import androidx.recyclerview.widget.*
import androidx.transition.*
import com.mtcdb.stem.mathtrix.*
import com.mtcdb.stem.mathtrix.R
import com.mtcdb.stem.mathtrix.learn.*


class LessonsFragment : Fragment() {

    companion object {
        private const val ARG_SELECTED_CHAPTER = "selectedChapter"
        private const val ARG_SELECTED_SUBJECT = "selectedSubject"

        fun newInstance(selectedChapter : String, selectedSubject : String) : LessonsFragment {
            val fragment = LessonsFragment()
            val args = Bundle()
            args.putString(ARG_SELECTED_CHAPTER, selectedChapter)
            args.putString(ARG_SELECTED_SUBJECT, selectedSubject)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var selectedChapter : String
    private lateinit var selectedSubject : String

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val view = inflater.inflate(R.layout.fragment_lessons, container, false)
        val recyclerView : RecyclerView = view.findViewById(R.id.recyclerViewLessons)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        selectedChapter = arguments?.getString(ARG_SELECTED_CHAPTER) ?: ""
        selectedSubject = arguments?.getString(ARG_SELECTED_SUBJECT) ?: ""

        TransitionManager.beginDelayedTransition(container!!, AutoTransition())
        recyclerView.adapter =
            LessonsAdapter(getLessonsForChapter(selectedChapter), ::onLessonSelected)
        return view
    }

    private fun onLessonSelected(lesson : Lesson) {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(
            R.id.fragment_container,
            WebViewFragment.newInstance(arguments?.getString(ARG_SELECTED_CHAPTER), lesson)
        )
        transaction.addToBackStack(null)
        transaction.commit()
        val mainActivity = requireActivity() as MainActivity
        mainActivity.toolbar.title = lesson.name
    }


    private fun getLessonsForChapter(chapter : String) : List<Lesson> {
        return when (chapter) {

            //BUSINESS MATHEMATICS
            "Fundamental Operations on Fractions, Decimals, and Percentage" -> listOf(
                Lesson("Fundamental Operations", "fundamental_operations.html"),
                Lesson("Addition and Subtraction of Fractions", "addition_subtraction.html"),
                Lesson("Multiplication and Division of Fractions", "multiplication_division.html"),
                Lesson("Decimals and their Operations", "decimal_operations.html"),
                Lesson("Addition and Subtraction of Decimals", "addsub_decimals.html"),
                Lesson("Multiplication and Division of Decimals", "muldiv_decimals.html"),
                Lesson("Percentage", "percent.html"),
            )

            "Ratio and Proportion" -> listOf(
                Lesson("Ratio and Proportion", "ratio.html"),
                Lesson("Formulating and Solving Problems", "formulating_ratio.html"),
                Lesson("Using Proportions", "proportions.html"),
            )

            "Buying and Selling" -> listOf(
                Lesson("Introduction to Buying and Selling", "buying_and_selling.html"),
                Lesson("Pricing", "pricing.html"),
                Lesson("Profit and Loss", "profit_loss.html"),
                Lesson("Mark Up and Profit Margin", "lesson_3.3.html"),
                Lesson("Mark On", "mark_on.html"),
                Lesson("Mark Down", "mark_down.html"),
                Lesson("Single Trade Discounts and Discount Series", "trade_discount.html"),
                Lesson("Simple Interest", "simple_interest.html"),
                Lesson("Compound Interest", "compound_interest.html"),
            )

            "Salaries and Wages" -> listOf(
                Lesson("Salaries and Wages", "salaries_wages.html"),
                Lesson("Benefits and Deductions", "benefits_deductions.html"),
                Lesson("Computing Gross and Net Earnings", "computing_earnings.html"),
                Lesson("Commissions", "commissions.html"),
            )

            "Presentation and Analysis of Business Data" -> listOf(
                Lesson("Business Data", "business_data_forms.html"),
                Lesson("Levels of Measurement", "measurement_levels.html"),
                Lesson("Measures of Central Tendency", "central_tendency.html"),
                Lesson("Presentations and Analysis of Data", "presentations_analysis.html"),
            )

            //BUSINESS FINANCE

            "Introduction to Business and Finance" -> listOf(
                Lesson("Business Finance", "business_finance.html"),
                Lesson("Financial Management", "financial_management.html"),
            )

            "Financial Statement Preparation, Analysis, and Interpretation" -> listOf(
                Lesson("Accounting", "accounting.html"),
                Lesson("Liquidity", "liquidity.html"),
                Lesson("Profitability", "profitability.html"),
                Lesson("Efficiency", "efficiency.html"),
                Lesson("Financial Leverage", "financial_leverage.html"),
                Lesson("Case Analysis", "case_analysis.html"),
                Lesson("Horizontal and Vertical Analysis", "hover_analysis.html"),
            )

            "Financial Planning Tools and Concepts" -> listOf(
                Lesson("Planning", "planning.html"),
                Lesson("Event Planning", "event_planning.html"),
                Lesson("Budgeting", "budgeting.html"),
                Lesson("Working Capital Assets", "working_capital_assets.html"),
                Lesson("Holding Cash", "holding_cash.html"),
            )

            "Sources and Uses of Short-Term and Long-Term Funds" -> listOf(
                Lesson("Financing", "financing.html"),
                Lesson("Short-Term and Long-Term Financing", "short_long_financing.html"),
                Lesson("Loans", "loans.html"),
            )

            "Long Term Financial Concepts" -> listOf(
                Lesson("Interest", "interest.html"),
                Lesson("Future Value and Present Value", "values.html"),
                Lesson("Annuity", "annuity.html"),
                Lesson("Risk and Return Trade-off", "risk_and_return.html"),
                Lesson("Loan Amortization", "loan_amortization.html"),
            )

            "Investments" -> listOf(
                Lesson("Investments", "investments.html"),
                Lesson("Risk", "risk.html"),
            )

            // FABM 1
            "Introduction to Accounting" -> listOf(
                Lesson("Introduction to Accounting", "introduction_accounting.html"),
                Lesson("Why Accounting is Important", "why_accounting_important.html"),
                Lesson("Nature of Accounting", "nature_accounting.html"),
            )

            "Branches of Accounting" -> listOf(
                Lesson("What are Accounting Branches?", "accounting_branches.html"),
                Lesson("Branches of Accounting", "branches_accounting.html"),
            )

            "Users of Accounting Information" -> listOf(
                Lesson("Accounting Information", "accounting_information.html"),
                Lesson("Internal Users of Accounting", "internal_users_accounting.html"),
                Lesson("External Users of Accounting", "external_users_accounting.html"),
            )

            "Forms of Business Organization" -> listOf(
                Lesson("Business Organization", "business_organization.html"),
                Lesson("Sole Proprietorship", "sole_proprietorship.html"),
                Lesson("Partnership", "partnership.html"),
                Lesson("Corporation", "corporation.html"),
                Lesson("Cooperative", "cooperative.html"),
            )

            "Types of Business According to Activities" -> listOf(
                Lesson("Service Companies", "service_companies.html"),
                Lesson("Merchandising Companies", "merchandising_companies.html"),
                Lesson("Manufacturing Companies", "manufacturing_companies.html"),
            )

            "Accounting Concepts and Principles" -> listOf(
                Lesson("Accrual Accounting", "accrual_accounting.html"),
                Lesson("Judgement and Estimates", "judgement_estimates.html"),
                Lesson("Prudence", "prudence.html"),
                Lesson("Substance Over Form", "substance_over_form.html"),
                Lesson("Going Concerns", "going_concerns.html"),
                Lesson("Time Period Assumption", "time_period_assumption.html"),
                Lesson(
                    "Generally Accepted Accounting Principles (GAAP)",
                    "generally_accepted_accounting_principles.html"
                ),
                Lesson(
                    "International Financial Reporting Standards (IFRS)",
                    "international_financial_reporting_standards.html"
                ),
                Lesson(
                    "Financial and Sustainability Reporting Standards Council (FSRSC)",
                    "financial_sustainability_reporting_standards_council.html"
                ),
            )

            "The Accounting Equation" -> listOf(
                Lesson("Statement of Assets, Liabilities, and Net Worth (SALN)", "saln.html"),
                Lesson("Accounting Equation", "accounting_equation.html"),
                Lesson("Elements of Accounting Equation", "elements_accounting_equation.html"),
            )

            //APPLIED ECONOMICS
            "Introduction to Economics" -> listOf(
                Lesson("Introduction to Economics", "applied_economics.html"),
                Lesson("Economic Development", "economic_development.html"),
                Lesson("Economic History", "economic_history.html"),
                Lesson("GDP & GNP", "gdp_gnp.html"),
            )

            "Application of Demand" -> listOf(
                Lesson(
                    "Basic Principle of Demand and Supply",
                    "basic_principle_demand_supply.html"
                ),
                Lesson(
                    "Market/Economic Equilibrium",
                    "market_economic_equilibrium.html"
                ),
                Lesson("Equilibrium Price", "equilibrium_price.html"),
                Lesson("Price Elasticity of Demand", "elasticity_demand_supply.html"),
                Lesson("Income Elasticity of Demand", "income_elasticity_demand.html"),
                Lesson("Cross Elasticity of Demand", "cross_elasticity_demand.html"),
                Lesson("Market Structures", "market_structures.html"),
                Lesson("Perfect Competition", "perfect_competition.html"),
                Lesson("Monopolistic Competition", "monopolistic_competition.html"),
                Lesson("Oligopoly", "oligopoly.html"),
                Lesson(
                    "Population Growth on the Philippine Economy and Labor Market",
                    "population_growth.html"
                ),
                Lesson(
                    "Labor Market", "labor_market.html"
                ),
                Lesson("Wage Situation in the Philippines", "wage_situation.html"),
                Lesson(
                    "Labor Migration and Overseas Filipino Workers (OFWs)",
                    "labor_migration.html"
                ),
                Lesson("Philippine Economic Problems", "philippine_economic_problem.html"),
                Lesson("Philippine Peso Exchange Rates", "philippine_peso.html"),
                Lesson("Saving vs. Investing", "saving_investing.html"),
                Lesson("Rent", "rent.html"),
                Lesson("Minimum Wage", "minimum_wage.html"),
            )

            "Industry and Environment Analysis" -> listOf(
                Lesson("Small, Medium, and Large scale Businesses", "scale_businesses.html"),
                Lesson("Tools in Evaluating a Business", "tools_business.html"),
                Lesson("SWOT Analysis", "swot_analysis.html"),
                Lesson("Porter's Five Forces", "porter_five_forces.html"),
                Lesson("Industry Analysis", "industry_analysis.html"),
                Lesson("Environmental Analysis", "environmental_analysis.html"),
            )

            "Socioeconomic Impact Study" -> listOf(
                Lesson("Consumer Theory", "consumer_theory.html"),
                Lesson("Utility Function", "utility_function.html"),
                Lesson("The Production Theory", "production_theory.html"),
                Lesson("Socioeconomic Impact of a Business", "socioeconomic_impact.html"),
                Lesson("Government Impact on Business", "government_impact.html"),
                Lesson("Trade and Capital Movement", "trade_capital_movement.html"),
                Lesson("Herfindahl-Hirschman Index (HHI)", "hh_index.html"),
            )

            //BUSINESS ETHICS
            "Business in Social and Economic" -> listOf(
                Lesson(
                    "Nature and Forms of Business Organization",
                    "nature_and_forms_business.html"
                ),
                Lesson(
                    "Purpose of Establishing Business Enterprises",
                    "purpose_establishing_business.html"
                ),
                Lesson("Core Principle of Business Organization", "core_principle_business.html"),
                Lesson(
                    "Common Practices in Business Organization",
                    "common_practices_business.html"
                ),
            )

            "Foundations of the Principles of Business Ethics" -> listOf(
                Lesson(
                    "Classical Philosophies and Virtue Ethics",
                    "classical_philosophy_ethics.html"
                ),
                Lesson("Impact of Belief Systems", "impact_belief_systems.html"),
                Lesson("Filipino Value System", "filipino_value_system.html"),
            )

            "Social Responsibility of Entrepreneurs" -> listOf(
                Lesson(
                    "Responsibilities and Accountabilities of Entrepreneurs",
                    "responsibilities_entrepreneurs.html"
                ),
                Lesson(
                    "Models and Frameworks of Social Responsibility",
                    "models_social_responsibility.html"
                ),
            )

            "Business Beyond Profit Motivation" -> listOf(
                Lesson("Social Entrepreneurship", "social_entrepreneurship.html"),
                Lesson("Profit Motive", "profit_motive.html"),
            )

            //FUNDAMENTALS OF ABM II
            "MODULE 1" -> listOf(
                Lesson("Statement of Financial Position", "statement_financial_position.html"),
                Lesson("Assets", "assets.html"),
                Lesson("Liability", "liability.html"),
                Lesson("Capital", "capital.html"),

                )

            "MODULE 2" -> listOf(
                Lesson(
                    "Preparation of the Statement of Financial Position of a Single Proprietorship",
                    "preparation_statement_financial_position.html"
                ),
                Lesson("Description of Account Titles", "description_account_titles.html"),
            )

            "MODULE 3" -> listOf(
                Lesson("Comprehensive Income", "comprehensive_income.html"),
                Lesson("Profit or Loss", "profit_or_loss.html"),
                Lesson("Other Comprehensive Income", "other_comprehensive_income.html"),
            )

            "MODULE 4" -> listOf(
                Lesson("Statement of Changes in Equity", "statement_changes_equity.html"),
                Lesson("Relevant Terminologies", "relevant_terminologies.html"),
                Lesson("Accounting for Share Issue", "accounting_share_issue.html"),

                )

            "MODULE 5" -> listOf(
                Lesson("Statement of Cash Flows", ""),
                Lesson("", ""),
                Lesson("", ""),
                Lesson("", ""),


                )

            "MODULE 6" -> listOf(
                Lesson("", ""),
                Lesson("Financial Institutions", "financial_institutions.html"),
                Lesson("Selecting Bank Accounts", "selecting_bank_accounts.html"),
                Lesson("Bank Reconciliation Statement", "bank_reconciliation.html"),
                Lesson("Savings Accounts", "savings_accounts.html"),

                )

            //PRINCIPLES OF MARKETING
            "Market Principles and Strategies" -> listOf(
                Lesson("What is Marketing?", "what_is_marketing.html"),
                Lesson("Traditional Approach to Marketing", "traditional_approach_marketing.html"),
                Lesson("Goals of Marketing", "goals_marketing.html"),
                Lesson(
                    "Contemporary Approach to Marketing",
                    "contemporary_approach_marketing.html"
                ),

                )

            "Customer Relationships" -> listOf(
                Lesson("Relationship Marketing", "relationship_marketing.html"),
                Lesson("Value of Customers", "value_customers.html"),
                Lesson(
                    "Relationship Development Strategies",
                    "relationship_development_strategies.html"
                ),
                Lesson(
                    "Successful Customer Service Strategy in the Philippine Business Enterprise",
                    "successful_customer_service.html"
                ),
            )

            "Market Opportunity Analysis and Consumer Analysis" -> listOf(

            )

            //ORGANISATION AND MANAGEMENT
            "Nature and Concept of Management" -> listOf(

            )

            "Firm and its Environment" -> listOf(

            )

            else -> emptyList()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val mainActivity = requireActivity() as MainActivity
        mainActivity.toolbar.title = selectedSubject
    }
}
