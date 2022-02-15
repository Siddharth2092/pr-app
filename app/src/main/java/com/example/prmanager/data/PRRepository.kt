package com.example.prmanager.data

import com.example.prmanager.data.remote.model.PRResponse
import com.example.prmanager.data.remote.source.PRDataSource
import com.example.prmanager.router.ApiResponseInfo
import com.example.prmanager.router.BaseApiRequest
import com.example.prmanager.utils.Constants
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class PRRepository @Inject constructor(private val prDataSource: PRDataSource) : BaseApiRequest() {

  suspend fun getPrs(userId: String,
                     repoId: String,
                     prState: Constants.Companion.PR_STATE): Flow<ApiResponseInfo<List<PRResponse>>> {

    return flow {
      emit( callApi { prDataSource.getPRs(userId, repoId, prState) })
    }.flowOn(Dispatchers.IO)
  }
}