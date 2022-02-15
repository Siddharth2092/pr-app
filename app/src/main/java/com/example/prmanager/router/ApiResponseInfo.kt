package com.example.prmanager.router

sealed class ApiResponseInfo<T>(val data: T? = null, val code: String? = null, val message: String? = null) {

  class Success<T>(data: T) : ApiResponseInfo<T>(data)
  class Error<T>(code: String, message: String, data: T? = null) : ApiResponseInfo<T>(data, code, message)
  class Loading<T> : ApiResponseInfo<T>()
}