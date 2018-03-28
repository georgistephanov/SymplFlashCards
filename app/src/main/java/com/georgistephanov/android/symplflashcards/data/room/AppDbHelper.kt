package com.georgistephanov.android.symplflashcards.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Room
import android.content.Context
import com.georgistephanov.android.symplflashcards.data.room.dao.DeckDao
import com.georgistephanov.android.symplflashcards.data.room.dao.FlashCardDao
import com.georgistephanov.android.symplflashcards.data.room.entities.Deck
import com.georgistephanov.android.symplflashcards.data.room.entities.DeckAndCards
import com.georgistephanov.android.symplflashcards.data.room.entities.FlashCard
import com.georgistephanov.android.symplflashcards.di.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDbHelper @Inject constructor(@ApplicationContext context: Context) : DbHelper {

    private val database: FlashCardsDatabase
    private val deckDao: DeckDao
    private val flashCardDao: FlashCardDao

    init {
        database = Room.databaseBuilder(context, FlashCardsDatabase::class.java, "SymplCards")
                .build()

        deckDao = database.deckDao()
        flashCardDao = database.flashCardDao()
    }

    override fun getFlashCard(id: Int) : FlashCard?
            = flashCardDao.getFlashCard(id)

    override fun getAllFlashCardsFromDeck(deckName: String): LiveData<List<FlashCard>>
            = flashCardDao.getAllFlashCardsFromDeck(deckName)

    override fun insertFlashCard(flashCard: FlashCard) {
        flashCardDao.insert(flashCard)
    }

    override fun updateFlashCard(flashCard: FlashCard) {
        flashCardDao.update(flashCard)
    }

    override fun deleteFlashCard(flashCard: FlashCard) {
        flashCardDao.delete(flashCard)
    }

    override fun deleteFlashCard(id: Int) {
        flashCardDao.delete(id)
    }

    override fun getAllDecks(): LiveData<List<DeckAndCards>> = deckDao.getDecks()

    override fun getDeck(name: String) : LiveData<DeckAndCards> = deckDao.getDeck(name)

    override fun insertDeck(deck: Deck) {
        deckDao.insert(deck)
    }

    override fun deleteDeck(deck: Deck) {
        deckDao.delete(deck)
    }

    override fun deleteDeck(name: String) {
        deckDao.delete(name)
    }
}