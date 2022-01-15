package com.example.android.politicalpreparedness.domain

import com.example.android.politicalpreparedness.arch.entity.Result
import com.example.android.politicalpreparedness.network.models.Election

interface NetworkDataSource {
    suspend fun getListOfElections(): Result<List<Election>>
}