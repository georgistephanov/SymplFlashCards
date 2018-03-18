package com.georgistephanov.android.symplflashcards.data.room.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation

@Entity(tableName = "Deck")
data class Deck(@PrimaryKey(autoGenerate = true)
                val _id: Int,
                val name: String,
                @Relation(parentColumn = "_id", entityColumn = "deck_id")
                val flashCards: Set<FlashCard>)