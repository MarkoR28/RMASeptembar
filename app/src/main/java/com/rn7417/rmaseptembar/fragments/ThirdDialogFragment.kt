package com.rn7417.rmaseptembar.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.rn7417.rmaseptembar.R

class ThirdDialogFragment : DialogFragment() {

    internal lateinit var listener: NoticeDialogListener3
    interface NoticeDialogListener3 {
        fun onDialogSelect3(index: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NoticeDialogListener3
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Crust selection")
                .setItems(
                    R.array.pizzaCrusts,
                    DialogInterface.OnClickListener { dialog, which ->
                        listener.onDialogSelect3(which.toString())
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}