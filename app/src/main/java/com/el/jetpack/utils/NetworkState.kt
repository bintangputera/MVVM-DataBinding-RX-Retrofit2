package com.el.jetpack.utils

enum class Status {
    RUNNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status: Status) {
    companion object {
        val LOADED: NetworkState = NetworkState(Status.SUCCESS)
        val LOADING: NetworkState = NetworkState(Status.RUNNNING)
        val ERROR: NetworkState = NetworkState(Status.FAILED)
    }
}