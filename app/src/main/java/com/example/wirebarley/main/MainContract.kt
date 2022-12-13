package com.example.wirebarley.main

import com.example.tordertask.BasePresenter
import com.example.tordertask.BaseView

interface MainContract {
    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter {

    }
}