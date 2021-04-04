package com.hfad.sapper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.hfad.sapper.R
import com.hfad.sapper.data.Cell

class CellAdapter(private var playingField: MutableList<Cell> = mutableListOf()) :
    RecyclerView.Adapter<CellAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_cell, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val cell = playingField[position]
        viewHolder.cellImage.text = cell.valueCell.toString()
    }

    override fun getItemCount() = playingField.size

    fun setPlayingField(playingField: MutableList<Cell>) {
        this.playingField = playingField
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cellImage: Button = view.findViewById(R.id.button1)
    }
}