package com.kwekboss.bequotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kwekboss.bequotes.adapter.QuotesAdapter
import com.kwekboss.bequotes.model.Quotes

class MainActivity : AppCompatActivity(), QuotesAdapter.QuoteInterface {
    private lateinit var recyclerView: RecyclerView
    private lateinit var quotesAdapter:QuotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        quotesAdapter = QuotesAdapter(this)
        initRecyclerView()
    }



    private fun initRecyclerView(){
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = quotesAdapter
        }
    }

    // sending data to the next activity
    override fun clickQuotes(quotes: Quotes) {
       val intent = Intent(this,ReadQuote::class.java)
        intent.apply {
            putExtra("quote",quotes.quotes)
            putExtra("author",quotes.author)
            startActivity(intent)
        }
    }
}