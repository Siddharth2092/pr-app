package com.example.prmanager.data.remote.model

import com.google.gson.annotations.SerializedName

data class PRResponse(val id : Long,
                      var user : PRUser,
                      val title : String,
                      @field:SerializedName("created_at") val createdOn: String,
                      @field:SerializedName("closed_at") val closedOn: String,
                      @field:SerializedName("updated_at") val updatedAOn: String)

data class PRUser(val login : String,
                  val id : Long,
                  var userName : String?,
                  @field:SerializedName("avatar_url") val imageUrl: String)
