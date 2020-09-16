package com.rn7417.rmaseptembar.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.rn7417.rmaseptembar.R

class SecondDialogFragment : DialogFragment() {

    internal lateinit var listener: NoticeDialogListener
    interface NoticeDialogListener {
        fun onDialogSelect(index: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Pizza size")
                .setItems(
                    R.array.pizzaSizes,
                    DialogInterface.OnClickListener { dialog, which ->
                        listener.onDialogSelect(which.toString())
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}