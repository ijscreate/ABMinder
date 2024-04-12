package com.ijs.abminder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.ijs.abminder.calculator.CalculatorOptionsActivity
import com.ijs.abminder.dictionary.DictionaryActivity
import com.ijs.abminder.learn.subjects.SubjectsActivity
import com.ijs.abminder.quiz.DifficultyLevel
import kotlin.random.Random


class DashboardFragment : Fragment() {

    // List of random tips or motivations
    private val tipsList = listOf(
        "Stay focused and never give up!",
        "Take breaks to recharge your energy.",
        "Set achievable goals and celebrate your progress.",
        "Keep learning and expanding your skills.",
        "Believe in yourself and your abilities.",
        "Stay positive and optimistic.",
        "Surround yourself with supportive people.",
        "Stay organized and prioritize your tasks.",
        "Embrace challenges as opportunities for growth.",
        "Stay persistent and persevere through obstacles.",
        "Practice time management and avoid procrastination.",
        "Maintain a healthy work-life balance.",
        "Learn from your mistakes and use them as learning experiences.",
        "Stay curious and open-minded to new perspectives.",
        "Develop a growth mindset and embrace continuous improvement.",
        "Celebrate small wins and acknowledge your progress.",
        "Stay disciplined and consistent in your efforts.",
        "Seek feedback and use it constructively.",
        "Find inspiration in others' success stories.",
        "Practice self-care and prioritize your well-being.",
        "Stay humble and always strive to learn more.",
        "Embrace new challenges and step out of your comfort zone.",
        "Cultivate a positive attitude and spread positivity.",
        "Stay resilient and bounce back from setbacks.",
        "Develop strong time management skills.",
        "Practice mindfulness and stay present in the moment.",
        "Celebrate diversity and embrace different perspectives.",
        "Stay accountable and take responsibility for your actions.",
        "Develop effective communication skills.",
        "Practice gratitude and appreciate the little things.",
        "Stay determined and never lose sight of your goals.",
        "Embrace change and adapt to new situations.",
        "Stay focused on continuous self-improvement.",
        "Cultivate a sense of purpose and passion for what you do.",
        "Stay motivated and inspired by your dreams and aspirations.",
        "Learn about different accounting principles and practices.",
        "Stay updated with the latest business trends and technologies.",
        "Develop critical thinking and problem-solving skills.",
        "Network and build professional connections.",
        "Seek internships or practical experience in your field.",
        "Learn effective marketing and business strategies.",
        "Develop strong leadership and teamwork skills.",
        "Stay organized and detail-oriented in your work.",
        "Embrace ethical practices and integrity in business.",
        "Develop financial literacy and investment knowledge.",
        "Stay adaptable and open to learning new business models.",
        "Cultivate entrepreneurial skills and innovative thinking.",
        "Learn to analyze and interpret financial statements.",
        "Stay informed about relevant laws and regulations.",
        "Develop effective communication skills for presentations and meetings.",
        "Embrace continuous learning and professional development.",
        "Stay motivated by the potential for career growth and advancement.",
        "Learn to manage resources and budgets effectively.",
        "Cultivate strategic thinking and decision-making skills.",
        "Stay passionate about your chosen field and its impact.",
        "Believe in yourself and your abilities.",
        "Embrace challenges as opportunities to grow.",
        "Find inspiration in those around you.",
        "Practice mindfulness and stay present.",
        "Surround yourself with positive influences.",
        "Celebrate small wins along the way.",
        "Failure is a stepping stone to success.",
        "Consistency is key to achieving your goals.",
        "Stay curious and ask questions.",
        "Embrace your unique strengths and talents.",
        "Seek out new experiences and perspectives.",
        "Prioritize self-care and well-being.",
        "Collaborate and learn from others.",
        "Embrace change and adapt with flexibility.",
        "Perseverance pays off in the long run.",
        "Celebrate diversity and inclusivity.",
        "Focus on solutions, not problems.",
        "Take calculated risks and step out of your comfort zone.",
        "Cultivate gratitude for the present moment.",
        "Continuously challenge yourself to grow.",
        "Seek feedback and use it to improve.",
        "Practice patience and resilience.",
        "Embrace lifelong learning and curiosity.",
        "Surround yourself with supportive individuals.",
        "Find joy in the journey, not just the destination.",
        "Trust in your abilities and believe in yourself.",
        "Develop a growth mindset and embrace learning from mistakes.",
        "Cultivate resilience and bounce back from setbacks.",
        "Nurture your passions and pursue your dreams.",
        "Simplify your life and focus on what truly matters.",
        "Embrace diversity and learn from different perspectives.",
        "Practice self-compassion and be kind to yourself.",
        "Find balance and prioritize your well-being.",
        "Celebrate progress, no matter how small.",
        "Embrace change as an opportunity for growth.",
        "Surround yourself with positivity and uplifting influences.",
        "Seek out mentors and learn from their experiences.",
        "Cultivate patience and allow things to unfold naturally.",
        "Practice mindfulness and live in the present moment.",
        "Embrace challenges as opportunities to develop resilience.",
        "Celebrate your uniqueness and individuality.",
        "Find joy in the simple pleasures of life.",
        "Cultivate a positive mindset and outlook on life.",
        "Embrace lifelong learning and continuous self-improvement.",
        "Surround yourself with supportive individuals who uplift you.",
        "Celebrate your progress and acknowledge your achievements.",
        "Embrace change as a catalyst for personal growth."

    )

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        displayRandomTip()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        view.findViewById<CardView>(R.id.dictionary_card).setOnClickListener {
            startActivity(Intent(requireActivity(), DictionaryActivity::class.java))

        }

        view.findViewById<CardView>(R.id.calculator_card).setOnClickListener {
            val intent = Intent(requireContext(), CalculatorOptionsActivity::class.java)
            startActivity(intent)

        }

        view.findViewById<CardView>(R.id.learn_card).setOnClickListener {
            val intent = Intent(requireContext(), SubjectsActivity::class.java)
            startActivity(intent)

        }

        view.findViewById<CardView>(R.id.quiz_card).setOnClickListener {
            val intent = Intent(requireContext(), DifficultyLevel::class.java)
            startActivity(intent)
        }
        return view
    }

    private fun displayRandomTip() {
        // Get a random index within the range of the tipsList
        val randomIndex = Random.nextInt(tipsList.size)

        // Get the random tip using the random index
        val randomTip = tipsList[randomIndex]
        val textViewRandomTip = view?.findViewById<TextView>(R.id.randomTips)

        // Display the random tip on the TextView
        textViewRandomTip?.text = randomTip
    }
}