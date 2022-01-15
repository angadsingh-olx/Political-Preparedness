package com.example.android.politicalpreparedness.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.domain.NetworkDataSource
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.arch.entity.Result

class ElectionNetworkDataRepository(private val apiService: CivicsApiService): NetworkDataSource {

//    private var _electionsLiveData: MutableLiveData<Result<List<Election>>> = MutableLiveData()

//    val electionsLiveData: LiveData<Result<List<Election>>>
//        get() = _electionsLiveData

    override suspend fun getListOfElections(): Result<List<Election>> {
        val response = apiService.getElectionsFromNetwork()
        return if (response.isSuccessful) {
            if (response.body() != null) {
                Result.Success(response.body()!!.elections)
            } else {
                Result.Error("No Elections Found", response.code())
            }
        } else {
            Result.Error("Failed to load data", response.code())
        }
    }
}