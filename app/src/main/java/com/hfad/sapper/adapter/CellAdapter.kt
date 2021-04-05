package com.hfad.sapper.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.hfad.sapper.R
import com.hfad.sapper.data.Cell
import com.hfad.sapper.ui.main.CLEAR_CELL
import com.hfad.sapper.ui.main.FIELD_SIZE
import com.hfad.sapper.ui.main.MINE_CELL
import com.hfad.sapper.ui.main.TRIGGER_MINE_CELL

class CellAdapter(private var playingField: MutableList<Cell> = mutableListOf()) :
    RecyclerView.Adapter<CellAdapter.ViewHolder>() {
    private var itemListener: ItemClickListener? = null

    fun setListener(itemClickListener: ItemClickListener) {
        this.itemListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_cell, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val cell = playingField[position]
        when (cell.valueCell) {
            CLEAR_CELL, MINE_CELL -> {
                viewHolder.cellButton.setBackgroundColor(Color.GRAY)
            }
            TRIGGER_MINE_CELL -> {
                viewHolder.cellButton.setBackgroundColor(Color.RED)
            }
            else -> {
                viewHolder.cellButton.setBackgroundColor(Color.WHITE)
                viewHolder.cellButton.text = countMineAround(position).toString()
            }
        }
        viewHolder.cellButton.setOnClickListener {
            itemListener?.onItemClick(playingField, position)
        }
    }

    override fun getItemCount() = playingField.size

    fun setPlayingField(playingField: MutableList<Cell>) {
        this.playingField = playingField
        notifyDataSetChanged()
    }

    fun updatePlayingField(position: Int, value: Int) {
        playingField[position].valueCell = value
        notifyItemChanged(position)
    }

    private fun countMineAround(position: Int): Int {
        var count = 0
        val isFreeBottom = position < FIELD_SIZE * FIELD_SIZE - FIELD_SIZE
        val isFreeUp = position >= FIELD_SIZE
        val isFreeLeft = position % FIELD_SIZE != 0
        val isFreeRight = (position + 1) % FIELD_SIZE != 0
        if (isFreeBottom) {
            if ((playingField[position + FIELD_SIZE].valueCell == MINE_CELL) ||
                (playingField[position + FIELD_SIZE].valueCell == TRIGGER_MINE_CELL)
            ) count++
        }
        if (isFreeUp) {
            if ((playingField[position - FIELD_SIZE].valueCell == MINE_CELL) ||
                (playingField[position - FIELD_SIZE].valueCell == TRIGGER_MINE_CELL)
            ) count++
        }
        if (isFreeLeft) {
            if ((playingField[position - 1].valueCell == MINE_CELL) ||
                (playingField[position - 1].valueCell == TRIGGER_MINE_CELL)
            ) count++
        }
        if (isFreeRight) {
            if ((playingField[position + 1].valueCell == MINE_CELL) ||
                (playingField[position + 1].valueCell == TRIGGER_MINE_CELL)
            ) count++
        }
        if (isFreeBottom && isFreeRight) {
            if ((playingField[position + FIELD_SIZE + 1].valueCell == MINE_CELL) ||
                (playingField[position + FIELD_SIZE + 1].valueCell == TRIGGER_MINE_CELL)
            ) count++
        }
        if (isFreeBottom && isFreeLeft) {
            if ((playingField[position + FIELD_SIZE - 1].valueCell == MINE_CELL) ||
                (playingField[position + FIELD_SIZE - 1].valueCell == TRIGGER_MINE_CELL)
            ) count++
        }
        if (isFreeUp && isFreeRight) {
            if ((playingField[position - FIELD_SIZE + 1].valueCell == MINE_CELL) ||
                (playingField[position - FIELD_SIZE + 1].valueCell == TRIGGER_MINE_CELL)
            ) count++
        }
        if (isFreeUp && isFreeLeft) {
            if ((playingField[position - FIELD_SIZE - 1].valueCell == MINE_CELL) ||
                (playingField[position - FIELD_SIZE - 1].valueCell == TRIGGER_MINE_CELL)
            ) count++
        }
        return count
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cellButton: Button = view.findViewById(R.id.button1)
    }
}