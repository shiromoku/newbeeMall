package com.shiromoku.newbeeMall.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class HomeFragmentMainListView(private val views:Array<View>):BaseAdapter() {
    override fun getCount(): Int {
        return views.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return views[position]
    }
}