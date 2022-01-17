package com.example.android.politicalpreparedness.representative.adapter

import android.view.View
import android.widget.*
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.arch.entity.State
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.representative.model.Representative
import java.util.*
import androidx.appcompat.widget.AppCompatSpinner

import androidx.databinding.InverseBindingAdapter
import android.widget.ArrayAdapter

import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener

import androidx.databinding.InverseBindingListener







@BindingAdapter("profileImage")
fun fetchImage(view: ImageView, src: String?) {
    src?.let {
        val uri = src.toUri().buildUpon().scheme("https").build()
        //DONE: Add Glide call to load image and circle crop - user ic_profile as a placeholder and for errors.
        Glide.with(view).load(uri)
            .placeholder(R.drawable.ic_profile)
            .error(R.drawable.ic_profile)
            .into(view)
    }
}

@BindingAdapter("stateValue")
fun Spinner.setNewValue(value: String?) {
    val adapter = toTypedAdapter<String>(this.adapter as ArrayAdapter<*>)
    val position = when (adapter.getItem(0)) {
        is String -> adapter.getPosition(value)
        else -> this.selectedItemPosition
    }
    if (position >= 0) {
        setSelection(position)
    }
}

@BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
fun bindSpinnerData(
    pAppCompatSpinner: AppCompatSpinner,
    newSelectedValue: String?,
    newTextAttrChanged: InverseBindingListener
) {
    pAppCompatSpinner.onItemSelectedListener = object : OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
            newTextAttrChanged.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
    if (newSelectedValue != null) {
        val pos = (pAppCompatSpinner.adapter as ArrayAdapter<String?>).getPosition(newSelectedValue)
        pAppCompatSpinner.setSelection(pos, true)
    }
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun captureSelectedValue(spinner: AppCompatSpinner): String? {
    return spinner.selectedItem as String
}

@BindingAdapter("electionListData")
fun bindElectionAdapter(recyclerView: RecyclerView, data: List<Election>?) {
    val adapter = recyclerView.adapter as ElectionListAdapter
    adapter.submitList(data)
}

@BindingAdapter("representativeListData")
fun bindRepresentativeAdapter(recyclerView: RecyclerView, data: List<Representative>?) {
    val adapter = recyclerView.adapter as RepresentativeListAdapter
    adapter.submitList(data)
}

@BindingAdapter("loaderState")
fun bindLoaderState(progressBar: ProgressBar, state: State?) {
    when (state) {
        State.LOADING -> progressBar.visibility = View.VISIBLE
        is State.ERROR -> progressBar.visibility = View.GONE
        State.SUCCESS -> progressBar.visibility = View.GONE
    }
}

@BindingAdapter("dateText")
fun loadFormattedDate(textView: TextView, date: Date?) {
    textView.text = date?.toString()
}

@BindingAdapter("shouldShowView")
fun shouldShowView(textView: TextView, any: Any?) {
    textView.visibility = if (any != null) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

inline fun <reified T> toTypedAdapter(adapter: ArrayAdapter<*>): ArrayAdapter<T>{
    return adapter as ArrayAdapter<T>
}
