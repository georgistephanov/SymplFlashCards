package com.georgistephanov.android.symplflashcards.ui.main

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.georgistephanov.android.symplflashcards.App
import com.georgistephanov.android.symplflashcards.R
import com.georgistephanov.android.symplflashcards.data.DataManager
import com.georgistephanov.android.symplflashcards.data.room.entities.Deck
import com.georgistephanov.android.symplflashcards.data.room.entities.DeckAndCards
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.find

class MainListAdapter(context: Context, val data: List<DeckAndCards>)
    : ArrayAdapter<DeckAndCards>(context, R.layout.adapter_item_main, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row = convertView ?: (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.adapter_item_main, parent, false)

        row.background = if (position % 2 == 0) {
            ContextCompat.getDrawable(context, R.drawable.adapter_row_light)
        } else {
            ContextCompat.getDrawable(context, R.drawable.adapter_row_dark)
        }

        with (row) {
            // Set the stack name
            find<TextView>(R.id.text_stack_name).text = data[position].deck.name

            // Set the amount of flashcards
            val numFlashCards = data[position].cards.size
            find<TextView>(R.id.text_stack_num_cards).text = resources.getQuantityString(R.plurals.amount_flashcards, numFlashCards, numFlashCards)
        }

        return row
    }
}