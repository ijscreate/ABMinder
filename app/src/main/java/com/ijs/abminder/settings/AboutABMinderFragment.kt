package com.ijs.abminder.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ijs.abminder.R

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

            1. Dictionary: Access a comprehensive dictionary of business and finance terms directly within the ABMinder app. Look up definitions, explanations, and examples to enhance your understanding of key terminology used in your studies.

            2. Calculator: Utilize specialized calculators for various financial calculations, such as compound interest, annuities, present value, and more. Our calculators are specifically tailored to the needs of ABM students, providing accurate and efficient solutions to complex mathematical problems.

            3. Lessons: Access detailed lessons and tutorials covering essential topics in Business Mathematics, Business Finance, FABM 1, and Applied Economics. Our carefully curated content is designed to help you understand complex concepts and build a solid foundation in these subjects.

            4. Quiz: Test your knowledge and reinforce your learning with interactive quizzes and practice exercises. Track your progress and identify areas for improvement as you work through our quizzes designed to simulate exam conditions.

            5. Personalized Study Plans: Create personalized study plans tailored to your individual learning needs and goals. Set reminders, track your study time, and stay organized to maximize your productivity and academic success.

            6. Real-World Examples: Explore real-world examples and case studies to illustrate key concepts and their practical applications in the business world. Our application bridges the gap between theory and practice, helping you understand how theoretical concepts are applied in real-life scenarios.

            7. User-Friendly Interface: ABMinder features a user-friendly interface designed for seamless navigation and ease of use. Whether you're studying at home, in the library, or on the go, our app provides a convenient and accessible learning experience anytime, anywhere.
        """.trimIndent()

        aboutAbminderTextView.text = des
    }
}