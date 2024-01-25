package com.kwekboss.bequotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kwekboss.bequotes.R
import com.kwekboss.bequotes.model.Quotes

class QuotesAdapter(private val _quotesInterface:QuoteInterface): RecyclerView.Adapter<QuotesAdapter.ViewHolder>() {

   private val differCallBack = object: DiffUtil.ItemCallback<Quotes>() {
        override fun areItemsTheSame(oldItem: Quotes, newItem: Quotes): Boolean {
            return oldItem.author == newItem.author
        }

        override fun areContentsTheSame(oldItem: Quotes, newItem: Quotes): Boolean {
            return oldItem==newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val layout = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_child_layout,parent,false)
               return ViewHolder(layout,_quotesInterface)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(differ.currentList[position])
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    inner class ViewHolder(itemView:View, quoteInterface: QuoteInterface): RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            quoteInterface.clickQuotes(differ.currentList[absoluteAdapterPosition])
        }
    }

        fun bindView(quotes: Quotes){
        val mainQuote = itemView.findViewById<TextView>(R.id.txt_qoute)
        val quoteAuthor =itemView.findViewById<TextView>(R.id.txt_aut)

        mainQuote.text = quotes.quote
        quoteAuthor.text = quotes.author
    }
    }

    interface QuoteInterface{
        fun clickQuotes(quotes: Quotes)
    }

}