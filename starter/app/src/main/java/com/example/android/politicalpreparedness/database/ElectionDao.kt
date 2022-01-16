package com.example.android.politicalpreparedness.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    //DONE: Add insert query
    @Insert
    suspend fun saveElection(data: Election)

    //DONE: Add select all election query
    @Query("select * from election_table")
    suspend fun getListOfElections(): List<Election>?

    //DONE: Add select single election query
    @Query("select * from election_table where id = :id")
    suspend fun getElectionById(id: Long): Election?

    //DONE: Add delete query
    @Query("delete from election_table where id = :id")
    suspend fun removeElectionById(id: Long)

    //DONE: Add clear query
    @Query("delete from election_table")
    suspend fun clear()
}