package io.fotoapparat.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import io.fotoapparat.sample.constance.Constance
import org.jsoup.Jsoup
import org.w3c.dom.Document
import java.io.IOException

class ResultActivity : AppCompatActivity() {
    private lateinit var number: String

    private lateinit var doc: org.jsoup.nodes.Document

    private lateinit var handler: Handler

    private lateinit var tvCarBrand: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        number = intent.getStringExtra(Constance.INTENT_KEY_1)
        tvCarBrand = findViewById(R.id.tvCarBrand)

        handler = Handler(Looper.getMainLooper())
        Thread {
            handler.post(getWeb())
        }.start()
    }

    private fun getWeb(): Runnable = Runnable {
        try {
            doc = Jsoup.connect("http://developer.alexanderklimov.ru/android/").get()

            tvCarBrand.text = doc.title()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}