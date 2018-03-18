package com.georgistephanov.android.symplflashcards.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.georgistephanov.android.symplflashcards.data.room.dao.DeckDao
import com.georgistephanov.android.symplflashcards.data.room.dao.FlashCardDao
import com.georgistephanov.android.symplflashcards.data.room.entities.Deck
import com.georgistephanov.android.symplflashcards.data.room.entities.FlashCard

@Database(entities = [FlashCard::class, Deck::class], version = 1, exportSchema = false)
abstract class FlashCardsDatabase : RoomDatabase() {

    abstract fun flashCardDao() : FlashCardDao

    abstract fun deckDao() : DeckDao

}