package com.h2mt.flashcards.services

import android.database.Observable
import com.h2mt.flashcards.models.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface UserApi {

    @FormUrlEncoded
    @POST("/oauth/token")
    fun loginUser(@Field("username") username : String,
                  @Field("password") password : String,
                  @Field("grant_type") grantType : String,
                  @Header("Authorization") authHeader : String) : Call<ResponseBody>

    @POST("api/users")
    fun signupUser(@Body user : User): Call<Object>

}