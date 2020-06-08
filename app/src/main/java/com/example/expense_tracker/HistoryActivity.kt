package com.example.expense_tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val historyList: ListView = findViewById(R.id.historyList)

        val message = intent.getStringExtra("EXTRA_HISTORY")
        val historyArray = intent.getStringArrayListExtra("EXTRA_ARRAY")

        val historyAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, historyArray)
        historyList.adapter = historyAdapter
        historyAdapter.notifyDataSetChanged()

        findViewById<TextView>(R.id.textView2).apply {
            text = message
        }

    }
}
