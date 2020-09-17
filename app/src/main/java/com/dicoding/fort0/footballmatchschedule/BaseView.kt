package com.dicoding.fort0.footballmatchschedule

interface BaseView<T> {

    var presenter: T
    fun showError (message:String?)
}