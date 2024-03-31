package com.mismayilov.core.utils

import androidx.recyclerview.widget.DiffUtil

class MyDiffUtil<T>(private val itemsTheSame: (oldItem: T, newItem: T) -> Boolean,
                    private val contentsTheSame: (oldItem: T, newItem: T) -> Boolean) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        return itemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        return contentsTheSame(oldItem, newItem)
    }


}