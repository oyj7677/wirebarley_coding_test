package com.example.wirebarley.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

open class ActivityUtils {

    companion object{
        fun addFragmentToActivity(fragmentManager: FragmentManager, fragment: Fragment, frameId: Int) {
            val transaction = fragmentManager.beginTransaction()
            transaction.add(frameId, fragment)
            transaction.commit()
        }
    }
}