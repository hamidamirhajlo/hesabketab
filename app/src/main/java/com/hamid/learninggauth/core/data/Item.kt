package com.hamid.learninggauth.core.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String,
    var total: String,
    var cost: String,
    var income: String,
    var forr: String? = "",
    var comment: String? = "",
    var date: Long? = Date().time
)