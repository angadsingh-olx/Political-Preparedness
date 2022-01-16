package com.example.android.politicalpreparedness.arch.entity

sealed class State {
    object LOADING: State()
    class ERROR(val message: String?): State()
    object SUCCESS: State()
}