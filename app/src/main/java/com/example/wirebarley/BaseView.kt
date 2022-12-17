package com.example.wirebarley

interface BaseView<T> {
    fun setPresenter(presenter: T)
}