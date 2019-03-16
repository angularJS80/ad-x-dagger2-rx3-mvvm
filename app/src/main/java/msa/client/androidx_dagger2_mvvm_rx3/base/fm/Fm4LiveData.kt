package msa.client.androidx_dagger2_mvvm_rx3.base.fm

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import msa.client.androidx_dagger2_mvvm_rx3.base.data.AsyncStateLiveDataDefine
import msa.client.androidx_dagger2_mvvm_rx3.base.vm.VmForFm
import msa.client.androidx_dagger2_mvvm_rx3.base.data.AsyncState

// 추상클래스로 용도는 프래그먼트 전역에서 사용 목적을 둔다.
// 프래그먼트에서 사용될 뷰모델을 제너릭 으로 받고 제너릭 뷰모델에 상태 관리 메소드를 구현해놨다.
// 상속 및 인터페이스로
abstract class Fm4LiveData<VM> :
    Fm3LifeCycle<VM>() where VM : VmForFm, VM : AsyncStateLiveDataDefine {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.liveData.observe(viewLifecycleOwner, Observer { requestState ->
            requestState?.let {
                when (it) {
                    is AsyncState.Started -> dataPushStart()
                    is AsyncState.Completed -> {
                        dataPushSuccess()
                        dataPushTerminate()
                    }
                    is AsyncState.Failed -> {
                        dataPushError(it.error)
                        dataPushTerminate()
                    }
                }
            }
        })
    }

    protected abstract fun dataPushStart()
    protected abstract fun dataPushSuccess()
    protected abstract fun dataPushError(throwable: Throwable)
    protected abstract fun dataPushTerminate()
}
