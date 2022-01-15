package com.example.android.politicalpreparedness.domain

import com.example.android.politicalpreparedness.network.models.Election

interface LocalDataSource {
    suspend fun getListOfElections()

    suspend fun deleteElection(id: Long)

    suspend fun saveElection(election: Election)
}