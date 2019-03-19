package msa.client.androidx_dagger2_mvvm_rx3.authentication.vm

import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import msa.client.androidx_dagger2_mvvm_rx3.authentication.AuthenticatorUserFlow
import msa.client.androidx_dagger2_mvvm_rx3.authentication.repo.AuthenticatorRepositoryManager
import msa.client.androidx_dagger2_mvvm_rx3.authentication.repo.UserManager
import msa.client.androidx_dagger2_mvvm_rx3.authentication.webapi.AuthenticatorApiInterface
import msa.client.androidx_dagger2_mvvm_rx3.base.data.AsyncStateLiveData
import msa.client.androidx_dagger2_mvvm_rx3.base.vm.VmForFm
import msa.client.androidx_dagger2_mvvm_rx3.base.data.AsyncState
import msa.client.androidx_dagger2_mvvm_rx3.base.data.AsyncStateLiveDataDefine
import msa.client.androidx_dagger2_mvvm_rx3.base.ext.getClassTag
import msa.client.androidx_dagger2_mvvm_rx3.base.helper.SharedPreferencesHelper
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit



/*
import net.samystudio.beaver.data.AsyncState
import net.samystudio.beaver.data.manager.AuthenticatorRepositoryManager
import net.samystudio.beaver.data.manager.UserManager
import net.samystudio.beaver.di.scope.FragmentScope
import net.samystudio.beaver.ext.getClassTag
import net.samystudio.beaver.ui.base.viewmodel.BaseFragmentViewModel
import net.samystudio.beaver.ui.base.viewmodel.DataPushViewModel
import net.samystudio.beaver.ui.common.navigation.NavigationRequest
import net.samystudio.beaver.ui.common.viewmodel.AsyncStateLiveData
*/


//@FragmentScope
class AuthenticatorFmVm(application: Application)// @Inject
// constructor(private val authenticatorRepositoryManager: AuthenticatorRepositoryManager)
    : VmForFm(application), AsyncStateLiveDataDefine
    {
    private val _context:Context ? = getApplication();

    private val accountManager: AccountManager = AccountManager.get(_context)
    private val sharedPreferences: SharedPreferences =  PreferenceManager.getDefaultSharedPreferences(_context)
    private val rxSharedPreferences: RxSharedPreferences = RxSharedPreferences.create(sharedPreferences)

    private val sharedPreferencesHelper: SharedPreferencesHelper= SharedPreferencesHelper(rxSharedPreferences)

    private val  userManager:UserManager = UserManager(accountManager,sharedPreferencesHelper)


// 레토르핏 통해서 구현체 가져와야...
    private   val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost")
            //.client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
     private val authenticatorApiInterface: AuthenticatorApiInterface = retrofit.create(AuthenticatorApiInterface::class.java!!)


    private val authenticatorRepositoryManager: AuthenticatorRepositoryManager = AuthenticatorRepositoryManager(userManager,authenticatorApiInterface)
    private val _asyncStateLiveData: AsyncStateLiveData = AsyncStateLiveData()
    override val liveData: LiveData<AsyncState> =
        AsyncStateLiveData()//_asyncStateLiveData
    private val _signInVisibility: MutableLiveData<Boolean> = MutableLiveData()
    private val _signUpVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val signInVisibility: LiveData<Boolean> = _signInVisibility
    val signUpVisibility: LiveData<Boolean> = _signUpVisibility
    private var authenticatorResponse: AccountAuthenticatorResponse? = null
    private lateinit var intent: Intent

   override fun handleIntent(intent: Intent) { super.handleIntent(intent)

        this.intent = intent

        authenticatorResponse =
                intent.getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE)
        authenticatorResponse?.onRequestContinued()

        //_signInVisibility.value = !intent.hasExtra(UserManager.KEY_CREATE_ACCOUNT)
        //_signUpVisibility.value = !intent.hasExtra(UserManager.KEY_CONFIRM_ACCOUNT)
    }

    fun <T : AuthenticatorUserFlow> addUserFlow(observable: Observable<T>) {
        addDisposable(

            observable.flatMap { userFlow ->
            when (userFlow) {


                is AuthenticatorUserFlow.SignIn ->
                    _asyncStateLiveData.bind(
                        authenticatorRepositoryManager.signIn(userFlow.email, userFlow.password)
                    ).observeOn(AndroidSchedulers.mainThread())
                        .doOnNext {
                            if (it is AsyncState.Completed) handleSignResult(
                                userFlow.email,
                                userFlow.password
                            )
                        }
                is AuthenticatorUserFlow.SignUp ->
                    _asyncStateLiveData.bind(
                        authenticatorRepositoryManager.signUp(userFlow.email, userFlow.password)
                    ).observeOn(AndroidSchedulers.mainThread())
                        .doOnNext {
                            if (it is AsyncState.Completed) handleSignResult(
                                userFlow.email,
                                userFlow.password
                            )
                        }.zipWith(Observable.just(""), BiFunction { t1, _ -> t1 })
                else -> Observable.error<AsyncState> {
                    IllegalArgumentException("Unknown user flow ${userFlow.getClassTag()}.")
                }


            }
        }.subscribe())
    }

    private fun handleSignResult(email: String, password: String) {
        /*authenticatorResponse?.onResult(
            bundleOf(
                AccountManager.KEY_ACCOUNT_NAME to email,
                AccountManager.KEY_ACCOUNT_TYPE to UserManager.ACCOUNT_TYPE,
                AccountManager.KEY_PASSWORD to password
            )
        )

        authenticatorResponse = null
        navigate(NavigationRequest.Pop())*/
    }

    /*override fun onCleared() {
        super.onCleared()

        authenticatorResponse?.onError(
            AccountManager.ERROR_CODE_CANCELED,
            "Authentication was cancelled"
        )
        authenticatorResponse = null

        intent.removeExtra(UserManager.KEY_CREATE_ACCOUNT)
        intent.removeExtra(UserManager.KEY_CONFIRM_ACCOUNT)
    }*/
}