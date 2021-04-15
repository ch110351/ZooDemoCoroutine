package com.example.zoointro.widget


import android.view.View

interface ItemClick<T> {
    fun onClicked(view: View, t: T)
}