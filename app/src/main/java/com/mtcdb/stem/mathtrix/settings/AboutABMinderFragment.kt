package com.mtcdb.stem.mathtrix.settings

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.mtcdb.stem.mathtrix.*

class AboutAbminderFragment : Fragment() {

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        return inflater.inflate(R.layout.fragment_about_abminder, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aboutAbminderTextView = view.findViewById<TextView>(R.id.about_abminder_text_view)
        val des = """
            Welcome to ABMinder – your ultimate companion for mastering Business Mathematics, Business Finance, FABM 1, and Applied Economics!

            ABMinder is a mobile application developed by our dedicated team of researchers at Mother Theresa Colegio De Balatan. Designed specifically for ABM (Accountancy, Business, and Management) students, ABMinder aims to provide a comprehensive and interactive learning experience to support students in their academic journey.

            Key features of ABMinder include:

            1. Comprehensive Lessons: Access detailed lessons and tutorials covering essential topics in Business Mathematics, Business Finance, FABM 1, and Applied Economics. Our carefully curated content is designed to help you understand complex concepts and build a solid foundation in these subjects.

            2. Interactive Quizzes: Test your knowledge and reinforce your learning with interactive quizzes and practice exercises. Track your progress and identify areas for improvement as you work through our quizzes designed to simulate exam conditions.

            3. Personalized Study Plans: Create personalized study plans tailored to your individual learning needs and goals. Set reminders, track your study time, and stay organized to maximize your productivity and academic success.

            4. Real-World Examples: Explore real-world examples and case studies to illustrate key concepts and their practical applications in the business world. Our application bridges the gap between theory and practice, helping you understand how theoretical concepts are applied in real-life scenarios.

            5. User-Friendly Interface: ABMinder features a user-friendly interface designed for seamless navigation and ease of use. Whether you're studying at home, in the library, or on the go, our app provides a convenient and accessible learning experience anytime, anywhere.
        """.trimIndent()
        aboutAbminderTextView.text = des
    }
}