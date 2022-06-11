package com.hamid.learninggauth.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hamid.learninggauth.R
import com.najva.sdk.Najva
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHostFragment.findNavController()

        bottomNavigationMain.setupWithNavController(navController)


        readNotification(intent)

    }

    //get json from from notification
    private fun readNotification(intent: Intent) {
        intent.let {
            val data = intent.getStringExtra(Najva.NOTIFICATION_JSON)
            println(data)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            readNotification(intent)
        }
    }
}