package com.example.android.politicalpreparedness.domain

import com.example.android.politicalpreparedness.arch.entity.Result
import com.example.android.politicalpreparedness.network.models.Election

interface LocalDataSource {
    suspend fun getListOfElections(): Result<List<Election>>

    suspend fun deleteElection(id: Long)

    suspend fun saveElection(election: Election)

    suspend fun getElectionById(id: Long): Result<Election?>
}