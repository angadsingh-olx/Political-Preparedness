package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.android.politicalpreparedness.databinding.ViewholderElectionBinding
import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(private val clickListener: ElectionListener): ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }

    //DONE: Bind ViewHolder
    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}

//DONE: Create ElectionViewHolder
class ElectionViewHolder(private val viewBinding: ViewholderElectionBinding): RecyclerView.ViewHolder(viewBinding.root) {
    //DONE: Add companion object to inflate ViewHolder (from)
    companion object {
        fun from(parent: ViewGroup): ElectionViewHolder {
            val viewBinding = ViewholderElectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ElectionViewHolder(viewBinding)
        }
    }

    fun bind(data: Election, electionListener: ElectionListener) {
        viewBinding.electionData = data
        viewBinding.electionListener = electionListener
        viewBinding.executePendingBindings()
    }
}

//DONE: Create ElectionDiffCallback
class ElectionDiffCallback: DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem.id == newItem.id
    }
}

//DONE: Create ElectionListener
interface ElectionListener {
    fun onElectionItemSelected(election: Election)
}