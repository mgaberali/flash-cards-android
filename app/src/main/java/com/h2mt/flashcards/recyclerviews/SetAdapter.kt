package com.h2mt.flashcards.recyclerviews

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.h2mt.flashcards.R
import com.h2mt.flashcards.activities.CardListActivity
import com.h2mt.flashcards.models.Set
import com.h2mt.flashcards.models.SetCreateRequest
import kotlinx.android.synthetic.main.card_row_item.view.*
import kotlinx.android.synthetic.main.set_row_item.view.*

class SetAdapter (private var setList: ArrayList<Set>, val setListOperations: SetListOperations) : RecyclerView.Adapter<SetViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val inflatedView: View = layoutInflater.inflate(R.layout.set_row_item, parent, false)
        return SetViewHolder(inflatedView)
    }

    override fun getItemCount(): Int{
        return setList.size
    }

    override fun onBindViewHolder(viewHolder: SetViewHolder, position: Int) {
        val set = setList[position]
        viewHolder.name.text = set.name

        viewHolder.name.setOnClickListener {
            setListOperations.openSet(Integer(set.id))
        }

        viewHolder.deleteButton.setOnClickListener{
            setListOperations.deleteSet(Integer(set.id))
            setList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, setList.size)
        }

        viewHolder.editButton.setOnClickListener{
            setListOperations.editSet(position, set, it)
        }
    }

    fun addSet(set: Set){
        setList.add(set)
        notifyItemInserted(setList.size)
    }

    fun updateSet(position: Int, setId: Integer, setData: SetCreateRequest) {
        var newSet = Set(setId as Int, setData.name, setData.desc, "ttt")
        setList.set(position, newSet)
        notifyItemChanged(position)
    }
}

class SetViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val name = view.name!!
    val deleteButton = view.deleteButton!!
    val editButton = view.editButton!!
}

interface SetListOperations{
    fun openSet(setId: Integer)
    fun deleteSet(setId: Integer)
    fun editSet(position: Int, setInfo: Set, it: View)
}