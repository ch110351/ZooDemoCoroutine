package com.example.zoointro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val title = MutableLiveData<String>()
    val actionBarTitle: LiveData<String>
        get() = title

    fun updateActionBarTitle(title: String) {
        this.title.postValue(title)
    }
}