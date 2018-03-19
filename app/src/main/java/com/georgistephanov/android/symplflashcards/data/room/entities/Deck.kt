package com.georgistephanov.android.symplflashcards.data.room.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Deck(@PrimaryKey val name: String)