package com.ijs.abminder.learn.chapters

import android.os.*
import android.view.*
import androidx.fragment.app.*
import androidx.recyclerview.widget.*
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.ijs.abminder.*
import com.ijs.abminder.learn.lessons.*
import com.ijs.abminder.learn.subjects.*

class ChaptersFragment : Fragment() {

    companion object {
        private const val ARG_SUBJECT = "arg_subject"

        fun newInstance(subject : String) : ChaptersFragment {
            val fragment = ChaptersFragment()
            val args = Bundle()
            args.putString(ARG_SUBJECT, subject)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mainActivity : SubjectsActivity
    private lateinit var selectedSubject : String

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val view = inflater.inflate(R.layout.fragment_chapters, container, false)

        // Initialize RecyclerView
        val recyclerView : RecyclerView = view.findViewById(R.id.recyclerViewChapters)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        selectedSubject = arguments?.getString(ARG_SUBJECT) ?: ""
        recyclerView.adapter =
            ChaptersAdapter(getChaptersForSubject(selectedSubject), ::onChapterSelected)

        mainActivity = (activity as? SubjectsActivity)!!

        TransitionManager.beginDelayedTransition(container!!, AutoTransition())

        return view
    }

    private fun onChapterSelected(chapter : String) {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(
            R.id.content_frame,
            LessonsFragment.newInstance(chapter, selectedSubject)
        )
        transaction.addToBackStack(null)
        transaction.commit()
        mainActivity.toolbar.title = chapter
    }

    private fun getChaptersForSubject(subject : String) : List<String> {
        return when (subject) {
            "Business Mathematics" -> listOf(
                "Fundamental Operations on Fractions, Decimals, and Percentage",
                "Ratio and Proportion",
                "Buying and Selling",
                "Salaries and Wages",
                "Presentation and Analysis of Business Data",
            )

            "Business Finance" -> listOf(
                "Introduction to Business and Finance",
                "Financial Statement Preparation, Analysis, and Interpretation",
                "Financial Planning Tools and Concepts",
                "Sources and Uses of Short-Term and Long-Term Funds",
                "Investments",
            )

            "Business Ethics" -> listOf(
                "Business in Social and Economic",
                "Foundations of the Principles of Business Ethics",
                "Social Responsibility of Entrepreneurs",
                "Business Beyond Profit Motivation"
            )

            "Applied Economics" -> listOf(
                "Introduction to Economics",
                "Application of Demand",
                "Industry and Environment Analysis",
                "Socioeconomic Impact Study",
            )

            "Fundamentals of ABM I" -> listOf(
                "Introduction to Accounting",
                "Branches of Accounting",
                "Users of Accounting Information",
                "Forms of Business Organization",
                "Types of Business According to Activities",
                "Accounting Concepts and Principles",
                "The Accounting Equation",
            )

            "Fundamentals of ABM II" -> listOf(
                "Statement of Financial Position",
                "Preparation of the Statement of Financial Position",
                "Statement of Comprehensive Income",
                "Statement of Changes in Equity",
                "Statement of Cash Flows",
                "Bank Account and Financial Health",
            )

            "Principles of Marketing" -> listOf(
                "Marketing Principles and Strategies",
                "Customer Relationships",
                "Market Opportunity Analysis and Consumer Analysis",
            )

            "Organization and Management" -> listOf(
                "Nature and Concept of Management",
                "Firm and its Environment",
            )


            else -> listOf()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.toolbar.title = getString(R.string.learn)
    }

}
