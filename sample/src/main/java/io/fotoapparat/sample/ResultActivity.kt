package io.fotoapparat.sample

import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.fotoapparat.sample.constance.Constance

class ResultActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var number: String
    private lateinit var script: String
    private lateinit var carName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        carName = findViewById(R.id.carName)

        webView = findViewById(R.id.webView)
        number = intent.getStringExtra(Constance.INTENT_KEY_1)

        webView.loadUrl("https://avtocod.ru/proverkaavto/${number}?rd=GRZ")
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.evaluateJavascript("(f() return{document.querySelector(\".report-header__title\").innerText})()"
        ) { s ->
            carName.text = s
        }
    }
}