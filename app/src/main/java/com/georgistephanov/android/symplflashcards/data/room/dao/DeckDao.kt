package com.georgistephanov.android.symplflashcards.data.room.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.georgistephanov.android.symplflashcards.data.room.entities.Deck
import com.georgistephanov.android.symplflashcards.data.room.entities.DeckAndCards

@Dao
interface DeckDao {

    @Query("SELECT * FROM Deck")
    fun getDecks() : LiveData<List<DeckAndCards>>

    @Query("SELECT * FROM Deck WHERE name = :name")
    fun getDeck(name: String) : LiveData<DeckAndCards>

    @Insert
    fun insert(deck: Deck)

    @Delete
    fun delete(deck: Deck)

    @Query("DELETE FROM Deck WHERE name = :name")
    fun delete(name: String)

}