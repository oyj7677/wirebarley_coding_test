package com.example.wirebarley.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wirebarley.R

class MainFragment : Fragment(), MainContract.View {

    companion object {
        fun newInstance(): MainFragment = MainFragment()
        private const val TAG = "SignUpFragment"
    }

    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }
}