package com.example.android.politicalpreparedness.representative

import android.app.Application
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.arch.entity.State
import com.example.android.politicalpreparedness.data.ElectionNetworkDataRepository
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RepresentativeViewModel(private val networkDataRepository: Lazy<ElectionNetworkDataRepository>, val app: Application) : ViewModel() {

    //DONE: Establish live data for representatives and address
    val representativeList: LiveData<List<Representative>>
            get() = _representatives
    private val _representatives = MutableLiveData<List<Representative>>()

    val showSnackBarInt: LiveData<Int>
            get() = _showSnackBarInt
    private val _showSnackBarInt = MutableLiveData<Int>()

    val selectedItem = MutableLiveData<Int>()

    val loaderState: LiveData<State>
        get() = _state
    private val _state = MutableLiveData<State>()

    val line1 = MutableLiveData<String>()
    val line2 = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val state = MediatorLiveData<String>()
    val zip = MutableLiveData<String>()

    private var runningJob: Job ?= null

    init {
        state.addSource(selectedItem) {
            state.value = app.resources.getStringArray(R.array.states)[it]
        }
    }

    //DONE: Create function to fetch representatives from API from a provided address
    fun getRepresentatives() {
        runningJob?.cancel()
        runningJob = viewModelScope.launch {
            _state.value = State.LOADING
            if (validateEnteredData()) {
                val address = getAddress()
                kotlin.runCatching {
                    val (offices, officials) = networkDataRepository.value.getRepresentativesFromNetworkAsync(address).await()
                    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }
                    _state.value = State.SUCCESS
                }.onFailure {
                    _showSnackBarInt.value = R.string.err_failed_rep_data
                    _state.value = State.ERROR("")
                }
            }
        }
    }

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //DONE: Create function get address from geo location
    fun setLocation(address: Address) {
        line1.value = address.line1
        line2.value = address.line2
        city.value = address.city
        state.value = address.state
        zip.value = address.zip

        getRepresentatives()
    }

    //DONE: Create function to get address from individual fields
    private fun validateEnteredData(): Boolean {
        if (line1.value.isNullOrBlank()) {
            _showSnackBarInt.value = R.string.err_enter_address_1
            return false
        }
        if (city.value.isNullOrBlank()) {
            _showSnackBarInt.value = R.string.err_enter_city
            return false
        }
        if (zip.value.isNullOrBlank()) {
            _showSnackBarInt.value = R.string.err_enter_zip
            return false
        }
        return true
    }

    private fun getAddress(): Address = Address(
        line1.value!!,
        line2.value,
        city.value!!,
        state.value!!,
        zip.value!!
    )
}
