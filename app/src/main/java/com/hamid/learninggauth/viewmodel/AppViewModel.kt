package com.hamid.learninggauth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamid.learninggauth.core.data.repo.Repository
import com.hamid.learninggauth.core.data.Item
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AppViewModel(private val repository: Repository) : ViewModel() {


    fun insert(item: Item) = viewModelScope.launch(IO) { repository.insert(item) }

    fun readAll(): LiveData<List<Item>> = repository.readAll()

    fun update(item: Item) = viewModelScope.launch(IO) { repository.update(item) }

    fun delete(itemId: Int) = viewModelScope.launch(IO) { repository.delete(itemId) }

    fun getItemById(id: Int): LiveData<Item> = repository.getItemById(id)

    fun filterByDate(from: Long, until: Long): LiveData<List<Item>> {
        return repository.filterByDate(from, until)
    }


}
