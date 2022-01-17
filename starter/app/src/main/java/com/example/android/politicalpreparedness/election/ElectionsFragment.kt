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
import com.example.android.politicalpreparedness.arch.entity.State
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.network.models.Election
import com.google.android.material.snackbar.Snackbar

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
        //DONE: Add binding values
        viewBinding.lifecycleOwner = this
        viewBinding.electionViewModel = viewModel


        //DONE: Link elections to voter info
        //DONE: Initiate recycler adapters
        val electionAdapter = ElectionListAdapter(object: ElectionListener {
            override fun onElectionItemSelected(election: Election) {
                this@ElectionsFragment.findNavController()
                    .navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election.id, election.division))
            }
        })
        val savedElectionAdapter = ElectionListAdapter(object: ElectionListener {
            override fun onElectionItemSelected(election: Election) {
                this@ElectionsFragment.findNavController()
                    .navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election.id, election.division))
            }
        })
        viewBinding.listElections.adapter = electionAdapter
        viewBinding.listSavedElections.adapter = savedElectionAdapter

        //DONE: Populate recycler adapters
        viewModel.fetchElectionData()
        viewModel.fetchSavedData()

        viewModel.state.observe(viewLifecycleOwner, Observer {
            if (it is State.ERROR) {
                it.message?.let { message ->
                    Snackbar.make(viewBinding.root, message, Snackbar.LENGTH_SHORT).show()
                }
            }
        })
        return viewBinding.root
    }

    //DONE: Refresh adapters when fragment loads

}