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
            putString(this@MainActivity.getString(R.string.user_preference_access_token_key), "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiZmxhc2gtY2FyZHMiXSwiZXhwIjoxNTMyMzk1OTcxLCJ1c2VyX25hbWUiOiJtb2hhbWVkQGdtYWlsLmNvbSIsImp0aSI6IjA2NjUzYzU2LWVkN2EtNDBiZi05YmI4LTZlMDE2MzgzY2Q0NyIsImNsaWVudF9pZCI6IndlYiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.TqfHJdaLATKcreZ4XUcYTaTVDaNQ4Bde294rHVdCMPI")
            commit()
        }
        //-------------------------------------------------------

        go_to_card_list_btn.setOnClickListener({
            val intent = Intent(this, CardListActivity::class.java)
            startActivity(intent)
        })
    }
}
