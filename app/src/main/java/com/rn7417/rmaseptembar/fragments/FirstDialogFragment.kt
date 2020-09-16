package com.rn7417.rmaseptembar.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class FirstDialogFragment : DialogFragment() {

    internal lateinit var listener: NoticeDialogListener1
    interface NoticeDialogListener1 {
        fun onOkPressed(index: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NoticeDialogListener1
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Next step is to pick your pizza size.\n Select one size that you prefer.\n After selecting, you will be given option to select your desired crust.\n Enjoy")
                .setPositiveButton("Ok",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onOkPressed(id.toString())
                    })
                .setTitle("How to order")
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}