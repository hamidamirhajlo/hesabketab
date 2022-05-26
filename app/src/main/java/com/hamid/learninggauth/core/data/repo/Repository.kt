package com.hamid.learninggauth.core.data.repo

import androidx.lifecycle.LiveData
import com.hamid.learninggauth.Application
import com.hamid.learninggauth.core.data.AppData
import com.hamid.learninggauth.core.data.local.AppDao
import com.hamid.learninggauth.core.data.local.AppDatabase
import org.json.JSONObject

class Repository(appDatabase: AppDatabase,private val app:Application) {

    private val appDao: AppDao = appDatabase.dao()

    suspend fun insert(appData: AppData) = appDao.insert(appData)
    suspend fun delete(appData: AppData) = appDao.delete(appData)
    fun readAll(): LiveData<List<AppData>> = appDao.readAll()
    suspend fun update(appData: AppData) = appDao.update(appData)
    fun getItemById(id: Int): LiveData<AppData> = appDao.getItemById(id)

    fun filterByDate(startDate: Long, endDate: Long) = appDao.filterByDate(startDate, endDate)

//    fun greaterYear(): Int = appDao.greaterYear()

    fun nerkhJsonObject(): JSONObject {
        val text: String
        val inputStream = app.resources.assets.open("list.json")

        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()

        text = String(buffer, Charsets.UTF_8)
        return JSONObject(text)

    }
}