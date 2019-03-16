package net.samystudio.beaver.ui.base.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.ui.setupActionBarWithNavController
import msa.client.androidx_dagger2_mvvm_rx3.base.vm.VmForAt

abstract class At2Toolbar<VM : VmForAt> : At1LifeCycle<VM>() {
    protected abstract val toolbar: Toolbar

    /**
     * Optional toolbar title view if action bar provided view can't match our needs. This view
     * will fade in/out when text change.
     */
    protected abstract val toolbarTitle: TextView?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        setupActionBarWithNavController(navController)
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)

        // try to fade in/out title if possible
        toolbarTitle?.let {

            if (it.text == title) return

            it.animate().cancel()

            if (title.isNullOrBlank()) {
                hideTitle()?.setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        it.text = ""
                    }
                })
            } else {
                if (it.text.isNullOrBlank()) {
                    it.text = title
                    showTitle()
                } else {
                    hideTitle()?.setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            it.text = title
                            showTitle()
                        }
                    })
                }
            }
        }
    }

    private fun showTitle() =
        toolbarTitle
            ?.animate()
            ?.alpha(1.0f)

    private fun hideTitle() =
        toolbarTitle
            ?.animate()
            ?.alpha(0.0f)
}