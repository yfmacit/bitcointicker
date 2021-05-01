package com.yfmacit.cryptocurrencypricetracker.ui.base

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.data.model.app.enums.AppEnums
import com.yfmacit.cryptocurrencypricetracker.ui.common.dialogs.CommonDialog
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(), BaseDelegate, BaseNavigator {
    private var loader : RelativeLayout? = null
    var dataBinding: T? = null
        private set
    private var mViewModel: V? = null
    abstract val viewModel : V
    abstract val bindingVariable: Int
    @get:LayoutRes
    abstract val layoutId: Int

    @Inject
    lateinit var dataManager: DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
        dataBinding!!.setVariable(bindingVariable, mViewModel)
        dataBinding!!.executePendingBindings()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun showMessage(
        type: AppEnums.MessageStatus,
        title: String,
        message: String,
        positiveHandler: (() -> Unit)?,
        negativeHandler: (() -> Unit)?
    ) {
        when (type) {
            AppEnums.MessageStatus.INFO -> {
                CommonDialog.showInfoDialog(this@BaseActivity, title, message)
            }
            AppEnums.MessageStatus.SUCCESS -> {
                CommonDialog.showSuccessDialog(this@BaseActivity, title, message)
            }
            AppEnums.MessageStatus.ERROR -> {
                CommonDialog.showErrorDialog(this@BaseActivity, title, message)
            }
            AppEnums.MessageStatus.LOGOUT -> {
                CommonDialog.showLogoutDialog(this@BaseActivity, title, message,
                object : CommonDialog.DialogOnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        when(which) {
                            0 -> positiveHandler?.invoke()
                            1 -> negativeHandler?.invoke()
                        }
                        dialog?.cancel()
                    }
                })
            }
        }
    }

    override fun logout() {

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val view = currentFocus
        val result = super.dispatchTouchEvent(event)

        if (view is EditText) {
            val mView = currentFocus
            val screcoords = IntArray(2)
            mView!!.getLocationOnScreen(screcoords)
            val x = event.rawX + mView.left - screcoords[0]
            val y = event.rawY + mView.top - screcoords[1]

            if (event.action == MotionEvent.ACTION_UP && (x < mView.left || x >= mView.right || y < mView.top || y > mView.bottom)) {
                hideKeyboard()
                mView.clearFocus()
            }
        }
        return result
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(window.currentFocus!!.windowToken, 0)
        }
    }
}