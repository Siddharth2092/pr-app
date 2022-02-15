package com.example.prmanager.data.remote.api

import com.example.prmanager.data.remote.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

  @GET("users/{userId}")
  suspend fun getUserByLoginId(@Path("userId") userId: String) : Response<UserResponse>
}