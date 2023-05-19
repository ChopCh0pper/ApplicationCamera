package io.fotoapparat.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import io.fotoapparat.sample.constance.Constance

class ResultActivity : AppCompatActivity() {
    private var result = ""
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvResult = findViewById(R.id.tvResult)
        result = intent.getStringExtra(Constance.INTENT_KEY_1)

        tvResult.text = result
    }
}