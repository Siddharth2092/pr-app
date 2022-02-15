package com.example.prmanager.data.remote.source

import com.example.prmanager.data.remote.api.UserService
import com.example.prmanager.data.remote.model.UserResponse
import retrofit2.Response
import javax.inject.Inject

class UserDataSource @Inject constructor(private val userService: UserService) {

  suspend fun getUserByLoginId(userId: String) : Response<UserResponse> =
    userService.getUserByLoginId(userId)
}