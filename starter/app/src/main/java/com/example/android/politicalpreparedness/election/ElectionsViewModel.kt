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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//DONE: Construct ViewModel and provide election datasource
class ElectionsViewModel(
    private val electionLocalDataRepository: Lazy<ElectionLocalDataRepository>,
    private val electionNetworkDataRepository: Lazy<ElectionNetworkDataRepository>
): ViewModel() {

    //DONE: Create live data val for upcoming elections
    private var _electionsLiveData = MutableLiveData<List<Election>>()

    val electionsLiveData: LiveData<List<Election>>
        get() = _electionsLiveData

    //DONE: Create live data val for saved elections
    private var _savedElectionsLiveData = MutableLiveData<List<Election>>()

    val savedElectionsLiveData: LiveData<List<Election>>
        get() = _savedElectionsLiveData

    val state: LiveData<State>
        get() = _state
    private val _state = MutableLiveData<State>()

    //DONE: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    fun fetchElectionData() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(State.LOADING)
            kotlin.runCatching {
                electionNetworkDataRepository.value.getListOfElections()
            }.onSuccess {
                if (it is Result.Success) {
                    _state.postValue(State.SUCCESS)
                    _electionsLiveData.postValue(it.data)
                } else {
                    it as Result.Error
                    _state.postValue(State.ERROR(it.message))
                }
            }.onFailure {
                _state.postValue(State.ERROR("Failed to load data"))
                it.printStackTrace()
            }
        }
    }

    fun fetchSavedData() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                electionLocalDataRepository.value.getListOfElections()
            }.onSuccess {
                if (it is Result.Success) {
                    _state.postValue(State.SUCCESS)
                    _savedElectionsLiveData.postValue(it.data)
                } else {
                    it as Result.Error
                    _state.postValue(State.ERROR(it.message))
                }
            }.onFailure {
                _state.postValue(State.ERROR("Unknown Error"))
                it.printStackTrace()
            }
        }
    }

    //DONE: Create functions to navigate to saved or upcoming election voter info

}