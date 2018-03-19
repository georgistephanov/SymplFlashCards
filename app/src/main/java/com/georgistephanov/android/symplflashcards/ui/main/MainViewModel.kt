package com.georgistephanov.android.symplflashcards.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.georgistephanov.android.symplflashcards.App
import com.georgistephanov.android.symplflashcards.data.room.entities.Deck
import com.georgistephanov.android.symplflashcards.data.room.entities.DeckAndCards
import com.georgistephanov.android.symplflashcards.di.ApplicationContext
import javax.inject.Inject

class MainViewModel @Inject constructor(@ApplicationContext applicationContext: Context) : ViewModel() {

    private val dataManager = (applicationContext as App).component.getDataManager()

    val decks: LiveData<List<DeckAndCards>> = dataManager.getAllDecks()

}