package com.ijs.abminder.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ijs.abminder.R

class CreditFragment : Fragment() {

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credit, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize TextViews
        val philippinePesoCreditTextView =
            view.findViewById<TextView>(R.id.philippinePesoCreditTextView)
        val learningCreditTextView = view.findViewById<TextView>(R.id.learningCreditTextView)
        val testCreditTextView = view.findViewById<TextView>(R.id.testCreditTextView)
        val stackOfBooksCreditTextView =
            view.findViewById<TextView>(R.id.stackOfBooksCreditTextView)
        val openBookCreditTextView = view.findViewById<TextView>(R.id.openBookCreditTextView)
        val readCreditTextView = view.findViewById<TextView>(R.id.readCreditTextView)
        val searchCreditTextView = view.findViewById<TextView>(R.id.searchCreditTextView)

        // Set credits for each image/icon
        // Set text for each credit TextView with links
        philippinePesoCreditTextView.text =
            "Philippine Peso Icon: Designed by Freepik from Flaticon (https://www.flaticon.com/free-icon/philippine-peso_6507209?term=coins+peso&page=1&position=9&origin=search&related_id=6507209)"
        learningCreditTextView.text =
            "Learning Icon: Designed by photo3idea_studio from Flaticon (https://www.flaticon.com/free-icon/learning_3321726?term=learn&page=1&position=87&origin=search&related_id=3321726)"
        testCreditTextView.text =
            "Test Icon: Designed by Freepik from Flaticon (https://www.flaticon.com/free-icon/test_2995522?term=test&page=1&position=21&origin=search&related_id=2995522)"
        stackOfBooksCreditTextView.text =
            "Stack of Books Icon: Designed by Freepik from Flaticon (https://www.flaticon.com/free-icon/stack-of-books_5833290?term=books&related_id=5833290)"
        openBookCreditTextView.text =
            "Open Book Icon: Designed by Freepik from Flaticon (https://www.flaticon.com/free-icon/open-book_2702154?term=books&page=1&position=23&origin=search&related_id=2702154)"
        readCreditTextView.text =
            "Read Icon: Designed by Freepik from Flaticon (https://www.flaticon.com/free-icon/read_4696863?term=reading&page=2&position=10&origin=search&related_id=4696863)"
        searchCreditTextView.text =
            "Search Icon: Designed by Smashicons from Flaticon (https://www.flaticon.com/free-icon/search_200941?term=search&page=1&position=47&origin=search&related_id=200941)"

    }
}
