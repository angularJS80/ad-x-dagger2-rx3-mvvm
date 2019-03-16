package msa.client.androidx_dagger2_mvvm_rx3

import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Intent
import msa.client.androidx_dagger2_mvvm_rx3.base.vm.VmForAt

/*
import net.samystudio.beaver.di.scope.ActivityScope
import net.samystudio.beaver.ui.base.viewmodel.BaseActivityViewModel
import net.samystudio.beaver.ui.common.navigation.NavigationRequest
*/

//@ActivityScope
class MainActivityVm //@Inject constructor()
    : VmForAt() {
    private var hasAuthenticatorResponse: Boolean = false

    override fun handleIntent(intent: Intent) {
        super.handleIntent(intent)

        hasAuthenticatorResponse = intent.getParcelableExtra<AccountAuthenticatorResponse>(
            AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE
        ) != null
    }

    override fun handleReady() {
        super.handleReady()

        // If we got authenticatorResponse that mean we were ask from system to create
        // account, so let's notify we want to authenticate.
        //if (hasAuthenticatorResponse) navigate(NavigationRequest.Push(R.id.action_global_authenticator))
    }
}