package com.hfad.sapper.adapter

import com.hfad.sapper.data.Cell

interface ItemClickListener {
    fun onItemClick(list: MutableList<Cell>, position: Int)
}