package com.example.android.politicalpreparedness

import android.app.Application
import com.example.android.politicalpreparedness.arch.ServiceLocator
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi

class PoliticalPreparednessApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.database = ElectionDatabase.getInstance(this)
        ServiceLocator.networkService = CivicsApi.retrofitService
    }
}