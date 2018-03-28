package com.georgistephanov.android.symplflashcards.data.room.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.georgistephanov.android.symplflashcards.data.room.entities.FlashCard

@Dao
interface FlashCardDao {

    @Query("SELECT * FROM FlashCard WHERE _id = :flashCardId")
    fun getFlashCard(flashCardId: Int) : FlashCard?

    @Query("SELECT * FROM FlashCard WHERE deck_name=:deckName")
    fun getAllFlashCardsFromDeck(deckName: String) : LiveData<List<FlashCard>>

    @Update
    fun update(flashCard: FlashCard)

    @Insert
    fun insert(flashCard: FlashCard)

    @Delete
    fun delete(flashCard: FlashCard)

    @Query("DELETE FROM FlashCard WHERE _id = :flashCardId")
    fun delete(flashCardId: Int)

}