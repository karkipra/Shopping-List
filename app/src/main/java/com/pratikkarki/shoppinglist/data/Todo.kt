package com.pratikkarki.shoppinglist.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todo")
data class Todo(
        @PrimaryKey(autoGenerate = true) var todoId: Long?,
        @ColumnInfo(name = "createdate") var createDate: String,
        @ColumnInfo(name = "done") var done: Boolean,
        @ColumnInfo(name = "todo text") var todoText: String,
        @ColumnInfo(name = "item price") var itemPrice: String,
        @ColumnInfo(name = "item description") var itemDesc: String,
        @ColumnInfo(name = "item type") var itemType: String
) : Serializable