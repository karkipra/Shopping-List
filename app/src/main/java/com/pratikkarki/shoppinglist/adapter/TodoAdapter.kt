package com.pratikkarki.shoppinglist.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.pratikkarki.shoppinglist.MainActivity
import com.pratikkarki.shoppinglist.R
import com.pratikkarki.shoppinglist.data.AppDatabase
import com.pratikkarki.shoppinglist.data.Todo
import com.pratikkarki.shoppinglist.touch.TodoTouchHelperAdapter

import kotlinx.android.synthetic.main.todo_row.view.*
import java.util.*

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.ViewHolder>, TodoTouchHelperAdapter {

    companion object {
        var food = 0
        var electronics = 0
        var games = 0
        var clothes = 0
        var books = 0
    }


    var todoItems = mutableListOf<Todo>()


    val context : Context

    constructor(context: Context, items: List<Todo>) : super() {
        this.context = context
        this.todoItems.addAll(items)
    }

    constructor(context: Context) : super() {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
                R.layout.todo_row, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todoItems[position]

        holder.tvDate.text = todo.createDate
        holder.cbDone.isChecked = todo.done
        holder.tvItemPrice.text = "$" + todo.itemPrice
        holder.tvDesc.text = todo.itemDesc

        when(todo.itemType){
            "Food" -> {
                holder.imType.setImageResource(R.drawable.food)
                food += todo.itemPrice.toInt()
            }

            "Electronics" -> {
                holder.imType.setImageResource(R.drawable.electronics)
                electronics += todo.itemPrice.toInt()
            }

            "Games" -> {
                holder.imType.setImageResource(R.drawable.games)
                games += todo.itemPrice.toInt()
            }
            "Clothes" -> {
                holder.imType.setImageResource(R.drawable.clothes)
                clothes += todo.itemPrice.toInt()
            }
            "Books" -> {
                holder.imType.setImageResource(R.drawable.books)
                books += todo.itemPrice.toInt()
            }
        }



        holder.btnDelete.setOnClickListener {
            deleteTodo(holder.adapterPosition)
        }

        holder.btnEdit.setOnClickListener {
            (context as MainActivity).showEditTodoDialog(
                    todo, holder.adapterPosition
            )
        }
    }

    private fun deleteTodo(adapterPosition: Int) {

        Thread {
            AppDatabase.getInstance(context).todoDao().deleteTodo(
                    todoItems[adapterPosition]
            )

            itemReducer(adapterPosition)

            todoItems.removeAt(adapterPosition)

            (context as MainActivity).runOnUiThread{
                notifyItemRemoved(adapterPosition)
            }
        }.start()
    }

    private fun itemReduceAll(){
        food = 0
        electronics = 0
        games = 0
        clothes = 0
        books = 0
    }

    private fun itemReducer(adapterPosition: Int) {
        when {
            todoItems[adapterPosition].itemType == "Food" -> {
                food -= todoItems.get(adapterPosition).itemPrice.toInt()
                food = Math.max(food, 0)
            }
            todoItems[adapterPosition].itemType == "Games" -> {
                games -= todoItems.get(adapterPosition).itemPrice.toInt()
                games = Math.max(games, 0)
            }
            todoItems[adapterPosition].itemType == "Electronics" -> {
                electronics -= todoItems.get(adapterPosition).itemPrice.toInt()
                electronics = Math.max(electronics, 0)
            }
            todoItems[adapterPosition].itemType == "Clothes" -> {
                clothes -= todoItems.get(adapterPosition).itemPrice.toInt()
                clothes = Math.max(clothes, 0)
            }
            else -> {
                books -= todoItems.get(adapterPosition).itemPrice.toInt()
                books = Math.max(books, 0)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val tvDate = itemView.tvItemName
        val tvItemPrice = itemView.tvPrice
        val cbDone = itemView.cbPurchased
        val btnDelete = itemView.btnDelete
        val btnEdit = itemView.btnEdit
        val tvDesc = itemView.tvDescription
        val imType = itemView.imItem
    }


    fun addTodo(todo: Todo) {
        todoItems.add(0, todo)
        //notifyDataSetChanged()
        notifyItemInserted(0)
    }

    override fun onDismissed(position: Int) {
        deleteTodo(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(todoItems, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun updateTodo(item: Todo, idx: Int) {
        todoItems[idx] = item
        notifyItemChanged(idx)
    }

    fun removeAll() {
        todoItems.clear()
        itemReduceAll()
        notifyDataSetChanged()
    }

}