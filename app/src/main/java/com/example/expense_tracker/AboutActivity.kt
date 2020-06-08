package com.example.expense_tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Get the Intent that started this activity and extract the string
        val message = intent.getStringExtra("EXTRA_M")

        // Capture the layout's TextView and set the string as its text
        findViewById<TextView>(R.id.textView).apply {
            text = message
        }
    }
}
