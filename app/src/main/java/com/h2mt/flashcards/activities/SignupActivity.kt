package com.h2mt.flashcards.activities

import android.app.ProgressDialog
import android.content.Intent
import android.database.Observable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.h2mt.flashcards.R
import com.h2mt.flashcards.models.User
import com.h2mt.flashcards.services.UserService
import com.squareup.moshi.JsonReader
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    var _emailText: EditText? = null
    var _passwordText: EditText? = null
    var _confirmPasswordText: EditText? = null
    var _firstNameText: EditText? = null
    var _lastNameText: EditText? = null
    var _signupButton: Button? = null
    var _resetButton: Button? =null
    var _loginLink: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        _emailText = findViewById(R.id.signupEmailText) as EditText
        _passwordText = findViewById(R.id.signuppasswordText) as EditText
        _confirmPasswordText = findViewById(R.id.confirmPasswordText) as EditText
        _firstNameText = findViewById(R.id.firstNameText) as EditText
        _lastNameText = findViewById(R.id.lastNameText) as EditText
        _signupButton = findViewById(R.id.signupButton) as Button
        _resetButton = findViewById(R.id.SignupResetButton) as Button
        _loginLink = findViewById(R.id.loginTextView) as TextView

        _resetButton!!.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            _emailText!!.setText("")
            _passwordText!!.setText("")
            _confirmPasswordText!!.setText("")
            _firstNameText!!.setText("")
            _lastNameText!!.setText("")
        }


        _signupButton!!.setOnClickListener { signup() }

        _loginLink!!.setOnClickListener {
            // Finish the registration screen and return to the Login activity
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        }

    }


    fun signup() {
        Log.d("Signup", "Signup")

        if (!validate()) {
            onSignupFailed()
            return
        }

        _signupButton!!.isEnabled = false

        val progressDialog = ProgressDialog(this@SignupActivity)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()

         val newUser = User(
                firstName = _firstNameText!!.text.toString(),
                lastName = _lastNameText!!.text.toString(),
                 email = _emailText!!.text.toString(),
                 password = _passwordText!!.text.toString()
        )
        UserService.signupUser(newUser).enqueue( object: Callback<Object> {
            override fun onFailure(call: Call<Object>?, t: Throwable?) {
                Log.d("SignupFailed",t.toString())
                onSignupFailed()
            }

            override fun onResponse(call: Call<Object>?, response: Response<Object>?) {
                Log.d("SignupResponse***** ", response.toString());

                response?.let {
                    if (response.isSuccessful) {
                        val responseHeader = response.headers();
                        val response = response.body()
                        Log.d("SignupResponse", response.toString());
                        Log.d("SignupResponseCode", responseHeader.get("code").toString());

                        onSignupSuccess()

                    }else{
                        Log.d("SignupResponseCode", response.headers().toMultimap().values.toString());
                        Log.d("SignupFailed", "signup failed without Exception")
                        onSignupFailed()
                        progressDialog.cancel()

                    }
                }

            }

        })

    }

    fun onSignupSuccess() {
        _signupButton!!.isEnabled = true
//        setResult(Activity.RESULT_OK, null)
//        finish()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun onSignupFailed() {
        Toast.makeText(baseContext, "Signup failed", Toast.LENGTH_LONG).show()

        _signupButton!!.isEnabled = true
    }


    fun validate(): Boolean {
        var valid = true

        val email = _emailText!!.text.toString()
        val password = _passwordText!!.text.toString()
        val confirmPassword = _confirmPasswordText!!.text.toString()
        val firstName = _firstNameText!!.text.toString()
        val lastName = _lastNameText!!.text.toString()

        if (firstName.isEmpty() || firstName.length < 3) {
            _firstNameText!!.error = "at least 3 characters"
            valid = false
        } else {
            _firstNameText!!.error = null
        }

        if (lastName.isEmpty() || lastName.length < 3) {
            _lastNameText!!.error = "at least 3 characters"
            valid = false
        } else {
            _lastNameText!!.error = null
        }

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

        if (confirmPassword.isEmpty() || confirmPassword.length < 4 || confirmPassword.length > 10 || confirmPassword != password) {
            _confirmPasswordText!!.error = "Password Do not match"
            valid = false
        } else {
            _confirmPasswordText!!.error = null
        }

        return valid
    }
}
