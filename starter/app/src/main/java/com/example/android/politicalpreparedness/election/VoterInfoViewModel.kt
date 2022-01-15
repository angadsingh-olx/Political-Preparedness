package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.arch.entity.Result
import com.example.android.politicalpreparedness.arch.entity.State
import com.example.android.politicalpreparedness.data.ElectionLocalDataRepository
import com.example.android.politicalpreparedness.data.ElectionNetworkDataRepository
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.launch

class VoterInfoViewModel(
    private val electionLocalDataRepository: Lazy<ElectionLocalDataRepository>,
    private val electionNetworkDataRepository: Lazy<ElectionNetworkDataRepository>
) : ViewModel() {

    //TODO: Add live data to hold voter info
    private var _voterInfoLiveData = MutableLiveData<VoterInfoResponse>()

    val voterInfoLiveData: LiveData<VoterInfoResponse>
        get() = _voterInfoLiveData

    val state: LiveData<State>
        get() = _state
    private val _state = MutableLiveData<State>()

    //TODO: Add var and methods to populate voter info
    fun fetchElectionData(division: Division, id: Long) {
        viewModelScope.launch {
            _state.value = State.LOADING
            kotlin.runCatching {
                val address = listOf(division.state, division.country)
                    .filterNot { it.isBlank() }
                    .joinToString(separator = ",")
                electionNetworkDataRepository.value.getVotersInfo(address, id)
            }.onSuccess {
                if (it is Result.Success) {
                    _state.value = State.SUCCESS
                    _voterInfoLiveData.value = it.data
                } else {
                    it as Result.Error
                    _state.value = State.ERROR(it.message)
                }
            }.onFailure {
                _state.value = State.ERROR("Unknown Error")
                it.printStackTrace()
            }
        }
    }

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}