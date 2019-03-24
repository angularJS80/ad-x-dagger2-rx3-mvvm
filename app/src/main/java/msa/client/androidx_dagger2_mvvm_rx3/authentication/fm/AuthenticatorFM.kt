@file:Suppress("ProtectedInFinal")

package msa.client.androidx_dagger2_mvvm_rx3.authentication.fm

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_authenticator.*
import msa.client.androidx_dagger2_mvvm_rx3.R
import msa.client.androidx_dagger2_mvvm_rx3.authentication.entity.MemberSignIn
import msa.client.androidx_dagger2_mvvm_rx3.authentication.entity.MemberSignUp
import msa.client.androidx_dagger2_mvvm_rx3.base.fm.Fm4LiveData
import msa.client.androidx_dagger2_mvvm_rx3.authentication.vm.AuthenticatorFmVm
/*
import net.samystudio.beaver.R
import net.samystudio.beaver.data.local.SharedPreferencesHelper
import net.samystudio.beaver.ext.EmailValidator
import net.samystudio.beaver.ext.PasswordValidator
import net.samystudio.beaver.ext.getGenericErrorDialog
import net.samystudio.beaver.ext.getMethodTag
import net.samystudio.beaver.ui.base.fragment.BaseDataPushFragment
import java.util.concurrent.TimeUnit
*/

// 총 4단계의 걸친 추상 상속 프래그먼트를 상속 받는 프래그 먼트
// 다이어 로그 기능과 데거 인젝트가 가능하고 라이프 사이클 관리가 되고 4단계 라이프 데이터에서 막힘..
// 프래그먼트 대 뷰모델을 제너릭으로 구현

      //클레스    - > fm4 부모 (내가 사용할 VM 클래스를 넘겨줌 )
class AuthenticatorFM : Fm4LiveData<AuthenticatorFmVm>() {
    override val layoutViewRes: Int = R.layout.fragment_authenticator
    override val viewModelClass: Class<AuthenticatorFmVm> = AuthenticatorFmVm::class.java



    override var title: String? = "Authenticator"
    private var disposables: CompositeDisposable? = null
    /*
    @Inject
    protected lateinit var emailValidator: EmailValidator
    @Inject
    protected lateinit var passwordValidator: PasswordValidator
    @Inject
    protected lateinit var sharedPreferencesHelper: SharedPreferencesHelper*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //sign_in_email.setText(sharedPreferencesHelper.accountName.get())

        viewModel.signInVisibility.observe(this, Observer { sign_in_layout.isVisible = it })
        viewModel.signUpVisibility.observe(this, Observer { sign_up_layout.isVisible = it })

        viewModel.singInClickSubscriber(signInClicksPublisher())
        viewModel.signUpClickSubscriber(signUpClicksPublisher())
        /*
               disposables = CompositeDisposable()

               disposables?.add(
                   Observables
                       .combineLatest(
                           sign_in_email.textChanges()
                               .debounce(500, TimeUnit.MILLISECONDS)
                               .observeOn(AndroidSchedulers.mainThread())
                               .map { t ->
                                   val emailValid = emailValidator.validate(t)
                                   sign_in_email_layout.error =
                                           if (!t.isEmpty() && !emailValid) "Invalid email" else null
                                   emailValid
                               },
                           sign_in_password.textChanges()
                               .debounce(500, TimeUnit.MILLISECONDS)
                               .observeOn(AndroidSchedulers.mainThread())
                               .map { t ->
                                   val passwordValid = passwordValidator.validate(t)
                                   sign_in_password_layout.error =
                                           if (!t.isEmpty() && !passwordValid) "Invalid password (minimum 8 chars)" else null
                                   passwordValid
                               }
                       ) { t1, t2 -> t1 && t2 }
                       .observeOn(AndroidSchedulers.mainThread())
                       .startWith(false)
                       .subscribe { sign_in.isEnabled = it })

               disposables?.add(
                   Observables
                       .combineLatest(
                           sign_up_email.textChanges()
                               .debounce(500, TimeUnit.MILLISECONDS)
                               .observeOn(AndroidSchedulers.mainThread())
                               .map { t ->
                                   val emailValid = emailValidator.validate(t)
                                   sign_up_email_layout.error =
                                           if (!t.isEmpty() && !emailValid) "Invalid email" else null
                                   emailValid
                               },
                           sign_up_password.textChanges()
                               .debounce(500, TimeUnit.MILLISECONDS)
                               .observeOn(AndroidSchedulers.mainThread())
                               .map { t ->
                                   val passwordValid = passwordValidator.validate(t)
                                   sign_up_password_layout.error =
                                           if (!t.isEmpty() && !passwordValid) "Invalid password (minimum 8 chars)" else null
                                   passwordValid
                               },
                           sign_up_confirm_password.textChanges()
                               .debounce(500, TimeUnit.MILLISECONDS)
                               .observeOn(AndroidSchedulers.mainThread())
                               .map { t ->
                                   val password = sign_up_password.text.toString()
                                   val passwordMatchValid = t.toString() == password
                                   sign_up_confirm_password_layout.error =
                                           if (passwordValidator.validate(password) && !passwordMatchValid) "Passwords don't match" else null
                                   passwordMatchValid
                               }
                       ) { t1, t2, t3 -> t1 && t2 && t3 }
                       .observeOn(AndroidSchedulers.mainThread())
                       .startWith(false)
                       .subscribe { sign_up.isEnabled = it })*/
    }

          // 버튼 클릭 이벤트 발생기
          private fun signUpClicksPublisher(): Observable<MemberSignUp> {
              return sign_up.clicks()
                  .map {
                      MemberSignUp(
                          sign_up_email.text.toString(),
                          sign_up_password.text.toString()
                      )
                  }
          }

          // 버튼 클릭 이벤트 발생기
          private fun signInClicksPublisher(): Observable<MemberSignIn> {
              return sign_in.clicks()
                  .map {
                      MemberSignIn(
                          sign_in_email.text.toString(),
                          sign_in_password.text.toString(),
                          sign_in_email.text.toString()
                      )
                  }
          }

          override fun dataPushStart() {
        // TODO show loader
    }

    override fun dataPushSuccess() {
    }

    override fun dataPushError(throwable: Throwable) {
        fragmentManager?.let {
            //getGenericErrorDialog(context!!).showNow(it, getMethodTag())
        }
    }

    override fun dataPushTerminate() {
        // TODO hide loader
    }

    override fun onDestroyView() {
        super.onDestroyView()

        disposables?.dispose()
    }
}