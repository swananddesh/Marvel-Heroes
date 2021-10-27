package com.stalwart.domain

/**
Created by Swanand Deshpande
 */

// This data class is responsible to communicate the current state of Network call to UI.
data class ApiState<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {

        fun <T> loading(data: T?): ApiState<T> {
            return ApiState(Status.LOADING, data, null)
        }

        fun <T> success(data: T?): ApiState<T> {
            return ApiState(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): ApiState<T> {
            return ApiState(Status.ERROR, data, msg)
        }
    }
}
