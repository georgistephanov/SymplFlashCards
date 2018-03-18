package com.georgistephanov.android.symplflashcards.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.georgistephanov.android.symplflashcards.data.room.entities.FlashCard

@Dao
interface FlashCardDao {

    @Query("SELECT * FROM FlashCard WHERE _id = :flashCardId")
    fun getFlashCard(flashCardId: Int) : FlashCard

    @Insert
    fun insert(flashCard: FlashCard)

    @Delete
    fun delete(flashCard: FlashCard)

    @Query("DELETE FROM FlashCard WHERE _id = :flashCardId")
    fun delete(flashCardId: Int)

}