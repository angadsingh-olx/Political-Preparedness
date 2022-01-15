package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.arch.ServiceLocator
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.network.models.Election

class ElectionsFragment: Fragment() {

    //DONE: Declare ViewModel
    private val viewModel by viewModels<ElectionsViewModel> {
        ElectionsViewModelFactory(
            ServiceLocator.electionLocalRepository,
            ServiceLocator.electionNetworkRepository
        )
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val viewBinding = FragmentElectionBinding.inflate(inflater, container, false)
        //DONE: Add ViewModel values and create ViewModel
        viewBinding.lifecycleOwner = this
        viewBinding.electionViewModel = viewModel

        //TODO: Add binding values

        //TODO: Link elections to voter info

        //DONE: Initiate recycler adapters
        val adapter = ElectionListAdapter(object: ElectionListener {
            override fun onElectionItemSelected(election: Election) {
                this@ElectionsFragment.findNavController()
                    .navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election.id, election.division))
            }
        })
        viewBinding.listElections.adapter = adapter

        //DONE: Populate recycler adapters
        viewModel.fetchElectionData()
        return viewBinding.root
    }

    //TODO: Refresh adapters when fragment loads

}