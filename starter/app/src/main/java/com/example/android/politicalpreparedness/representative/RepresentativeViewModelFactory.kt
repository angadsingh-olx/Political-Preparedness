package com.example.android.politicalpreparedness.representative

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.data.ElectionNetworkDataRepository

class RepresentativeViewModelFactory(
    private val networkDataRepository: Lazy<ElectionNetworkDataRepository>,
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = RepresentativeViewModel(
        networkDataRepository, application
    ) as T
}