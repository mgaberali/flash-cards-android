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
            putString(this@MainActivity.getString(R.string.user_preference_access_token_key), this@MainActivity.getString(R.string.flash_cards_access_token))
            commit()
        }
        //-------------------------------------------------------

        go_to_card_list_btn.setOnClickListener({
            val intent = Intent(this, CardListActivity::class.java)
            startActivity(intent)
        })

        signinbutton.setOnClickListener({
            val intent = Intent(this, SetListActivity::class.java)
            startActivity(intent)
        })
    }
}
