package com.example.shoppinglists.Utils

import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import com.example.shoppinglists.Data.ListDetails
import com.example.shoppinglists.Data.ListItem
import com.example.shoppinglists.MainActivity
import com.example.shoppinglists.R
import kotlinx.android.synthetic.main.dialog_add_details.*
import kotlinx.android.synthetic.main.dialog_add_item.*
import kotlinx.android.synthetic.main.dialog_add_item.btn_cancel
import kotlinx.android.synthetic.main.dialog_add_item.btn_confirm
import java.text.SimpleDateFormat
import java.util.*

object AlertDialogs {
    fun dialog_add_item(context: MainActivity) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setCancelable(false)
            setView(R.layout.dialog_add_item)
        }
        val dialog = builder.create()
        dialog.show()

        dialog.window!!.setBackgroundDrawable(ColorDrawable(context.resources.getColor(android.R.color.transparent)))

        dialog.btn_confirm.setOnClickListener { if (checkTextValid.add_item_valid(dialog.input_title.text.toString())) {
            val format = SimpleDateFormat("MM/DD", Locale.US).format(Calendar.getInstance().time)
            context.viewmodel.insertList(ListItem(0, dialog.input_title.text.toString(), format.toString()))
            dialog.dismiss()
        }
        else dialog.input_title.error = "Field must not be empty and have less than 25 characters." }

        dialog.btn_cancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun dialog_add_details(context: MainActivity) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setCancelable(false)
            setView(R.layout.dialog_add_details)
        }
        val dialog = builder.create()
        dialog.show()

        dialog.item_amount.minValue = 1
        dialog.item_amount.maxValue = 20

        dialog.window!!.setBackgroundDrawable(ColorDrawable(context.resources.getColor(android.R.color.transparent)))

        dialog.btn_confirm.setOnClickListener { if (checkTextValid.add_item_valid(dialog.input_item.text.toString())) {
            val data: ListItem = context.viewmodel.data.value!![context.viewmodel.currentDetails]
            val currentList: MutableList<ListDetails> = data.details.toMutableList()
            val itemAtBeginning = mutableListOf(ListDetails(0, dialog.input_item.text.toString(), dialog.item_amount.value, false))
            itemAtBeginning.addAll(currentList)

            context.viewmodel.updateList(ListItem(data.itemId, data.title, data.date, itemAtBeginning))
            dialog.dismiss()
        }
        else dialog.input_item.error = "Field must not be empty and have less than 25 characters." }

        dialog.btn_cancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun dialog_confirm_delete(context: MainActivity, items: List<ListItem>) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setCancelable(false)
            setView(R.layout.dialog_confirm_delete)
        }
        val dialog = builder.create()
        dialog.show()

        dialog.window!!.setBackgroundDrawable(ColorDrawable(context.resources.getColor(android.R.color.transparent)))

        val itemsToDelete = mutableListOf<ListItem>()
        itemsToDelete.addAll(items)

        dialog.btn_confirm.setOnClickListener {
            //itemsToDelete.forEach { item -> context.viewmodel.deleteList(item) }
            context.viewmodel.deleteList(itemsToDelete)
            context.viewmodel.selectedItems2.postValue(0)
            context.viewmodel.result.postValue(true)
            dialog.dismiss()
        }

        dialog.btn_cancel.setOnClickListener {
            context.viewmodel.result.postValue(false)
            dialog.dismiss()
        }
    }

    fun delete_details(context: MainActivity, items: List<ListDetails>, item: ListItem) {

        val itemsToDelete = items
        val newItem = ListItem(item.itemId, item.title, item.date, itemsToDelete, false)
        context.viewmodel.updateList(newItem)

    }
}