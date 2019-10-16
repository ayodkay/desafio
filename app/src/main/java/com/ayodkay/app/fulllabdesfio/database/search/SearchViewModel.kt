package com.ayodkay.app.fulllabdesfio.database.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SearchRepo
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allCategory: LiveData<List<Search>>

    init {
        val categoryDao = SearchDatabase.getDatabase(application, viewModelScope).searchDao()
        repository = SearchRepo(categoryDao)
        allCategory = repository.allSearch
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(categories: Search) = viewModelScope.launch {
        repository.insert(categories)
    }

    fun delete(categories: Search) = viewModelScope.launch {
        repository.delete(categories)
    }

}