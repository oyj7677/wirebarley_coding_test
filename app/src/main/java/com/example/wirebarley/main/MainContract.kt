package com.example.wirebarley.main

import com.example.tordertask.BasePresenter
import com.example.tordertask.BaseView

interface MainContract {
    interface View : BaseView<Presenter> {
        fun setInquiryDate(date: String)
        fun setExchangeRate(rate: String, sendCountry: String)
        fun setMsgReceivePayment(payment: String, sendCountry: String)
        fun updateCurrencyData(index: Int)
        fun getSendPayment(): Int
        fun errDialogAndFinish(title: String, errMsg: String)
    }

    interface Presenter : BasePresenter {
        fun calculateExchangeRate()
        fun compositeDisposableClear()
        fun setSendCountry(index: Int)
        fun getSendCountry(): String
        fun getExchangedPayment(sendPayment: Int): String
        fun getCurrencyRate(index: Int): String
    }
}