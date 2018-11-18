package com.pratikkarki.shoppinglist.touch

interface TodoTouchHelperAdapter {
    fun onDismissed(position: Int)
    fun onItemMoved(fromPosition: Int, toPosition: Int)
}