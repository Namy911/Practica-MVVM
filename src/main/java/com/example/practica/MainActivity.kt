package com.example.practica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
//
//        // Config from arrow navigate up
        findNavController(R.id.nav_host).let {nav ->
            appBarConfig = AppBarConfiguration(nav.graph)
            setupActionBarWithNavController(nav, appBarConfig)
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(findNavController(R.id.nav_host), appBarConfig)
    }
}
