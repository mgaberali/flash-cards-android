package com.h2mt.flashcards.activities

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.h2mt.flashcards.R
import com.h2mt.flashcards.models.Card
import com.h2mt.flashcards.services.UserService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    var _emailText: EditText? = null
    var _passwordText: EditText? = null
    var _loginButton: Button? = null
    var _resetButton: Button? =null
    var _signupLink: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        _loginButton = findViewById(R.id.loginButton) as Button
        _resetButton = findViewById(R.id.resetButton) as Button
        _passwordText = findViewById(R.id.passwordText) as EditText
        _emailText = findViewById(R.id.userEmailText) as EditText

        _resetButton!!.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            _emailText!!.setText("")
            _passwordText!!.setText("")
        }

        _loginButton!!.setOnClickListener { login() }

    }


    fun login() {
        Log.d("LoginActivity", "Login")

        if (!validate()) {
            onLoginFailed()
            return
        }

        _loginButton!!.isEnabled = false

        val progressDialog = ProgressDialog(this@LoginActivity)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Login...")
        progressDialog.show()

        val email = _emailText!!.text.toString()
        val password = _passwordText!!.text.toString()

        UserService.loginUser(email,password).enqueue(object : Callback <ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.d("LoginFailed",t.toString())
                onLoginFailed()
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                response?.let {
                    if (response.isSuccessful) {
                        val response = response.body()
                        Log.d("LoginReponseBody", response?.string());

                    }else{
                        Log.d("LoginFailed", "login failed without Exception")
                        onLoginFailed()
                   progressDialog.cancel()

                    }
                }
            }
        })

    }

    fun validate(): Boolean {
        var valid = true

        val email = _emailText!!.text.toString()
        val password = _passwordText!!.text.toString()

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText!!.error = "enter a valid email address"
            valid = false
        } else {
            _emailText!!.error = null
        }

        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            _passwordText!!.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            _passwordText!!.error = null
        }

        return valid
    }

    fun onLoginFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()

        _loginButton!!.isEnabled = true
    }

    fun onLoginSuccess() {
        _loginButton!!.isEnabled = true
//        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}
