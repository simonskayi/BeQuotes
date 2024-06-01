package com.kwekboss.bequotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kwekboss.bequotes.adapter.QuotesAdapter
import com.kwekboss.bequotes.model.Quotes
import com.kwekboss.bequotes.utils.Constants.SAVED_INSTANCE_kEY

class MainActivity : AppCompatActivity(), QuotesAdapter.QuoteInterface {
    private lateinit var recyclerView: RecyclerView
    private lateinit var quotesAdapter: QuotesAdapter
    private lateinit var progressBar: ProgressBar
    var quoteList: ArrayList<Quotes> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progresBar)
        quotesAdapter = QuotesAdapter(this)

        initRecyclerView()
        showProgressBar()

        if (savedInstanceState == null) {
            getQuotes()
        }

        else {
         quoteList = savedInstanceState.getParcelableArrayList(SAVED_INSTANCE_kEY) ?: arrayListOf()
            quotesAdapter.differ.submitList(quoteList)
            hideProgressBar()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(SAVED_INSTANCE_kEY, quoteList)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        savedInstanceState?.let {
            quoteList = it.getParcelableArrayList(SAVED_INSTANCE_kEY) ?: ArrayList()
            quotesAdapter.differ.submitList(quoteList)
        }
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

    private fun getQuotes() {
        val database = FirebaseDatabase.getInstance().getReference("Quotes")
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (quotesSnapshot in snapshot.children) {
                        val allQuotes = quotesSnapshot.getValue(Quotes::class.java)
                        quoteList.add(allQuotes!!)
                    }

                    quoteList.shuffle()

                    //display on recyclerview
                    quotesAdapter.differ.submitList(quoteList)
                }
                hideProgressBar()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
                hideProgressBar()
            }

        })

    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}
