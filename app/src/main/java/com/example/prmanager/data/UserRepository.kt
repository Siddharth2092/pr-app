package com.example.prmanager.data

import com.example.prmanager.data.remote.model.UserResponse
import com.example.prmanager.data.remote.source.UserDataSource
import com.example.prmanager.router.ApiResponseInfo
import com.example.prmanager.router.BaseApiRequest
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class UserRepository @Inject constructor(private val userDataSource: UserDataSource) : BaseApiRequest() {

  suspend fun getUserByLoginId(userId: String): ApiResponseInfo<UserResponse> {
    return callApi { userDataSource.getUserByLoginId(userId) }
  }
}