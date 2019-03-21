package msa.client.androidx_dagger2_mvvm_rx3.base.vm

import android.app.Application
import androidx.annotation.CallSuper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class Vm(application: Application) : AndroidViewModel(application) {
    //@Inject
    //protected lateinit var application: Application

    protected val compositeDisposable = CompositeDisposable()

    @CallSuper
    override fun onCleared() = compositeDisposable.dispose()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}