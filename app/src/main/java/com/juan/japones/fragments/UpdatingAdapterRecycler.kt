package com.juan.japones.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juan.japones.R
import com.juan.japones.database.RoomDatabaseClass
import com.juan.japones.dataclasses.Words
import kotlinx.android.synthetic.main.deleting_item_holder.view.*

class UpdatingAdapterRecycler(myList:List<Words>, _db:RoomDatabaseClass) : RecyclerView.Adapter<UpdatingAdapterRecycler.WordHolder> (){

    private val list = myList
    private val checkList:MutableList<Boolean> = mutableListOf()
    private var hold:WordHolder? = null

    init {
        list.forEach{
            checkList.add(false)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        return WordHolder(LayoutInflater.from(parent.context).inflate(R.layout.deleting_item_holder,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        hold = holder
        holder.wordDefinition.text = list[position].word
        holder.wordMeaning.text = list[position].meaning

        holder.checkbox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked)
                {
                    checkList[position] = true

                }
                else
                    checkList[position] = false
            }
        })

    }

    fun getCheckList() : MutableList<Boolean> {
        return checkList
    }

    fun restartCheckList(pos:Int)
    {
        checkList[pos] = false
    }

    class WordHolder(holder: View) : RecyclerView.ViewHolder(holder)
    {
        var wordDefinition:TextView = holder.findViewById(R.id.word_definition)
        var wordMeaning:TextView = holder.findViewById(R.id.word_meaning)
        var checkbox:CheckBox = holder.findViewById(R.id.check_box)
    }


}