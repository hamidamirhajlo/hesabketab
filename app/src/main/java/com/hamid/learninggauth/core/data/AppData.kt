package com.hamid.learninggauth.core.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class AppData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val total: String,
    val cost: String,
    val day: Int,
    val month: Int,
    val year: Int
)