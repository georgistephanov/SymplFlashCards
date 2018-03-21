package com.georgistephanov.android.symplflashcards.ui.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.georgistephanov.android.symplflashcards.R
import com.georgistephanov.android.symplflashcards.data.room.entities.Deck
import com.georgistephanov.android.symplflashcards.data.room.entities.DeckAndCards
import org.jetbrains.anko.find

class DeckRecyclerViewAdapter(
        val context: Context,
        val data: List<DeckAndCards>,
        private val fragmentInteractionListener: DeckListFragment.OnListFragmentInteractionListener?
    ) : RecyclerView.Adapter<DeckRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_item_main, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            dataItem = data[position]

            deckName.text = data[position].deck.name
            numCards.text = context.resources.getQuantityString(
                    R.plurals.amount_flashcards,
                    data[position].cards.size,
                    data[position].cards.size
            )

            row.setOnClickListener {
                if (holder.dataItem != null && fragmentInteractionListener != null) {
                    fragmentInteractionListener
                            .onListFragmentInteraction(it, (holder.dataItem as DeckAndCards).deck.name)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        var dataItem: DeckAndCards? = null

        val deckName by lazy { row.find<TextView>(R.id.text_deck_name) }
        val numCards by lazy { row.find<TextView>(R.id.text_deck_num_cards) }
    }
}