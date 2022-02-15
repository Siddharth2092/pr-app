package com.example.prmanager.data.remote.source

import com.example.prmanager.data.remote.api.PullRequestsService
import com.example.prmanager.data.remote.model.PRResponse
import com.example.prmanager.router.ApiResponseInfo
import com.example.prmanager.utils.Constants.Companion.PR_STATE
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class PRDataSource @Inject constructor(private val prService : PullRequestsService) {

  suspend fun getPRs(userId: String, repoId: String, prState: PR_STATE) : Response<List<PRResponse>>
      = prService.getPullRequests(userId, repoId, prState.value)
}