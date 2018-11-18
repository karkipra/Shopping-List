package com.pratikkarki.shoppinglist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.*
import com.pratikkarki.shoppinglist.data.Todo
import kotlinx.android.synthetic.main.dialog_todo.view.*
import java.lang.RuntimeException

class TodoDialog : DialogFragment() {

    interface TodoHandler {
        fun todoCreated(item: Todo)
        fun todoUpdated(item: Todo)
    }

    private lateinit var todoHandler: TodoHandler

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is TodoHandler) {
            todoHandler = context
        } else {
            throw RuntimeException("The Activity does not implement the TodoHandler Interface")
        }
    }

    private lateinit var etItemName: EditText
    private lateinit var etTodoText: EditText
    private lateinit var cbPurchased: CheckBox
    private lateinit var etItemPrice: EditText
    private lateinit var etItemDesc: EditText
    private lateinit var spItemType: Spinner
    private lateinit var itemTypeFromSpinner: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("New Item")

        val rootView = requireActivity().layoutInflater.inflate(
                R.layout.dialog_todo, null
        )
        //etTodoText = rootView.findViewById(R.id.etTodoText)
        etItemName = rootView.etItemName
        etTodoText = rootView.etItemPrice
        cbPurchased = rootView.cbPurchased
        etItemPrice = rootView.etItemPrice
        etItemDesc = rootView.etItemDescription
        spItemType = rootView.spItemTypes

        builder.setView(rootView)

        spItemType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                itemTypeFromSpinner = "Food"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                itemTypeFromSpinner = parent?.getItemAtPosition(position).toString()
            }

        }

        val arguments = this.arguments
        if (arguments != null && arguments.containsKey(MainActivity.KEY_ITEM_TO_EDIT)) {
            val todoItem = arguments.getSerializable(
                    MainActivity.KEY_ITEM_TO_EDIT
            ) as Todo //Case for Edits
            etItemName.setText(todoItem.createDate)
            etTodoText.setText(todoItem.todoText)
            etItemPrice.setText(todoItem.itemPrice)
            etItemDesc.setText(todoItem.itemDesc)

            builder.setTitle("Edit Item")
        }

        builder.setPositiveButton("OK") { dialog, witch ->

        }

        return builder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (etItemName.text.isNotEmpty()) {
                val arguments = this.arguments
                if (arguments != null && arguments.containsKey(MainActivity.KEY_ITEM_TO_EDIT)) {
                    handleTodoEdit()
                } else {
                    handleTodoCreate()
                }

                dialog.dismiss()
            } else {
                etItemName.error = "This field can not be empty"
            }
        }
    }

    private fun handleTodoCreate() {
        todoHandler.todoCreated(
                Todo(
                        null,
                        etItemName.text.toString(),
                        cbPurchased.isChecked,
                        "Purchased",
                        etItemPrice.text.toString(),
                        etItemDesc.text.toString(),
                        itemTypeFromSpinner

                )
        )
    }

    private fun handleTodoEdit() {
        val todoToEdit = arguments?.getSerializable(
                MainActivity.KEY_ITEM_TO_EDIT
        ) as Todo
        todoToEdit.createDate = etItemName.text.toString()
        todoToEdit.todoText = etTodoText.text.toString()
        todoToEdit.done = cbPurchased.isChecked

        todoHandler.todoUpdated(todoToEdit)
    }

}