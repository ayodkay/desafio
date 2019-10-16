package com.ayodkay.app.fulllabdesfio.database.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ayodkay.app.fulllabdesfio.database.category.Categories
import com.ayodkay.app.fulllabdesfio.database.category.CategoryDatabase
import com.ayodkay.app.fulllabdesfio.database.category.CategoryRepo
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CategoryRepo
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allCategory: LiveData<List<Categories>>

    init {
        val categoryDao = CategoryDatabase.getDatabase(application, viewModelScope).categoryDao()
        repository = CategoryRepo(categoryDao)
        allCategory = repository.allCategory
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(categories: Categories) = viewModelScope.launch {
        repository.insert(categories)
    }

    fun delete(categories: Categories) = viewModelScope.launch {
        repository.delete(categories)
    }

}
