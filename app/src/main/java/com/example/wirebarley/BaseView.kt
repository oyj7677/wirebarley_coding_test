package com.example.tordertask

interface BaseView<T> {
    fun setPresenter(presenter: T)
}