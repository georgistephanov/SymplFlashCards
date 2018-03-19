package com.georgistephanov.android.symplflashcards.data.room.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class DeckAndCards() {
    @Embedded
    lateinit var deck: Deck

    @Relation(parentColumn = "name", entityColumn = "deck_name")
    lateinit var cards: List<FlashCard>
}