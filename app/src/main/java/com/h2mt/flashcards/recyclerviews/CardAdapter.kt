package com.h2mt.flashcards.recyclerviews

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.h2mt.flashcards.R
import com.h2mt.flashcards.models.Card
import kotlinx.android.synthetic.main.card_row_item.view.*

class CardAdapter (private var cardList: List<Card>) : RecyclerView.Adapter<CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val inflatedView: View = layoutInflater.inflate(R.layout.card_row_item, parent, false)
        return CardViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(viewHolder: CardViewHolder, position: Int) {
        val card = cardList[position]
        viewHolder.term.text = card.term
        viewHolder.definition.text = card.definition
    }

}

class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val term = view.term_tv!!
    val definition = view.definition_tv!!
}