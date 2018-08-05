package com.h2mt.flashcards.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.h2mt.flashcards.R
import com.h2mt.flashcards.models.Card
import com.h2mt.flashcards.models.Set
import com.h2mt.flashcards.models.SetCreateRequest
import com.h2mt.flashcards.recyclerviews.CardAdapter
import com.h2mt.flashcards.recyclerviews.SetAdapter
import com.h2mt.flashcards.recyclerviews.SetListOperations
import com.h2mt.flashcards.services.CardService
import com.h2mt.flashcards.services.SetService
import kotlinx.android.synthetic.main.activity_card_list.*
import kotlinx.android.synthetic.main.activity_set_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SetListActivity: AppCompatActivity(), SetListOperations {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_list)

        sets_recycleview.visibility = View.GONE
        sets_recycleview.layoutManager = LinearLayoutManager(this)

        loadSets()

        addSet.setOnClickListener(View.OnClickListener {
            val setCreateRequest = SetCreateRequest(name="hossam", desc = "hossam desc")
            SetService.addSet(request = setCreateRequest).enqueue(object: Callback<Void>{
                override fun onFailure(call: Call<Void>?, t: Throwable?) {
                    Log.e("ADD_SET", t.toString())
                }

                override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                    Log.i("ADD_SET", "add set repsonse received successfully")
                    if(response!!.isSuccessful){
                        Log.i("ADD_SET", "add set repsonse received successfully")
                    }
                }
            })
        })
    }

    override fun openSet(setId: Integer) {
        val intent = Intent(this, CardListActivity::class.java)
        intent.putExtra("setId", setId)
        startActivity(intent)
    }

    private fun loadSets(){
        sets_progress.visibility = View.VISIBLE
        sets_recycleview.visibility = View.GONE

        SetService.getSets().enqueue(object : Callback<List<Set>>{
            override fun onFailure(call: Call<List<Set>>?, t: Throwable?) {
                Log.e("GET_SETS", t.toString())
            }

            override fun onResponse(call: Call<List<Set>>?, response: Response<List<Set>>?) {
                response?.let{
                    Log.i("GET_SETSS", "Get sets repsonse received successfully")
                    if(response.isSuccessful){
                        val setsList = response.body()
                        setsList?.let{
                            val setAdapter = SetAdapter(setsList, this@SetListActivity)
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