package com.juan.japones.presenter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.juan.japones.R
import com.juan.japones.dataclasses.AnswerDataClass

private var list:MutableList<AnswerDataClass>? = null
private var context: Context? = null

class RecyclerAdapter(_list:MutableList<AnswerDataClass>) : RecyclerView.Adapter<RecyclerAdapter.WordHolder>()
{


    init {
        list = _list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : WordHolder {

        context = parent.context
        return WordHolder(LayoutInflater.from(parent.context).inflate(R.layout.holder_layout,parent,false))
    }

    override fun getItemCount() = list!!.size

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        holder.textViewWord.text = list!![position].question
        holder.textViewMeaning.text = list!![position].meaning
        holder.textViewAnswer.text = list!![position].myAnswer

        //Now we correct the questions:
        var meaning = holder.textViewMeaning.text.toString()
        var myAnswer = holder.textViewAnswer.text.toString()

        if (list!![position].meaning.equals(list!![position].myAnswer))//meaning.equals(myAnswer))
            holder.textViewAnswer.setTextColor(ContextCompat.getColor(context!!,R.color.green))
        else
            holder.textViewAnswer.setTextColor(ContextCompat.getColor(context!!,R.color.red))
    }

    class WordHolder(item: View) : RecyclerView.ViewHolder(item) {
        var textViewWord:TextView = item.findViewById(R.id.word)
        var textViewMeaning:TextView = item.findViewById(R.id.meaning)
        var textViewAnswer:TextView = item.findViewById(R.id.my_answer)
    }
}