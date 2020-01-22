package com.juan.japones.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juan.japones.presenter.RecyclerAdapter
import com.juan.japones.R
import com.juan.japones.dataclasses.AnswerDataClass

class ResultsFragment(cont: Context, _list: MutableList<AnswerDataClass>) : Fragment() {

    val list = _list
    var recyclerView:RecyclerView? = null
    var recyclerAdapter:RecyclerAdapter? = null

    companion object {
        fun getInstance(cont: Context, _list:MutableList<AnswerDataClass>) : ResultsFragment
        {
            return ResultsFragment(cont, _list)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.recycle_parent_layout,container,false)
        recyclerView = v.findViewById(R.id.recycler_layout_linear)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = linearLayoutManager
        recyclerAdapter = RecyclerAdapter(list)
        recyclerView!!.adapter = recyclerAdapter
        recyclerAdapter!!.notifyDataSetChanged()
        return v
    }
}