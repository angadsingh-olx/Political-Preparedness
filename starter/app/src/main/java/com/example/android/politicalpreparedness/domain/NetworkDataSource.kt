package com.example.android.politicalpreparedness.domain

import com.example.android.politicalpreparedness.arch.entity.Result
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Deferred

interface NetworkDataSource {
    suspend fun getListOfElections(): Result<List<Election>>
    suspend fun getVotersInfo(address: String, electionId: Long?): Result<VoterInfoResponse>
    fun getRepresentativesFromNetworkAsync(address: Address): Deferred<RepresentativeResponse>
}