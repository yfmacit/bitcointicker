package com.yfmacit.cryptocurrencypricetracker.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.yfmacit.cryptocurrencypricetracker.data.model.app.enums.AppEnums
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>, K: BaseDelegate> : Fragment(), BaseNavigator{
    var baseActivity: BaseActivity<*, *>? = null
        private set
    var delegate:K? = null
    private var mRootView: View? = null
    var viewDataBinding: T? = null
        private set
    private var mViewModel: V? = null
    private var fragmentTag: String? = null
    abstract val bindingVariable: Int
    @get:LayoutRes
    abstract val layoutId: Int
    abstract val viewModel: V
    lateinit var mFragmentNavigation: FragmentNavigation


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            this.baseActivity = activity
        }

        if (context is FragmentNavigation) {
            mFragmentNavigation = context
        }

        if (context is BaseDelegate) {
            delegate = context as K
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    interface FragmentNavigation {
        fun pushFragment(fragment: Fragment)
        fun popFragment(depth: Int)
    }

    override fun showLoading() {
        delegate?.showLoading()
    }

    override fun hideLoading() {
        delegate?.hideLoading()
    }

    override fun showMessage(
        type: AppEnums.MessageStatus,
        title: String,
        message: String,
        positiveHandler: (() -> Unit)?,
        negativeHandler: (() -> Unit)?
    ) {
        delegate?.showMessage(type, title, message, positiveHandler, negativeHandler)
    }
}