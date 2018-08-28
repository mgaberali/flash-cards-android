package com.h2mt.flashcards.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.h2mt.flashcards.R
import com.h2mt.flashcards.models.Card
import com.h2mt.flashcards.recyclerviews.CardAdapter
import com.h2mt.flashcards.services.CardService
import kotlinx.android.synthetic.main.activity_card_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)

        cards_recycleview.visibility = View.GONE
        cards_recycleview.layoutManager = LinearLayoutManager(this)

        loadCardsBySetId(1)
    }

    private fun loadCardsBySetId(setId: Int){

        progress.visibility = View.VISIBLE
        cards_recycleview.visibility = View.GONE

        CardService.getCardsBySetId(setId).enqueue(object : Callback<List<Card>> {

            override fun onFailure(call: Call<List<Card>>?, t: Throwable?) {
                Log.e("GET_CARDS", t.toString())
            }

            override fun onResponse(call: Call<List<Card>>?, response: Response<List<Card>>?) {
                response?.let {
                    if(response.isSuccessful){
                        val cardsList = response.body()
                        cardsList?.let {
                            val cardAdapter = CardAdapter(cardsList)
                            cards_recycleview.adapter = cardAdapter
                        }
                        cards_recycleview.scrollToPosition(0)
                        cards_recycleview.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                    }
                }
            }

        })
    }
}
