package com.h2mt.flashcards.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.h2mt.flashcards.R
import com.h2mt.flashcards.models.Set
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // @TODO Remove this block after implementing log in
        val userSharedPref = getSharedPreferences(this
                .getString(R.string.user_preference_file_key), Context.MODE_PRIVATE)
        with(userSharedPref.edit()){
            putString(this@MainActivity.getString(R.string.user_preference_access_token_key), "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiZmxhc2gtY2FyZHMiXSwiZXhwIjoxNTMzNTE2NTY2LCJ1c2VyX25hbWUiOiJtb2hhbWVkQGdtYWlsLmNvbSIsImp0aSI6IjIzMWQ1NmJmLTJhOWEtNDQ1Ny04MDY4LTVhZTFkYjJlMDdkMyIsImNsaWVudF9pZCI6IndlYiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.xzJxirwA4odEWREV8do75f5njEhikF81rW4xizPN-N8")
            commit()
        }
        //-------------------------------------------------------

        // This how to integrate with card list activity
        val set = Set(4, "English" ,"English Words", "2018-07-20T20:30:00")
        go_to_card_list_btn.setOnClickListener({
            val intent = Intent(this, CardListActivity::class.java)
            intent.putExtra(getString(R.string.extra_set_key), set)
            startActivity(intent)
        })
        //---------------------------------------------------------

    }
}
