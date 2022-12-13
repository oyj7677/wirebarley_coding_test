package com.example.wirebarley.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tordertask.utils.ActivityUtils
import com.example.wirebarley.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragment = supportFragmentManager.findFragmentById(R.id.contentFrame) as MainFragment?
        if(fragment == null) {
            fragment = MainFragment.newInstance()
            ActivityUtils.addFragmentToActivity(supportFragmentManager, fragment, R.id.contentFrame)
        }

        MainPresenter(applicationContext, fragment)
    }
}