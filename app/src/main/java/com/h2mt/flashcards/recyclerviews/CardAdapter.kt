package com.h2mt.flashcards.recyclerviews

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
        setAnimation(viewHolder.itemView)
    }

    fun updateCardList(cards: List<Card>) {
        DiffUtil.calculateDiff(CardRowDiffCallback(cards, cardList), false).dispatchUpdatesTo(this)
        cardList = cards
    }

    private fun setAnimation(viewToAnimate: View) {
        if (viewToAnimate.animation == null) {
            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, android.R.anim.slide_in_left)
            viewToAnimate.animation = animation
        }
    }

}

class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val term = view.term_tv!!
    val definition = view.definition_tv!!
}

class CardRowDiffCallback (private val newRows: List<Card>, private val oldRows: List<Card>) :
        DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRow = oldRows[oldItemPosition]
        val newRow = newRows[newItemPosition]
        return oldRow.id == newRow.id
    }

    override fun getOldListSize(): Int = oldRows.size

    override fun getNewListSize(): Int = newRows.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRow = oldRows[oldItemPosition]
        val newRow = newRows[newItemPosition]
        return oldRow == newRow
    }

}