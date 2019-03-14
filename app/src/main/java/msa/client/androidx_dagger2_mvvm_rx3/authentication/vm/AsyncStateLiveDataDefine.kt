package msa.client.androidx_dagger2_mvvm_rx3.authentication.vm

import androidx.lifecycle.LiveData
import msa.client.androidx_dagger2_mvvm_rx3.base.data.AsyncState

interface AsyncStateLiveDataDefine {
    val liveData: LiveData<AsyncState>
}