package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.arch.entity.Result
import com.example.android.politicalpreparedness.arch.entity.State
import com.example.android.politicalpreparedness.data.ElectionLocalDataRepository
import com.example.android.politicalpreparedness.data.ElectionNetworkDataRepository
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(
    private val electionLocalDataRepository: Lazy<ElectionLocalDataRepository>,
    private val electionNetworkDataRepository: Lazy<ElectionNetworkDataRepository>
): ViewModel() {

    //TODO: Create live data val for upcoming elections
    private var _electionsLiveData = MutableLiveData<List<Election>>()

    val electionsLiveData: LiveData<List<Election>>
        get() = _electionsLiveData

    //TODO: Create live data val for saved elections
    private var _savedElectionsLiveData = electionLocalDataRepository.value.savedElectionsLiveData

    val savedElectionsLiveData: LiveData<Result<List<Election>>>
        get() = _savedElectionsLiveData

    val state: LiveData<State>
        get() = _state
    private val _state = MutableLiveData<State>()

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    fun fetchElectionData() {
        viewModelScope.launch {
            kotlin.runCatching {
                electionNetworkDataRepository.value.getListOfElections()
            }.onSuccess {
                if (it is Result.Success) {
                    _electionsLiveData.value = it.data
                } else {

                }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info
}