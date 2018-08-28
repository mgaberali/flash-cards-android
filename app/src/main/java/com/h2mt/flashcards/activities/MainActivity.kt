package com.h2mt.flashcards.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.h2mt.flashcards.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // @TODO Remove this block after implementing log in
        val userSharedPref = getSharedPreferences(this
                .getString(R.string.user_preference_file_key), Context.MODE_PRIVATE)
        with(userSharedPref.edit()){
            putString(this@MainActivity.getString(R.string.user_preference_access_token_key), "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiZmxhc2gtY2FyZHMiXSwiZXhwIjoxNTMyNjM2MDA4LCJ1c2VyX25hbWUiOiJ0YWhlci5heW91YjkwQGdtYWlsLmNvbSIsImp0aSI6ImMyOTliNDczLWEzY2UtNGM5MS1iNDlkLWRiNWU1ZTNhOTA1MSIsImNsaWVudF9pZCI6IndlYiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.zb41vTl-SBQTIn4ctxebHrvUKY5spmfhWLjAqohUUsE")
            commit()
        }
        //-------------------------------------------------------

        go_to_card_list_btn.setOnClickListener({
            val intent = Intent(this, CardListActivity::class.java)
            startActivity(intent)
        })
        go_to_Login_temp_btn.setOnClickListener({
            val intent2 = Intent(this, SignupActivity::class.java)
            startActivity(intent2)
        })
    }
}
