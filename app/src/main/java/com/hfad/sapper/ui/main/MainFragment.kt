package com.hfad.sapper.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hfad.sapper.R
import com.hfad.sapper.adapter.CellAdapter
import com.hfad.sapper.adapter.ItemClickListener
import com.hfad.sapper.data.Cell
import kotlin.random.Random

const val FIELD_SIZE = 8
const val CLEAR_CELL = -1
const val MINE_CELL = 10
const val TRIGGER_MINE_CELL = 11

class MainFragment : Fragment(), ItemClickListener {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var playingFieldRecyclerView: RecyclerView


    private val cellAdapter: CellAdapter = CellAdapter()
    private var playingField: MutableList<Cell> = generatePlayingField()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        playingFieldRecyclerView = view.findViewById(R.id.playing_field)
        cellAdapter.setListener(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        playingFieldRecyclerView.layoutManager = GridLayoutManager(requireContext(), FIELD_SIZE)
        cellAdapter.setPlayingField(playingField)
        playingFieldRecyclerView.adapter = cellAdapter
    }

    private fun generatePlayingField(): MutableList<Cell> {
        val playingField: MutableList<Cell> = mutableListOf()
        for (i in 0 until FIELD_SIZE * FIELD_SIZE) {
            val cell = Cell(CLEAR_CELL)
            playingField.add(cell)
        }
        var count = 0
        while (count < FIELD_SIZE) {
            val randomCell = Random.nextInt(FIELD_SIZE * FIELD_SIZE)
            if (playingField[randomCell].valueCell != 10) {
                playingField[randomCell].valueCell = 10
                count++
            }
        }
        return playingField
    }

    override fun onItemClick(list: MutableList<Cell>, position: Int) {
        val value = when (list[position].valueCell) {
            MINE_CELL, TRIGGER_MINE_CELL -> TRIGGER_MINE_CELL
            else -> 0
        }
        cellAdapter.updatePlayingField(position,value)
    }
}