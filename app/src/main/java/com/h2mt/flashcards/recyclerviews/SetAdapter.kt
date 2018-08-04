package com.h2mt.flashcards.recyclerviews

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.h2mt.flashcards.R
import com.h2mt.flashcards.models.Set
import kotlinx.android.synthetic.main.card_row_item.view.*
import kotlinx.android.synthetic.main.set_row_item.view.*

class SetAdapter (private var setList: List<Set>) : RecyclerView.Adapter<SetViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val inflatedView: View = layoutInflater.inflate(R.layout.card_row_item, parent, false)
        return SetViewHolder(inflatedView)
    }

    override fun getItemCount(): Int{
        return setList.size
    }

    override fun onBindViewHolder(viewHolder: SetViewHolder, position: Int) {
        val set = setList[position]
        viewHolder.name.text = set.name
    }
}

class SetViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val name = view.name!!
}