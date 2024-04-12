package com.ijs.abminder.learn

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.ijs.abminder.R
import com.ijs.abminder.learn.lessons.Lesson
import com.ijs.abminder.learn.subjects.SubjectsActivity
import java.io.IOException
import java.nio.charset.Charset

@Suppress("DEPRECATION")
class WebViewFragment : Fragment() {

    companion object {
        private const val ARG_SELECTED_CHAPTER = "selectedChapter"
        private const val ARG_SELECTED_LESSON = "selectedLesson"

        fun newInstance(selectedChapter : String?, selectedLesson : Lesson) : WebViewFragment {
            val fragment = WebViewFragment()
            val args = Bundle()
            args.putString(ARG_SELECTED_CHAPTER, selectedChapter)
            args.putParcelable(ARG_SELECTED_LESSON, selectedLesson)
            fragment.arguments = args
            return fragment
        }

    }

    private lateinit var mainActivity : SubjectsActivity
    private lateinit var selectedChapter : String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val view = inflater.inflate(R.layout.fragment_webview, container, false)

        // Initialize WebView
        val webView : WebView = view.findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true

        TransitionManager.beginDelayedTransition(container!!, AutoTransition())

        mainActivity = (activity as? SubjectsActivity)!!
        selectedChapter = arguments?.getString(ARG_SELECTED_CHAPTER) ?: ""

        // Load HTML content from assets based on selectedChapter and selectedLesson
        val selectedLesson = arguments?.getParcelable<Lesson>(ARG_SELECTED_LESSON)
        val htmlFileName = selectedLesson?.html

        if (htmlFileName != null) {
            try {
                val inputStream = requireActivity().assets.open(htmlFileName)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()

                val htmlContent = String(buffer, Charset.forName("UTF-8"))
                webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null)
            } catch (e : IOException) {
                e.printStackTrace()
            }
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.toolbar.title = selectedChapter
    }
}
