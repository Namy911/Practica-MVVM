package com.example.practica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practica.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                    .replace(R.id.container_display, MainFragment.newInstance())
//                    .commitNow()
//        }

    }
}
