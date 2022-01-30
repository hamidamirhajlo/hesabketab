package com.hamid.learninggauth.core.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hamid.learninggauth.core.data.AppData

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(appData: AppData)

    @Query("SELECT * FROM appdata")
    fun readAll(): LiveData<List<AppData>>

    @Query("SELECT * FROM appdata  ")
    fun readByDate(): LiveData<List<AppData>>

    @Update
    suspend fun update(appData: AppData)

    @Delete
    suspend fun delete(appData: AppData)

    @Query("SELECT * FROM appdata WHERE id == :itemId ")
    fun getItemById(itemId: Int): LiveData<AppData>


//    @Query("SELECT MAX(date) AS maxdate ")
//    fun greaterYear(): LiveData<Int>


}