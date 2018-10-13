package com.stylingandroid.certificatepinning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val httpClient = HttpClient()

        button_go.setOnClickListener {
            httpClient.load(::display)
        }
    }

    private fun display(text: String) {
        runOnUiThread {
            text1.text = text
        }
    }
}
