package com.example.prmanager.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.prmanager.data.PRRepository
import com.example.prmanager.data.UserRepository
import com.example.prmanager.data.remote.model.PRResponse
import com.example.prmanager.data.remote.model.UserResponse
import com.example.prmanager.router.ApiResponseInfo
import com.example.prmanager.utils.AndroidUtils
import com.example.prmanager.utils.Constants.Companion.PR_STATE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class PRViewModel @Inject internal constructor(private val prRepository: PRRepository,
                                               private val userRepository: UserRepository,
                                               private val app: Application): AndroidViewModel(app) {

  private val mutablePrResponse: MutableLiveData<ApiResponseInfo<List<PRResponse>>> = MutableLiveData()
  val prResponse: LiveData<ApiResponseInfo<List<PRResponse>>> = mutablePrResponse

  private val userCache: HashMap<Long, UserResponse> = HashMap()

  fun getPrs(userId: String, repoId: String, state: PR_STATE) = viewModelScope.launch {

    if (!AndroidUtils.isNetworkActive(app)) {
      mutablePrResponse.value = ApiResponseInfo.Error("NETWORK_NOT_AVAILABLE",
        "No internet found. Please check your internet connection and retry.")

    } else {
      mutablePrResponse.value = ApiResponseInfo.Loading()

      prRepository.getPrs(userId, repoId, state).collect { response ->

        if (response is ApiResponseInfo.Success) {

          withContext(Dispatchers.IO) {

            val prs: List<PRResponse> = response.data!!
            val userRequests = ArrayList<Deferred<ApiResponseInfo<UserResponse>>>()

            prs.forEach { pr ->
              if (userCache[pr.user.id] == null) {
                val userReq = async {
                  userRepository.getUserByLoginId(pr.user.login)
                }
                userRequests.add(userReq)
              } else {
                pr.user.userName = userCache[pr.user.id]!!.name
              }
            }

            val users: List<ApiResponseInfo<UserResponse>> = userRequests.awaitAll()

            users.forEach { userApiData ->
              if (userApiData is ApiResponseInfo.Success) {
                val user: UserResponse = userApiData.data!!
                userCache[user.id] = user
              } else {
                mutablePrResponse.value = response
              }
            }

            prs.forEach { pr ->
              if (pr.user.userName == null && userCache[pr.user.id] != null) {
                pr.user.userName = userCache[pr.user.id]!!.name
              }
            }

            withContext(Dispatchers.Main) {
              mutablePrResponse.value = response
            }
          }
        } else {
          mutablePrResponse.value = response
        }
      }
    }
  }
}