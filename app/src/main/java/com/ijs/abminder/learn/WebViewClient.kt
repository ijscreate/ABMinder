package com.ijs.abminder.learn

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatDelegate

class ThemeAwareWebViewClient : WebViewClient() {
    private val themeColorScript =
        "var meta = document.querySelector('meta[name=theme-color]'); " +
                "meta.setAttribute('content', '${if (isDarkMode) "#000000" else "#ffffff"}');"

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        view?.evaluateJavascript(themeColorScript, null)
    }

    companion object {
        private val isDarkMode: Boolean
            get() = (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
    }
}