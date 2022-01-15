package com.example.android.politicalpreparedness.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    //TODO: Add insert query
    @Insert
    suspend fun saveElection(data: Election)

    //TODO: Add select all election query
    @Query("select * from election_table")
    suspend fun getListOfElections(): List<Election>

    //TODO: Add select single election query
    @Query("select * from election_table where id = :id")
    suspend fun getElectionById(id: Long): Election

    //TODO: Add delete query
    @Query("delete from election_table where id = :id")
    suspend fun removeElectionById(id: Long)

    //TODO: Add clear query
    @Query("delete from election_table")
    suspend fun clear()
}