package com.rn7417.rmaseptembar.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class CheckoutFragment : DialogFragment() {

    internal lateinit var listener: NoticeDialogListenerCh
    interface NoticeDialogListenerCh {
        fun onOkPressedCheckout(index: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NoticeDialogListenerCh
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Your order will be ready in about 15 minutes!\n Enjoy!")
                .setPositiveButton("Ok",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onOkPressedCheckout(id.toString())
                    })
                .setTitle("Thank you for your order!")
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}