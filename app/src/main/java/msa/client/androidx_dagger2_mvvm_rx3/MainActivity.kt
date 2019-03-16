package msa.client.androidx_dagger2_mvvm_rx3

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import net.samystudio.beaver.ui.base.activity.At2Toolbar

class MainActivity : At2Toolbar<MainActivityVm>() {
    override val toolbar: Toolbar by lazy { header }
    override val toolbarTitle: TextView? = null
    override val layoutViewRes: Int = R.layout.activity_main
    override val navController: NavController by lazy { findNavController(R.id.nav_host) }
    override val viewModelClass: Class<MainActivityVm> = MainActivityVm::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        // We set launch screen theme from manifest, we need to get back to our Theme to remove
        // launch screen.
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()
}
