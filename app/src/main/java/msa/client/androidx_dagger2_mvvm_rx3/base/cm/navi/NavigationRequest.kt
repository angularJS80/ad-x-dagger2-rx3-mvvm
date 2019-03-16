package msa.client.androidx_dagger2_mvvm_rx3.base.cm.navi

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import msa.client.androidx_dagger2_mvvm_rx3.base.fm.Fm1Dialog

sealed class NavigationRequest {
    class Pop(
        @param:IdRes @get:IdRes val destinationId: Int? = null,
        val inclusive: Boolean = false
    ) : NavigationRequest()

    class PopToRoot(val inclusive: Boolean = false) : NavigationRequest()
    class Push(
        @param:IdRes @get:IdRes val destinationId: Int,
        val args: Bundle? = null,
        val options: NavOptions? = null,
        val extras: Navigator.Extras? = null
    ) : NavigationRequest()

    class Dialog(val destination: Fm1Dialog, val tag: String? = null) : NavigationRequest()
    object Finish : NavigationRequest()
}