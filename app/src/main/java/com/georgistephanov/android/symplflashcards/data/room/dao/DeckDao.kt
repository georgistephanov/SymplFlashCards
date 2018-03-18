package com.georgistephanov.android.symplflashcards.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.georgistephanov.android.symplflashcards.data.room.entities.Deck

@Dao
interface DeckDao {

    @Query("SELECT * FROM Deck WHERE _id = :deckId")
    fun getDeck(deckId: Int) : Deck

    @Insert
    fun insert(deck: Deck)

    @Delete
    fun delete(deck: Deck)

    @Query("DELETE FROM Deck WHERE _id = :deckId")
    fun delete(deckId: Int)

}