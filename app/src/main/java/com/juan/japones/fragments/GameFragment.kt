package com.juan.japones.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import com.juan.japones.R
import com.juan.japones.dataclasses.AnswerDataClass
import com.juan.japones.dataclasses.Words

class GameFragment (cont: Context, _list:List<Words>): Fragment()
{
    private var currentPosition = 0
    private var wordList = _list
    private var questionTextView: TextView? = null
    private var answerEditText: EditText? = null
    private var nextButton: ImageButton? = null
    private var previousButton:ImageButton? = null
    private var finishButton:Button? = null
    private var questionAnswerList:MutableList<AnswerDataClass> = mutableListOf()
    private var iMM:InputMethodManager? = null

    companion object {
        fun getInstance(cont: Context, list:List<Words>) : GameFragment
        {
            return GameFragment(cont,list)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        wordList.forEach {
            questionAnswerList.add(AnswerDataClass (it.word, it.meaning, ""))
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.guess_layout,container,false)

        questionTextView = v.findViewById(R.id.question_textview)
        answerEditText = v.findViewById(R.id.answer_edittext)
        nextButton = v.findViewById(R.id.button_next)
        previousButton = v.findViewById(R.id.button_previous)
        finishButton = v.findViewById(R.id.button_finish)
        iMM = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        questionTextView!!.text = wordList[currentPosition].word
        Log.i("MYLIST: ","$wordList")
        answerEditText!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                questionAnswerList[currentPosition].myAnswer = s.toString()
            }
        })

        nextButton!!.setOnClickListener {
            //change questions and start from beggining if last one
            currentPosition = if (currentPosition == wordList.size-1) 0 else currentPosition+1
            questionTextView!!.text = questionAnswerList[currentPosition].question

            if (questionAnswerList[currentPosition].myAnswer.equals(""))
                answerEditText!!.setText("")
            else
                answerEditText!!.setText(questionAnswerList[currentPosition].myAnswer)
        }

        previousButton!!.setOnClickListener {
            //change questions and start from end if first one
            currentPosition = if (currentPosition == 0) wordList.size-1 else currentPosition-1
            questionTextView!!.text = questionAnswerList[currentPosition].question


            if (questionAnswerList[currentPosition].myAnswer.equals(""))
                answerEditText!!.setText("")
            else
                answerEditText!!.setText(questionAnswerList[currentPosition].myAnswer)
        }

        finishButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                calculateResult()
            }
        })
        return v
    }

    private fun calculateResult()
    {
        questionAnswerList.forEach {
           if (it.myAnswer.isEmpty() || it.myAnswer.isBlank())
               it.myAnswer = "No Contestado"
        }

        val resultsFragment = ResultsFragment.getInstance(context!!,questionAnswerList)
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.constraint_base_layout,resultsFragment).addToBackStack("results_fragment").commit()
    }

    override fun onResume() {
        super.onResume()
        questionAnswerList.forEach {
            if(it.myAnswer.equals("No Contestado"))
                it.myAnswer = ""
        }
    }
}