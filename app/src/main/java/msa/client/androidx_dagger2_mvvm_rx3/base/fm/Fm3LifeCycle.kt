package msa.client.androidx_dagger2_mvvm_rx3.base.fm

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModelProvider
import msa.client.androidx_dagger2_mvvm_rx3.base.vm.VmForFm

/*
import net.samystudio.beaver.di.qualifier.FragmentContext
import net.samystudio.beaver.ext.navigate
import net.samystudio.beaver.ui.base.viewmodel.BaseFragmentViewModel
*/


// 뷰모델을 제너릭으로 받아 들이는 추상 프래그먼트 클레스
// 뷰모델에 따른 프래그먼트의 공통 상태 관리구현
abstract class Fm3LifeCycle<VM : VmForFm> : Fm2Dagger(),
    DialogInterface.OnShowListener {
    /**
     * @see [net.samystudio.beaver.di.module.FirebaseModule.provideFirebaseAnalytics]
     */
    //@Inject
    //final override lateinit var firebaseAnalytics: FirebaseAnalytics
    /**
     * @see [net.samystudio.beaver.ui.base.fragment.BaseViewModelFragmentModule.provideViewModelProvider]
     */
    //@Inject
    //@field:FragmentContext
    protected lateinit var viewModelProvider: ViewModelProvider
    protected abstract val viewModelClass: Class<VM>
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*
        viewModel = viewModelProvider.get(viewModelClass)
        viewModel.handleCreate()
        activity?.intent?.let { viewModel.handleIntent(it) }
        arguments?.let { viewModel.handleArguments(it) }
        */
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        viewModel.navigationCommand.observe(
            viewLifecycleOwner,
            Observer { request ->
                request?.let {
                    navController.navigate(it, activity, fragmentManager)
                }
            })

        viewModel.resultEvent.observe(viewLifecycleOwner, Observer { result ->
            result?.let {
                setResult(it.code, it.intent)
                if (it.finish)
                    finish()
            }
        })
        */
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

//        savedInstanceState?.let { viewModel.handleRestoreInstanceState(it) }
    }

    override fun onNewIntent(intent: Intent) {
//        viewModel.handleIntent(intent)

        super.onNewIntent(intent)
    }

    @CallSuper
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //      viewModel.handleResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()

        //viewModel.handleReady()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        //viewModel.handleSaveInstanceState(outState)
    }
}
