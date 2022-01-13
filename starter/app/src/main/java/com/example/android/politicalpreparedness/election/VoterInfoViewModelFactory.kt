package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.domain.ElectionDataRepository

//TODO: Create Factory to generate VoterInfoViewModel with provided election datasource
@Suppress("UNCHECKED_CAST")
class VoterInfoViewModelFactory(
    private val electionDataRepository: ElectionDataRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = VoterInfoViewModel(electionDataRepository) as T

}