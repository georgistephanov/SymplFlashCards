package com.georgistephanov.android.symplflashcards.di.module

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import android.arch.lifecycle.ViewModelProvider
import com.georgistephanov.android.symplflashcards.di.ViewModelKey
import com.georgistephanov.android.symplflashcards.ui.base.ViewModelFactory
import com.georgistephanov.android.symplflashcards.ui.deck.DeckViewModel
import com.georgistephanov.android.symplflashcards.ui.main.MainViewModel
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DeckViewModel::class)
    internal abstract fun bindDeckViewModel(viewModel: DeckViewModel): ViewModel
}