package com.h2mt.flashcards.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import com.h2mt.flashcards.R
import com.h2mt.flashcards.models.Card
import com.h2mt.flashcards.models.CreateCardRequest
import com.h2mt.flashcards.models.Set
import com.h2mt.flashcards.services.CardService
import kotlinx.android.synthetic.main.activity_card_list.*
import kotlinx.android.synthetic.main.dialog_create_card.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException


@SuppressLint("ValidFragment")
class CardDialogFragment(val cardOperation: CardOperation) : DialogFragment() {

    private val TAG = "CardDialog"

    var set: Set? = null
    var card: Card? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater!!.inflate(R.layout.dialog_create_card, container, false)

        val toolbar = rootView.findViewById<Toolbar>(R.id.dialog_toolbar)
        setToolbarTitle(toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        val actionBar = (activity as AppCompatActivity).supportActionBar

        actionBar?.let {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel)
        }
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        if (cardOperation == CardOperation.UPDATE_CARD){
            term_edit_text.setText(card!!.term)
            definition_edit_text.setText(card!!.definition)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu!!.clear()
        activity.menuInflater.inflate(R.menu.menu_create_card, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.action_save -> saveCard()
            android.R.id.home ->{
                dismiss()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun dismissDialog(){
        dismiss()
        (activity as DialogInterface.OnDismissListener).onDismiss(dialog)
    }

    private fun setToolbarTitle(toolbar: Toolbar) {
        if (cardOperation == CardOperation.CREATE_CARD) {
            toolbar.title = getString(R.string.create_card)
        } else if (cardOperation == CardOperation.UPDATE_CARD) {
            toolbar.title = getString(R.string.update_card)
        }
    }

    private fun saveCard(){
        if(!validateInputs()){
            return
        }
        if(cardOperation == CardOperation.CREATE_CARD){
            createNewCard()
        }else if(cardOperation == CardOperation.UPDATE_CARD){
            updateCard()
        }
    }

    private fun validateInputs() : Boolean{
        var valid = true
        if(term_edit_text.text.toString().isEmpty()){
            valid = false
            term_edit_text.error = getString(R.string.this_field_is_required)
        }
        if(definition_edit_text.text.toString().isEmpty()){
            valid = false
            definition_edit_text.error = getString(R.string.this_field_is_required)
        }
        return valid
    }

    private fun updateCard() {
        val term = term_edit_text.text.toString()
        val definition = definition_edit_text.text.toString()
        var updatedCard = Card(card!!.id, term, definition, card!!.imageUrl, card!!.setId)
        CardService.updateCard(updatedCard.id, updatedCard).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                Log.e(TAG, t.toString())
                handleError(t)
            }

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                dismissDialog()
            }
        })
    }

    private fun createNewCard() {
        val term = term_edit_text.text.toString()
        val definition = definition_edit_text.text.toString()
        val request = CreateCardRequest(term, definition, set!!.id)
        CardService.createCard(request).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                Log.e(TAG, t.toString())
                handleError(t)
            }

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                dismissDialog()
            }

        })
    }

    private fun handleError(t: Throwable?) {
        val message = when (t) {
            is ConnectException -> getString(R.string.no_internet_connection)
            else -> getString(R.string.something_went_wrong)
        }
        Snackbar.make(card_list_layout, message, Snackbar.LENGTH_LONG).show()
    }

}

enum class CardOperation {
    CREATE_CARD, UPDATE_CARD
}
