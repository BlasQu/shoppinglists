package com.example.shoppinglists

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.core.content.ContextCompat.getColor
import com.example.shoppinglists.Data.ListItem
import com.example.shoppinglists.Utils.checkTextValid
import kotlinx.android.synthetic.main.dialog_add_item.*
import java.text.SimpleDateFormat
import java.util.*

object AlertDialogs {
    fun dialog_add_item(context: MainActivity) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setCancelable(true)
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

}