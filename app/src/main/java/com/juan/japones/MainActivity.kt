package com.juan.japones

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.juan.japones.fragments.MainFragment

private var fragmentCount = 0

class MainActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportActionBar.apply {
            title = "                          ${resources.getString(R.string.app_name)}      ".toUpperCase()
        }

        val fragment = MainFragment.getInstance(applicationContext)
        supportFragmentManager.beginTransaction().add(R.id.constraint_base_layout, fragment).addToBackStack("base").commit()

    }

    override fun onBackPressed() {

        fragmentCount = supportFragmentManager.backStackEntryCount

        if ( fragmentCount == 1)
        {
            fragmentCount -= 1
            finish()
        }
        else
        {
            supportFragmentManager.popBackStackImmediate()
            fragmentCount -= 1
        }
    }
}
