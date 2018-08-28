package com.h2mt.flashcards.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.h2mt.flashcards.config.RetrofitConfig
import com.h2mt.flashcards.models.User

object UserService {

    private val userApi : UserApi = RetrofitConfig.retrofit.create(UserApi::class.java)

    fun loginUser(username : String, password : String) = userApi.loginUser(username,password, "password","Basic d2ViOjEyMzQ1Ng==")

    fun signupUser(user: User) = userApi.signupUser(user)

}
