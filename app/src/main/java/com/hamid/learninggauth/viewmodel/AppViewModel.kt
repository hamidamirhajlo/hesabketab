package com.hamid.learninggauth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamid.learninggauth.core.data.repo.Repository
import com.hamid.learninggauth.core.data.AppData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AppViewModel(private val repository: Repository) : ViewModel() {


    fun insert(appData: AppData) = viewModelScope.launch(IO) { repository.insert(appData) }

    fun readAll(): LiveData<List<AppData>> = repository.readAll()

    fun update(appData: AppData) = viewModelScope.launch(IO) { repository.update(appData) }

    fun delete(appData: AppData) = viewModelScope.launch(IO) { repository.delete(appData) }

    fun getItemById(id: Int): LiveData<AppData> = repository.getItemById(id)

    fun filterByDate(from: Long, until: Long): LiveData<List<AppData>> {
        return repository.filterByDate(from, until)
    }


}
