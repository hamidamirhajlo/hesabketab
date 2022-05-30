package com.hamid.learninggauth.core.data.repo

import androidx.lifecycle.LiveData
import com.hamid.learninggauth.Application
import com.hamid.learninggauth.core.data.Item
import com.hamid.learninggauth.core.data.local.AppDao
import com.hamid.learninggauth.core.data.local.AppDatabase
import org.json.JSONObject

class Repository(appDatabase: AppDatabase, private val app: Application) {

    private val appDao: AppDao = appDatabase.dao()

    suspend fun insert(item: Item) = appDao.insert(item)
    suspend fun delete(itemId: Int) = appDao.delete(itemId)
    fun readAll(): LiveData<List<Item>> = appDao.readAll()

    //    suspend fun update(item: Item) = appDao.update(item)
    suspend fun update(item: Item) =
        appDao.update(item.title, item.total, item.cost, item.income, item.forr!!, item.comment!!)

    fun getItemById(id: Int): LiveData<Item> = appDao.getItemById(id)

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