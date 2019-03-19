package msa.client.androidx_dagger2_mvvm_rx3.authentication.repo

import io.reactivex.Observable
import msa.client.androidx_dagger2_mvvm_rx3.base.data.AsyncState
import msa.client.androidx_dagger2_mvvm_rx3.authentication.webapi.AuthenticatorApiInterface
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @see [net.samystudio.beaver.di.module.NetworkModule.provideAuthenticatorApiInterface]
 */
@Singleton
class AuthenticatorRepositoryManager
//@Inject
constructor(
    private val userManager: UserManager,
    private val authenticatorApiInterface: AuthenticatorApiInterface
) {
    fun signIn(email: String, password: String): Observable<AsyncState> =
        authenticatorApiInterface
            .signIn(email, password)
            .toObservable()
            .onErrorReturnItem("token") // TODO remove this line, for debug only
            .map {
                userManager.connect(email, password, it)
                AsyncState.Completed
            }
            .cast(AsyncState::class.java)
            .onErrorReturn { AsyncState.Failed(it) }
            .startWith(AsyncState.Started)

    fun signUp(email: String, password: String): Observable<AsyncState> =
        authenticatorApiInterface
            .signUp(email, password)
            .toObservable()
            .onErrorReturnItem("token") // TODO remove this line, for debug only
            .map {
                userManager.createAccount(email, password)
                AsyncState.Completed
            }
            .cast(AsyncState::class.java)
            .onErrorReturn { AsyncState.Failed(it) }
            .startWith(AsyncState.Started)
}