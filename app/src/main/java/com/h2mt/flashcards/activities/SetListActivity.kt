package com.h2mt.flashcards.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.h2mt.flashcards.R
import com.h2mt.flashcards.models.Card
import com.h2mt.flashcards.models.Set
import com.h2mt.flashcards.recyclerviews.CardAdapter
import com.h2mt.flashcards.recyclerviews.SetAdapter
import com.h2mt.flashcards.services.CardService
import com.h2mt.flashcards.services.SetService
import kotlinx.android.synthetic.main.activity_card_list.*
import kotlinx.android.synthetic.main.activity_set_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SetListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_list)

        sets_recycleview.visibility = View.GONE
        sets_recycleview.layoutManager = LinearLayoutManager(this)
    }

    private fun loadSets(){
        sets_progress.visibility = View.VISIBLE
        cards_recycleview.visibility = View.GONE

        SetService.getSets().enqueue(object : Callback<List<Set>>{
            override fun onFailure(call: Call<List<Set>>?, t: Throwable?) {
                Log.e("GET_CARDS", t.toString())
            }

            override fun onResponse(call: Call<List<Set>>?, response: Response<List<Set>>?) {
                response?.let{
                    Log.i("GET_SetS", "Get sets repsonse received successfully")
                    if(response.isSuccessful){
                        val setsList = response.body()
                        setsList?.let{
                            val setAdapter = SetAdapter(setsList)
                            sets_recycleview.adapter = setAdapter
                        }
                        sets_recycleview.scrollToPosition(0)
                        sets_recycleview.visibility = View.VISIBLE
                        sets_progress.visibility = View.GONE
                    }
                }
            }
        })
    }
}