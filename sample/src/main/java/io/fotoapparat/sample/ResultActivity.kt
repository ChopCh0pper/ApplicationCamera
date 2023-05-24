package io.fotoapparat.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import io.fotoapparat.sample.constance.Constance
import org.jsoup.Jsoup
import org.w3c.dom.Document
import java.io.IOException

class ResultActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var number: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        webView = findViewById(R.id.webView)
        number = intent.getStringExtra(Constance.INTENT_KEY_1)

        webView.loadUrl("https://avtocod.ru/proverkaavto/${number}?rd=GRZ")
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
    }
}