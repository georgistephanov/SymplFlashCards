package com.georgistephanov.android.symplflashcards.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val flashCardStacks: MutableLiveData<List<MainListRow>> = MutableLiveData()

    init {
        flashCardStacks.value = listOf(
                MainListRow("Uni", 6),
                MainListRow("Didi", 18),
                MainListRow("Design patterns", 12))
    }
}