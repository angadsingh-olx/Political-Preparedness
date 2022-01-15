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


class VoterInfoFragment : Fragment() {

    //DONE: Add ViewModel values and create ViewModel
    private val viewModel by viewModels<VoterInfoViewModel> {
        VoterInfoViewModelFactory(
            ServiceLocator.electionLocalRepository,
            ServiceLocator.electionNetworkRepository
        )
    }

    private lateinit var viewBinding: FragmentVoterInfoBinding

    val actionListener = object: VotingDetailListener {
        override fun loadInfoUrl(url: String) {
            loadUrl(url)
        }

        override fun onFollowCtaAction(election: Election) {

        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //TODO: Add binding values
        viewBinding = FragmentVoterInfoBinding.inflate(inflater, container, false)
        viewBinding.lifecycleOwner = this
        viewBinding.viewModel = viewModel
        viewBinding.actionListener = actionListener
        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */
        arguments?.let {
            val division = VoterInfoFragmentArgs.fromBundle(it).argDivision
            val electionId = VoterInfoFragmentArgs.fromBundle(it).argElectionId
            viewModel.fetchElectionData(division, electionId.toLong())
        }

        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks
        return viewBinding.root
    }

    //TODO: Create method to load URL intents
    fun loadUrl(url: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
    }
}

interface VotingDetailListener {
    fun loadInfoUrl(url: String)

    fun onFollowCtaAction(election: Election)
}