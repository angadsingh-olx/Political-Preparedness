package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.politicalpreparedness.arch.ServiceLocator
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.network.models.Election
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.arch.entity.State
import com.google.android.material.snackbar.Snackbar


class VoterInfoFragment : Fragment() {

    //DONE: Add ViewModel values and create ViewModel
    private val viewModel by viewModels<VoterInfoViewModel> {
        VoterInfoViewModelFactory(
            ServiceLocator.electionLocalRepository,
            ServiceLocator.electionNetworkRepository
        )
    }

    private lateinit var viewBinding: FragmentVoterInfoBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //DONE: Add binding values
        viewBinding = FragmentVoterInfoBinding.inflate(inflater, container, false)
        viewBinding.lifecycleOwner = this
        viewBinding.viewModel = viewModel
        //DONE: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */
        arguments?.let {
            val division = VoterInfoFragmentArgs.fromBundle(it).argDivision
            val electionId = VoterInfoFragmentArgs.fromBundle(it).argElectionId
            viewModel.fetchElectionData(division, electionId.toLong())
            viewModel.onPageLoad(electionId.toLong())
            viewBinding.electionId = electionId.toLong()
        }

        //DONE: Handle loading of URLs
        viewModel.webUrl.observe(viewLifecycleOwner, Observer {
            loadUrl(it)
        })

        //DONE: Handle save button UI state
        viewModel.electionSaved.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewBinding.actionFollow.text = getString(R.string.label_unfollow_election)
            } else {
                viewBinding.actionFollow.text = getString(R.string.label_follow_election)
            }
        })

        viewModel.state.observe(viewLifecycleOwner, Observer {
            if (it is State.ERROR) {
                it.message?.let { message ->
                    Snackbar.make(viewBinding.root, message, Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.voterInfoLiveData.observe(viewLifecycleOwner, Observer {
            (activity as AppCompatActivity).supportActionBar?.title= it.election.name
        })
        //DONE: cont'd Handle save button clicks
        return viewBinding.root
    }

    //DONE: Create method to load URL intents
    private fun loadUrl(url: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
    }

    override fun onDestroyView() {
        (activity as AppCompatActivity).supportActionBar?.title= getString(R.string.app_name)
        super.onDestroyView()
    }
}