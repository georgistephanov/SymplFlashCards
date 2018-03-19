package com.georgistephanov.android.symplflashcards.data.room.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "FlashCard",
        foreignKeys = [ForeignKey(entity = Deck::class,
                parentColumns = ["name"], childColumns = ["deck_name"],
                onDelete = CASCADE)])
data class FlashCard(@PrimaryKey(autoGenerate = true)
                     val _id: Int,
                     val front: String,
                     val back: String,
                     val label: String,
                     val color: Int,
                     @ColumnInfo(name = "deck_name")
                     val deckName: String)