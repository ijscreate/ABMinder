package com.mtcdb.stem.mathtrix.settings

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.transition.*
import com.mtcdb.stem.mathtrix.R

class AboutUsFragment : Fragment() {

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about_us, container, false)

        // Get the TextView from the layout
        val aboutUsTextView = view.findViewById<TextView>(R.id.about_us_text_view)

        val des = """
            Welcome to our research group at Mother Theresa Colegio De Balatan! We are a dedicated team of researchers passionate about exploring innovative solutions to educational challenges. Our group, consisting of students from the STEM (Science, Technology, Engineering, and Mathematics) strand, brings together diverse perspectives and expertise to address complex problems in the field of education.

            Motivated by a shared commitment to academic excellence and continuous learning, our research focuses on:

            -Exploring the impact of technology on learning outcomes
            -Investigating effective teaching methodologies and strategies
            -Developing educational tools and resources to support student success, including our main product, the ABMinder android application
            -Analyzing trends in educational data and assessment practices

            Our product, ABMinder, an offline android application, is developed to support ABM (Accountancy, Business, and Management) students with a comprehensive and interactive learning experience. Through ABMinder, students can access detailed lessons, interactive quizzes, and business calculators to help them in their studies and prepare for successful careers.

            Through our collaborative efforts and thorough research, we aim to provide valuable insights to the educational community and make a positive impact on the lives of students and educators alike.
        """.trimIndent()
        // Set the text content
        aboutUsTextView.text = des

        TransitionManager.beginDelayedTransition(container!!, AutoTransition())

        return view
    }
}