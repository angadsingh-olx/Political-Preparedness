package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.arch.ServiceLocator
import com.example.android.politicalpreparedness.data.ElectionLocalDataRepository
import com.example.android.politicalpreparedness.data.ElectionNetworkDataRepository

//TODO: Create Factory to generate ElectionViewModel with provided election datasource
@Suppress("UNCHECKED_CAST")
class ElectionsViewModelFactory(
    val localDataRepository: Lazy<ElectionLocalDataRepository>,
    val networkDataRepository: Lazy<ElectionNetworkDataRepository>
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = ElectionsViewModel(
        localDataRepository, networkDataRepository
    ) as T
}