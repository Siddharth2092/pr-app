package com.example.prmanager.data.remote.api

import com.example.prmanager.data.remote.model.PRResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PullRequestsService {

  @GET("repos/{userId}/{repoId}/pulls")
  suspend fun getPullRequests(@Path("userId") userId: String,
                              @Path("repoId") repoId: String,
                              @Query("state") prState : String) : Response<List<PRResponse>>
}