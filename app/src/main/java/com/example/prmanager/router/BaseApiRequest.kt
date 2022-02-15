package com.example.prmanager.router

import retrofit2.Response

abstract class BaseApiRequest {

  suspend fun <T> callApi(apiCall: suspend () -> Response<T>): ApiResponseInfo<T> {
    try {
      val response = apiCall()

      if (response.isSuccessful) {
        val body = response.body()

        body?.let {
          return ApiResponseInfo.Success(body)
        }
      }
      return error("${response.code()} ${response.message()}")
    } catch (e: Exception) {
      return error(e.message ?: e.toString())
    }
  }

  private fun <T> error(errorMessage: String): ApiResponseInfo<T> =
    ApiResponseInfo.Error("500","Api call failed $errorMessage")
}