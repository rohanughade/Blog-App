package com.rohan.assignment.ui.uie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rohan.assignment.repositery.ARepositery
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val aRepositery: ARepositery) : ViewModel() {
    val postData = aRepositery.getPostStream().cachedIn(viewModelScope)
}