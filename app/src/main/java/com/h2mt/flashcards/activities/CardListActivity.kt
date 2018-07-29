package com.h2mt.flashcards.activities

import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.h2mt.flashcards.R
import com.h2mt.flashcards.dialogs.CreateCardDialogFragment
import com.h2mt.flashcards.models.Card
import com.h2mt.flashcards.models.Set
import com.h2mt.flashcards.recyclerviews.CardAdapter
import com.h2mt.flashcards.recyclerviews.CardListOperations
import com.h2mt.flashcards.services.CardService
import kotlinx.android.synthetic.main.activity_card_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException

class CardListActivity : AppCompatActivity(), DialogInterface.OnDismissListener, CardListOperations {

    private val TAG = "CardListActivity"

    lateinit var set: Set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)

        set = intent.getSerializableExtra(getString(R.string.extra_set_key)) as Set

        Log.d(TAG, set.toString())

        // Toolbar config
        card_list_toolbar.title = set.name
        setSupportActionBar(card_list_toolbar)

        cards_recycleview.visibility = View.GONE
        cards_recycleview.layoutManager = LinearLayoutManager(this)

        loadCardsBySetId(set.id)

        swipeContainer.setOnRefreshListener({
            loadCardsBySetId(set.id)
            swipeContainer.isRefreshing = false
        })

        create_card_fab.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val createCardDialog = CreateCardDialogFragment()
            createCardDialog.set = set
            val transaction = fragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.add(android.R.id.content, createCardDialog).addToBackStack(null).commit()
        }

    }

    override fun onDismiss(p0: DialogInterface?) {
        loadCardsBySetId(set.id)
    }


    private fun loadCardsBySetId(setId: Int){

        progress.visibility = View.VISIBLE
        cards_recycleview.visibility = View.GONE

        CardService.getCardsBySetId(setId).enqueue(object : Callback<List<Card>> {

            override fun onFailure(call: Call<List<Card>>?, t: Throwable?) {
                Log.e(TAG, t.toString())
                val message = when(t) {
                    is ConnectException -> getString(R.string.no_internet_connection)
                    else -> getString(R.string.something_went_wrong)
                }
                Snackbar.make(card_list_layout, message, Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Card>>?, response: Response<List<Card>>?) {
                response?.let {
                    if(response.isSuccessful){
                        val cardsList = response.body()
                        cardsList?.let {
                            if (cards_recycleview.adapter == null){
                                cards_recycleview.adapter = CardAdapter(cardsList as ArrayList<Card>, this@CardListActivity)
                            } else {
                                (cards_recycleview.adapter as CardAdapter).updateCardList(cardsList as ArrayList<Card>)
                            }

                        }
                        cards_recycleview.scrollToPosition(0)
                        cards_recycleview.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                    }
                }
            }

        })
    }

    override fun deleteCard(cardId: Int, callback: () -> Unit) {
        showLoading()
        CardService.deleteCard(cardId).enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                Log.e(TAG, t.toString())
                val message = when(t) {
                    is ConnectException -> getString(R.string.no_internet_connection)
                    else -> getString(R.string.something_went_wrong)
                }
                Snackbar.make(card_list_layout, message, Snackbar.LENGTH_LONG).show()
                hideLoading()
            }

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                callback()
                hideLoading()
            }

        })
    }

    private fun showLoading(){
        progress.visibility = View.VISIBLE
        card_list_layout.isEnabled = false
    }

    private fun hideLoading(){
        progress.visibility = View.GONE
        card_list_layout.isEnabled = true
    }

}