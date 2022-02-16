package com.example.prmanager.data

import com.example.prmanager.data.remote.model.UserResponse
import com.example.prmanager.data.remote.source.UserDataSource
import com.example.prmanager.router.ApiResponseInfo
import com.example.prmanager.router.BaseApiRequest
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class UserRepository @Inject constructor(private val userDataSource: UserDataSource) : BaseApiRequest() {

  private val userCache: HashMap<String, UserResponse> = HashMap()

  suspend fun getUserByLoginId(userLoginId: String): ApiResponseInfo<UserResponse> {

    if (userCache[userLoginId] != null) return ApiResponseInfo.Success(userCache[userLoginId]!!)

    val resp = callApi { userDataSource.getUserByLoginId(userLoginId) }
    if (resp is ApiResponseInfo.Success) {
      userCache[userLoginId] = resp.data!!
    }
    return resp
  }
}