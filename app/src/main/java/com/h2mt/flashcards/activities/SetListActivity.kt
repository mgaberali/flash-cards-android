package com.h2mt.flashcards.activities

import android.content.Context
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
import android.view.MotionEvent
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import kotlinx.android.synthetic.main.set_add_popup.view.*
import kotlinx.android.synthetic.main.set_row_item.*


class SetListActivity: AppCompatActivity(), SetListOperations {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_list)

        sets_recycleview.visibility = View.GONE
        sets_recycleview.layoutManager = LinearLayoutManager(this)

        loadSets()

        addSet.setOnClickListener(View.OnClickListener {
            var pair = onButtonShowPopupWindowClick(it)
            clickListenerToAddSet(pair.first, pair.second)
        })

    }

    override fun editSet(set: Set, it: View) {
        var pair = onButtonShowPopupWindowClick(it)
        prepareFormToUpdate(pair.first, set)
        clickListenerToUpdateSet(pair.first, pair.second, set)
    }

    override fun deleteSet(setId: Integer) {
        SetService.deleteSet(setId).enqueue(object: Callback<Void>{
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                Log.e("DELETE_SETS", t.toString())
            }

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                response?.let{
                    Log.i("DELETE_SETS", "Delete set repsonse received successfully")
                }
            }
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
                            val setAdapter = SetAdapter(ArrayList(setsList), this@SetListActivity)
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

    fun onButtonShowPopupWindowClick(view: View) : Pair<View, PopupWindow>{
        // inflate the layout of the popup window
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.set_add_popup, null)

        // create the popup window
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // lets taps outside the popup also dismiss it
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        // dismiss the popup window when touched
        popupView.setOnTouchListener(View.OnTouchListener { v, event ->
            popupWindow.dismiss()
            true
        })

        return Pair(popupView, popupWindow)
    }

    fun prepareFormToUpdate(popupView: View, set:Set){
        var setNameTextView = popupView.findViewById(R.id.setName) as TextView
        setNameTextView.text = set.name
        var setDescTextView = popupView.findViewById(R.id.setDesc) as TextView
        setDescTextView.text = set.desc
    }

    fun clickListenerToUpdateSet(popupView: View, popupWindow: PopupWindow, set: Set){
        var button =  popupView.findViewById(R.id.addSett) as Button
        button.setOnClickListener({
            var setNameTextView = popupView.findViewById(R.id.setName) as TextView
            var setDescTextView = popupView.findViewById(R.id.setDesc) as TextView
            val setCreateRequest = SetCreateRequest(name=setNameTextView.text.toString(), desc = setDescTextView.text.toString())
            Log.i("UPDATE_SET",  setDescTextView.text.toString())
            SetService.updateSet(setId = Integer(set.id), request = setCreateRequest).enqueue(object: Callback<Void>{
                override fun onFailure(call: Call<Void>?, t: Throwable?) {
                    Log.e("UPDATE_SET", t.toString())
                }

                override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                    Log.i("UPDATE_SET", "update set repsonse received successfully")
                    Log.i("UPDATE_SET", response.toString())
//                    Log.i("UPDATE_SET", response!!.raw().request().method())
                    if(response!!.isSuccessful){
                        Log.i("UPDATE_SET", "update set repsonse received successfully")
                        popupWindow.dismiss()
                    }
                }
            })
        })
    }

    fun clickListenerToAddSet(popupView: View, popupWindow: PopupWindow){
        var button =  popupView.findViewById(R.id.addSett) as Button
        button.setOnClickListener({
            var setNameTextView = popupView.findViewById(R.id.setName) as TextView
            var setDescTextView = popupView.findViewById(R.id.setDesc) as TextView
            val setCreateRequest = SetCreateRequest(name=setNameTextView.text.toString(), desc = setDescTextView.text.toString())
            SetService.addSet(request = setCreateRequest).enqueue(object: Callback<Void>{
                override fun onFailure(call: Call<Void>?, t: Throwable?) {
                    Log.e("ADD_SET", t.toString())
                }

                override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                    Log.i("ADD_SET", "add set repsonse received successfully")
                    if(response!!.isSuccessful){
                        Log.i("ADD_SET", "add set repsonse received successfully")
                        popupWindow.dismiss()
                    }
                }
            })
        })
    }

}