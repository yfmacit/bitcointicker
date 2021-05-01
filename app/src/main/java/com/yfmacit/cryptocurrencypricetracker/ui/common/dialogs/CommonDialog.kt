package com.yfmacit.cryptocurrencypricetracker.ui.common.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.yfmacit.cryptocurrencypricetracker.R

class CommonDialog {
    interface DialogOnClickListener : DialogInterface.OnClickListener
    companion object {
        private fun showDialog(context: Context, title: String, message: String, buttonCount: Int,
                               cancellable: Boolean, icon: Int,
                               listener: DialogOnClickListener? = null) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_common)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val lp = WindowManager.LayoutParams()
            val window = dialog.window
            lp.copyFrom(window!!.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = lp
            dialog.setCancelable(cancellable)

            val imageViewIcon = dialog.findViewById(R.id.imageViewIcon) as ImageView
            val textViewTitle = dialog.findViewById(R.id.textViewTitle) as TextView
            val textViewMessage = dialog.findViewById(R.id.textViewMessage) as TextView

            val buttonPositive = dialog.findViewById(R.id.buttonPositive) as Button
            val buttonNegative = dialog.findViewById(R.id.buttonNegative) as Button

            if (icon != -1)
                imageViewIcon.setImageDrawable(ContextCompat.getDrawable(context, icon))
            else
                imageViewIcon.visibility = View.GONE

            textViewTitle.text = title
            textViewMessage.text = message

            if (buttonCount == 1) {
              buttonNegative.visibility = View.GONE
            }

            buttonPositive.setOnClickListener {
                if (listener != null)
                    listener.onClick(dialog, 0)
                else
                    dialog.dismiss()
            }

            buttonNegative.setOnClickListener {
                if (listener != null)
                    listener.onClick(dialog, 1)
                else
                    dialog.dismiss()
            }

            if (!(context as Activity).isFinishing) {
                dialog.show()
            }
        }

        fun showLogoutDialog(context: Context, title: String, message: String,
                             listener: DialogOnClickListener? = null) {
            showDialog(context, title, message, 2,
                false, R.drawable.ic_warning, listener)
        }

        fun showInfoDialog(context: Context, title: String, message: String,
                           listener: DialogOnClickListener? = null) {
            showDialog(context, title, message, 1, false,
                R.drawable.ic_warning, listener)
        }

        fun showSuccessDialog(context: Context, title: String, message: String,
                           listener: DialogOnClickListener? = null) {
            showDialog(context, title, message, 1, false,
                R.drawable.ic_success, listener)
        }

        fun showErrorDialog(context: Context, title: String, message: String,
                            listener: DialogOnClickListener? = null) {
            showDialog(context, title, message, 1, false,
                R.drawable.ic_error, listener)
        }
    }
}