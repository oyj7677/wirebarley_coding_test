package com.example.wirebarley.main

import com.example.tordertask.BasePresenter
import com.example.tordertask.BaseView

interface MainContract {
    interface View : BaseView<Presenter> {
        fun setInquiryDate(date: String)
        fun setExchangeRate(rate: String, sendCountry: String)
        fun setMsgReceivePayment(payment: String, sendCountry: String)
    }

    interface Presenter : BasePresenter {
        fun calculateExchangeRate(sendCountry: String,  sendPayment: Int)
    }
}