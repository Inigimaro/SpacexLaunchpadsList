package com.jakubworoniecki.autentidemo.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchpadViewModel @Inject constructor(
    launchpadRepository: LaunchpadRepository
) : ViewModel() {
    val isDataLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    fun changeIsLoading(isLoading: Boolean) {
        viewModelScope.launch {
            isDataLoading.postValue(isLoading)
        }
    }
    val launchpads = launchpadRepository.getLaunchpads().cachedIn(viewModelScope)
}