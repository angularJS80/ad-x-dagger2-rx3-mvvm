package msa.client.androidx_dagger2_mvvm_rx3.base.vm

import android.app.Application
import android.os.Bundle

abstract class VmForFm(application: Application) : BaseViewControllerVm(application)
 {
    open fun handleArguments(argument: Bundle) {}
}