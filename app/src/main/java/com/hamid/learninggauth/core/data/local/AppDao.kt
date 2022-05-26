package com.hamid.learninggauth.core.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hamid.learninggauth.core.data.Item

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Query("SELECT * FROM item ORDER BY date ASC")
    fun readAll(): LiveData<List<Item>>

    @Query("SELECT * FROM item  ")
    fun readByDate(): LiveData<List<Item>>

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * FROM item WHERE id == :itemId ")
    fun getItemById(itemId: Int): LiveData<Item>


    @Query("SELECT * FROM item WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun filterByDate(startDate: Long, endDate: Long): LiveData<List<Item>>

//    @Query("SELECT MAX(date) AS maxdate ")
//    fun greaterYear(): LiveData<Int>


}