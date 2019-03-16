package msa.client.androidx_dagger2_mvvm_rx3.base.vm

import android.os.Bundle

abstract class VmForFm : BaseViewControllerVm()
 {
    open fun handleArguments(argument: Bundle) {}
}