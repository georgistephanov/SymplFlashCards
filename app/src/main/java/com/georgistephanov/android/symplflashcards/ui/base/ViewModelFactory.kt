package com.georgistephanov.android.symplflashcards.ui.base

import android.arch.lifecycle.ViewModel
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Provider
import javax.inject.Singleton


@Singleton
class ViewModelFactory
@Inject constructor(
        private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?:
                creators.asIterable().firstOrNull() { modelClass.isAssignableFrom(it.key) }?.value
                ?: throw IllegalArgumentException("Unknown model class $modelClass")

        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}