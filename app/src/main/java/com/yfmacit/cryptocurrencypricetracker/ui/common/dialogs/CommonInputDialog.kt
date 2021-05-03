package com.yfmacit.cryptocurrencypricetracker.ui.common.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.yfmacit.cryptocurrencypricetracker.R

class CommonInputDialog {
    interface InputDialogInterface {
        fun setValue(value: Int)
    }

    companion object {
        fun showInputDialog(context: Context, message: String, value: Int,
                            listener:InputDialogInterface? = null) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_common_input)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val lp = WindowManager.LayoutParams()
            val window = dialog.window
            lp.copyFrom(window!!.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = lp
            dialog.setCancelable(true)

            val imageViewClose = dialog.findViewById(R.id.imageViewClose) as ImageView
            val textViewMessage = dialog.findViewById(R.id.textViewMessage) as TextView
            val textInputLayout = dialog.findViewById(R.id.textInputLayout) as TextInputLayout
            val textInputEditTextValue = dialog.findViewById(R.id.textInputEditTextValue) as TextInputEditText
            val buttonSet = dialog.findViewById(R.id.buttonSet) as Button

            imageViewClose.setOnClickListener {
                dialog.dismiss()
            }

            textViewMessage.text = message

            textInputEditTextValue.setText(value.toString())
            textInputEditTextValue.requestFocus()
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

            textInputEditTextValue.setOnEditorActionListener { input, actionId, _ ->
                val tempValue = input.text.toString()
                if (validateValue(context, textInputLayout, tempValue)){
                    textInputEditTextValue.clearFocus()
                    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(input.windowToken, 0)

                    listener?.setValue(tempValue.toInt())
                    dialog.dismiss()
                    return@setOnEditorActionListener true
                }
                false
            }

            buttonSet.setOnClickListener {
                val tempValue = textInputEditTextValue.text.toString()
                if (validateValue(context, textInputLayout, tempValue)) {
                    listener?.setValue(tempValue.toInt())
                    dialog.dismiss()
                }
            }

            if (!(context as Activity).isFinishing) {
                dialog.show()
            }
        }

        private fun validateValue(context: Context, inputLayout: TextInputLayout,
                                  tempValue: String?): Boolean{
            return if (tempValue != null && tempValue != "" && tempValue.toInt() > 0) {
                true
            } else {
                inputLayout.error = context.resources.getString(R.string.dialog_value_error_hint)
                false
            }
        }
    }

}