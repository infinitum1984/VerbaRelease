package com.emptydev.verba.delete

import android.R
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class DeleteDialog(val context: Context, val endAction: (delete:Boolean) -> Unit) {
    var dialog: AlertDialog
    init {
        dialog= MaterialAlertDialogBuilder(context)
                .setTitle(context.getString(com.emptydev.verba.R.string.delete_dialog_title))
                .setMessage(context.getString(com.emptydev.verba.R.string.delete_dialog_msg)) // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(context.getString(com.emptydev.verba.R.string.yes)) { dialog, which ->
                    // Continue with delete operation
                    endAction.invoke(true)
                } // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(context.getString(com.emptydev.verba.R.string.no), null)
                .setIcon(R.drawable.ic_dialog_alert)
                .create()
    }
    fun show(){
        dialog.show()
    }
}
