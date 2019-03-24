package msa.client.androidx_dagger2_mvvm_rx3.authentication.repo

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import msa.client.androidx_dagger2_mvvm_rx3.authentication.entity.MemberSignIn
import msa.client.androidx_dagger2_mvvm_rx3.authentication.entity.MemberSignUp
import msa.client.androidx_dagger2_mvvm_rx3.base.data.AsyncState
import msa.client.androidx_dagger2_mvvm_rx3.authentication.webapi.AuthenticatorApiInterface


/**
 * @see [net.samystudio.beaver.di.module.NetworkModule.provideAuthenticatorApiInterface]
 */
//@Singleton
class AuthenticatorRepositoryManager
//@Inject
constructor(
    private val userManager: UserManager,
    private val authenticatorApiInterface: AuthenticatorApiInterface
) {
    fun signIn(memberSignIn: MemberSignIn): Observable<AsyncState> {
        authenticatorApiInterface
            .postLogin(memberSignIn)
            .subscribeOn(Schedulers.newThread())
            .onErrorReturn {  }

            .onErrorReturn {


            }.subscribe(postLoginObserver())

        return authenticatorApiInterface
            .signIn(memberSignIn.email, memberSignIn.password)
            .toObservable()
            //.onErrorReturnItem("token") // TODO remove this line, for debug only
            .map {
                println(it);
                //userManager.connect(memberSignIn.email, memberSignIn.password, it)
                AsyncState.Completed
            }
            .cast(AsyncState::class.java)
            //.observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .onErrorReturn {
                it.printStackTrace()
                AsyncState.Failed(it)

            }
            .startWith(AsyncState.Started)
    }

    fun postLoginObserver(): DisposableObserver<Any> {
        return object : DisposableObserver<Any>() {
            override fun onComplete() {
                println("onComplete")
            }

            override fun onNext(t: Any) {
                println(t)
            }

            override fun onError(e: Throwable) {
                println(e)
            }


        }
    }


    fun signUp(memberSignUp: MemberSignUp): Observable<AsyncState> =
        authenticatorApiInterface
            .signUp(memberSignUp.email, memberSignUp.password)
            .toObservable()
            .onErrorReturnItem("token") // TODO remove this line, for debug only
            .map {
                userManager.createAccount(memberSignUp.email, memberSignUp.password)
                AsyncState.Completed
            }
            .cast(AsyncState::class.java)
            .onErrorReturn { AsyncState.Failed(it) }
            .startWith(AsyncState.Started)
}