package com.hamid.learninggauth.core.adapter

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyLinearLayoutManager(context: Context): LinearLayoutManager(context) {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
        super.onLayoutChildren(recycler, state)
        }catch (e:IndexOutOfBoundsException){
            println("MyLinearLayoutManager.onLayoutChildren: ${e.localizedMessage}")
        }
    }
}