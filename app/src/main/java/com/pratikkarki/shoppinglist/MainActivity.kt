package com.pratikkarki.shoppinglist

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.pratikkarki.shoppinglist.adapter.TodoAdapter
import com.pratikkarki.shoppinglist.data.AppDatabase
import com.pratikkarki.shoppinglist.data.Todo
import com.pratikkarki.shoppinglist.touch.TodoTouchHelperCallback

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.Date

class MainActivity : AppCompatActivity(), TodoDialog.TodoHandler {

    companion object {
        val KEY_ITEM_TO_EDIT = "KEY_ITEM_TO_EDIT"
    }

    private lateinit var todoAdapter: TodoAdapter
    private var editIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        fabAddTodo.setOnClickListener { view ->
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/

            showAddTodoDialog()
        }

        initRecycleView()
    }

    private fun initRecycleView() {
        Thread {
            val todos = AppDatabase.getInstance(this@MainActivity).todoDao().findAllTodos()

            runOnUiThread {
                todoAdapter = TodoAdapter(this@MainActivity, todos)

                recyclerTodo.adapter = todoAdapter
                val callback = TodoTouchHelperCallback(todoAdapter)
                val touchHelper = ItemTouchHelper(callback)
                touchHelper.attachToRecyclerView(recyclerTodo)
            }
        }.start()
    }

    private fun showAddTodoDialog() {
        TodoDialog().show(supportFragmentManager, "TAG_CREATE")
    }

    public fun showEditTodoDialog(todoToEdit: Todo, idx: Int) {
        editIndex = idx
        val editItemDialog = TodoDialog()

        val bundle = Bundle()
        bundle.putSerializable(KEY_ITEM_TO_EDIT, todoToEdit)
        editItemDialog.arguments = bundle

        editItemDialog.show(supportFragmentManager,
                "EDITITEMDIALOG")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_summary -> {
                var intentStart = Intent()
                intentStart.setClass(this@MainActivity, SummaryActivity::class.java)
                startActivity(intentStart)
            }
            R.id.action_delete -> {
                Thread {
                    AppDatabase.getInstance(this@MainActivity).todoDao().deleteAll()
                    runOnUiThread {
                        todoAdapter.removeAll()
                    }
                }.start()
            }
        }
        return true
    }

    override fun todoCreated(item: Todo) {
        Thread {
            val id = AppDatabase.getInstance(this).todoDao().insertTodo(item)
            item.todoId = id

            runOnUiThread {
                todoAdapter.addTodo(item)
            }
        }.start()
    }

    override fun todoUpdated(item: Todo) {
        val dbThread = Thread {
            AppDatabase.getInstance(this@MainActivity).todoDao().updateTodo(item)

            runOnUiThread { todoAdapter.updateTodo(item, editIndex) }
        }
        dbThread.start()
    }


    override fun onBackPressed() {
        //Toast.makeText(this, "YOU CAN NOT GO BACK", Toast.LENGTH_LONG).show()
        val intentMain = Intent(Intent.ACTION_MAIN)
        intentMain.addCategory(Intent.CATEGORY_HOME)
        intentMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intentMain)
    }

}

