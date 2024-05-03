package com.ijs.abminder.dictionary.ui

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ijs.abminder.R
import java.util.Locale

class ViewBookmarkFragment : Fragment(), OnInitListener {

    private lateinit var textToSpeech : TextToSpeech
    private lateinit var speakButton : ImageButton

    companion object {

        private const val ARG_BOOKMARKED_TERM = "bookmarked_term"
        private const val ARG_BOOKMARKED_DEFINITION = "bookmarked_definition"
        private const val ARG_BOOKMARKED_EXAMPLE = "bookmarked_example"

        fun newInstance(term : String, definition : String, example : String) : Fragment {
            val fragment = ViewBookmarkFragment()
            val args = Bundle()
            args.apply {
                putString(ARG_BOOKMARKED_TERM, term)
                putString(ARG_BOOKMARKED_DEFINITION, definition)
                putString(ARG_BOOKMARKED_EXAMPLE, example)
            }
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {

        val view = inflater.inflate(R.layout.fragment_view_bookmark, container, false)

        TransitionManager.beginDelayedTransition(
            container, AutoTransition()
        )

        // Initialize Text-to-Speech engine
        textToSpeech = TextToSpeech(requireContext(), this)

        val term = arguments?.getString(ARG_BOOKMARKED_TERM)!!
        val definition = arguments?.getString(ARG_BOOKMARKED_DEFINITION)!!
        val example = arguments?.getString(ARG_BOOKMARKED_EXAMPLE)!!

        view.findViewById<TextView>(R.id.termTextView).text = term
        view.findViewById<TextView>(R.id.term_definition).text = definition
        view.findViewById<TextView>(R.id.term_example).text = example

        speakButton = view.findViewById(R.id.speakButton)
        speakButton.setOnClickListener {
            speakText(view.findViewById<TextView>(R.id.termTextView).text.toString())
        }

        val shareButton : ImageButton = view.findViewById(R.id.shareImageButton)
        shareButton.setOnClickListener {
            shareTerm(term, definition, example)
        }

        return view
    }

    override fun onInit(status : Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Set language for Text-to-Speech
            val result = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(ContentValues.TAG, "Language not supported")
                toast("Language not supported")
            } else {
                Log.i(ContentValues.TAG, "Text-to-Speech engine initialized")
            }
        } else {
            Log.e(ContentValues.TAG, "Initialization failed")
            toast("Initialization failed")
        }
    }

    private fun speakText(text : String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun toast(msg : String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun shareTerm(term : String, definition : String, example : String?) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Dictionary Term: $term")
        val shareMessage = "Term: $term\nDefinition: $definition\nExample: $example"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "Share Term"))
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release Text-to-Speech engine
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}