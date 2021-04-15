package com.example.zoointro.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.zoointro.R
import com.example.zoointro.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.venueListFragment
            ), drawer_layout
        )
        navController = findNavController(R.id.nav_host_fragment)
        navController.setGraph(R.navigation.main_nav_graph)
        setupActionBarWithNavController(navController)
        toolbar.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener { controller, destination: NavDestination, arguments ->
            val isTopLevelDestination =
                appBarConfiguration.topLevelDestinations.contains(destination.id)
            toolbar.setNavigationIcon(
                if (isTopLevelDestination) R.drawable.ic_menu
                else R.drawable.ic_arrow_back_black_24dp
            )
        }
        //update action bar title
        mainViewModel.actionBarTitle.observe(this, Observer {
            toolbar.title = it
        })

    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(
            this,
            R.id.nav_host_fragment
        ).navigateUp()
}