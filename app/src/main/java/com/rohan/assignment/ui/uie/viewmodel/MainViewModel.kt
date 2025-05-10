package com.rohan.assignment.ui.uie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rohan.assignment.repositery.ARepositery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor( aRepositery: ARepositery) : ViewModel() {
    val postData = aRepositery.getPostStream().cachedIn(viewModelScope)
}