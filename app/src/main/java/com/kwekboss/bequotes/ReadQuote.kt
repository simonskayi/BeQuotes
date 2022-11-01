package com.kwekboss.bequotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ReadQuote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_quote)
        val textViewQoute = findViewById<TextView>(R.id.qoute_txt)
        val textViewAuthor = findViewById<TextView>(R.id.qoute_authot_txt)
        val shareQuote = findViewById<FloatingActionButton>(R.id.share_button)

       // display received data
        textViewQoute.text = intent.getStringExtra("quote")
        textViewAuthor.text = intent.getStringExtra("author")

        //handling share intent
        shareQuote.setOnClickListener {
            val quote = intent.getStringExtra("quote")
             Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra("Share this",quote)
                val chooser = Intent.createChooser(intent,"Share Using...")
                startActivity(chooser)
            }
        }
    }

}