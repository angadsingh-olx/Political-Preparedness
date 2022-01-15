package com.example.android.politicalpreparedness.arch

import com.example.android.politicalpreparedness.data.ElectionLocalDataRepository
import com.example.android.politicalpreparedness.data.ElectionNetworkDataRepository
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApiService

object ServiceLocator {

    internal lateinit var database: ElectionDatabase

    internal lateinit var networkService: CivicsApiService

    private val electionDao: Lazy<ElectionDao> = lazy {
        return@lazy database.electionDao
    }

    val electionLocalRepository: Lazy<ElectionLocalDataRepository> = lazy {
        return@lazy ElectionLocalDataRepository(electionDao)
    }

    val electionNetworkRepository: Lazy<ElectionNetworkDataRepository> = lazy {
     return@lazy ElectionNetworkDataRepository(networkService)
    }

}