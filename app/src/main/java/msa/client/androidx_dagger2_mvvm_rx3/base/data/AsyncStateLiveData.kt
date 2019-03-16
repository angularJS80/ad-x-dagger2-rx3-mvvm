package msa.client.androidx_dagger2_mvvm_rx3.base.data

import androidx.lifecycle.LiveData
import io.reactivex.Flowable
import io.reactivex.Observable
import msa.client.androidx_dagger2_mvvm_rx3.base.data.AsyncState

class AsyncStateLiveData : LiveData<AsyncState>() {
    fun bind(observable: Observable<AsyncState>): Observable<AsyncState> =
        observable.doOnNext { postValue(it) }

    fun bind(flowable: Flowable<AsyncState>): Flowable<AsyncState> =
        flowable.doOnNext { postValue(it) }
}