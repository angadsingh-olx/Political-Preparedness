package com.example.android.politicalpreparedness.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.arch.entity.Result
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.domain.LocalDataSource
import com.example.android.politicalpreparedness.network.models.Election

class ElectionLocalDataRepository(private val electionDao: Lazy<ElectionDao>): LocalDataSource {
    private var _savedElectionsLiveData: MutableLiveData<Result<List<Election>>> = MutableLiveData()

    val savedElectionsLiveData: LiveData<Result<List<Election>>>
        get() = _savedElectionsLiveData

    override suspend fun getListOfElections() {
        kotlin.runCatching {
            electionDao.value.getListOfElections()
        }.onSuccess {
            _savedElectionsLiveData.value = Result.Success(it)
        }.onFailure {
            _savedElectionsLiveData.value = Result.Error("Failed to load saved data")
        }
    }

    override suspend fun deleteElection(id: Long) {
        electionDao.value.removeElectionById(id)
    }

    override suspend fun saveElection(election: Election) {
        electionDao.value.saveElection(election)
    }
}