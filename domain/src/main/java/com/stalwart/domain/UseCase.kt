package com.stalwart.domain

/**
Created by Swanand Deshpande
 */

// This data class is responsible to communicate the current state of Network call to UI.
data class UseCase<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {

        fun <T> loading(data: T?): UseCase<T> {
            return UseCase(Status.LOADING, data, null)
        }

        fun <T> success(data: T?): UseCase<T> {
            return UseCase(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): UseCase<T> {
            return UseCase(Status.ERROR, data, msg)
        }
    }
}
