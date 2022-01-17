package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.data.ElectionNetworkDataRepository

class RepresentativeViewModelFactory(
    private val networkDataRepository: Lazy<ElectionNetworkDataRepository>
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = RepresentativeViewModel(
        networkDataRepository
    ) as T
}