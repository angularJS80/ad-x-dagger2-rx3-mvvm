package msa.client.androidx_dagger2_mvvm_rx3.base.fm

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


// 앞서 정의한 기본 프레그먼트를 상속받고
// 데거 인텍트를 통해 프레그먼트를 주입 받기위해 해즈서퍼트 프레그먼트 인젝터 인터페이스 를 구현한다.
// 이클레스를 상속받아 구현한 프래그먼트는 인젝트 사용이 허용된다. 
abstract class Fm2Dagger : Fm1Dialog()
    //,HasSupportFragmentInjector
    {
    //@Inject
    //protected lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>
/*
    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return childFragmentInjector
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
    */
}
