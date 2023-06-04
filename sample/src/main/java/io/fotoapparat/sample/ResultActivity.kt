package io.fotoapparat.sample

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.fotoapparat.sample.constance.Constance
import io.fotoapparat.sample.constance.Scripts

class ResultActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var number: String
    private lateinit var myWebViewClient: MyWebViewClient
    private lateinit var url: String
    private lateinit var carName: TextView
    private lateinit var governmentNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        carName = findViewById(R.id.carName)
        governmentNumber = findViewById(R.id.governmentNumber)
        number = intent.getStringExtra(Constance.INTENT_KEY_1)

        url = "https://avtocod.ru/proverkaavto/${number}?rd=GRZ"
        webView = findViewById(R.id.webView)
        myWebViewClient = MyWebViewClient()
        webView.webViewClient = myWebViewClient

        myWebViewClient.onPageLoaded = {
            if (it == url) {
                webView.evaluateJavascript(Scripts.CAR_NAME_SCRIPT){
                        s ->
                    carName.text = s
                    Log.d(Constance.LOG_JS_RESULT, s)
                }
                webView.evaluateJavascript(Scripts.GOVERNMENT_NUMBER_SCRIPT){
                        s ->
                    governmentNumber.text = s
                    Log.d(Constance.LOG_JS_RESULT, s)
                }
            } else {
                Toast.makeText(applicationContext, Constance.TOAST_TEXT, Toast.LENGTH_LONG).show()
            }
        }

        webView.loadUrl(url)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
    }
    class MyWebViewClient: WebViewClient() {
        var onPageLoaded: ((String) -> Unit)? = null
        override fun onPageFinished(view: WebView?, url: String?) {
            onPageLoaded?.invoke(url!!)
        }
    }
}