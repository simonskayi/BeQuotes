package com.kwekboss.bequotes

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kwekboss.bequotes.adapter.QuotesAdapter
import com.kwekboss.bequotes.model.Quotes

class MainActivity : AppCompatActivity(), QuotesAdapter.QuoteInterface {
    private lateinit var recyclerView: RecyclerView
    private lateinit var quotesAdapter: QuotesAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progresBar)
        quotesAdapter = QuotesAdapter(this)

        initRecyclerView()

        showProgressBar()

       //Get Quotes from firebase database
        getQuotes()

    }


    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = quotesAdapter
        }
    }

    // sending data to the Read Quotes activity
    override fun clickQuotes(quotes: Quotes) {
        val intent = Intent(this, ReadQuote::class.java)
        intent.apply {
            putExtra("quote", quotes.quote)
            putExtra("author", quotes.author)
            startActivity(intent)
        }
    }

    private fun getQuotes(): ArrayList<Quotes> {

        val quotes = arrayListOf<Quotes>()
        val database = FirebaseDatabase.getInstance().getReference("Quotes")
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (quotesSnapshot in snapshot.children) {
                        val allQuotes = quotesSnapshot.getValue(Quotes::class.java)
                        quotes.add(allQuotes!!)
                    }
                    //Shuffle Quotes
                    quotes.shuffle()

                    // get quotes from firebase
                    quotesAdapter.differ.submitList(quotes)
                }
                hideProgressBar()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
                hideProgressBar()
            }

        })
        return quotes
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

   private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}
