package com.juan.japones.fragments

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IntegerRes
import androidx.fragment.app.Fragment
import com.juan.japones.R
import com.juan.japones.database.RoomDatabaseClass
import com.juan.japones.dataclasses.Words

class GuessingWordsFragment(context: Context, _db: RoomDatabaseClass) : Fragment()
{
    private var views: View? = null
    private var db: RoomDatabaseClass? = _db
    private var wordList:List<Words>? = null
    private var howManyQuestionsText: TextView? = null
    private var answerQuestions: EditText? = null
    private var button: Button? = null
    private var maxQuestions = 0
    private var questions:Int = 0

    companion object {
        fun getInstance(cont: Context, db:RoomDatabaseClass) : GuessingWordsFragment
        {
            return GuessingWordsFragment(cont,db)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        views  = inflater.inflate(R.layout.game_layout,container,false)
        wordList = readFromDatabase().shuffled()
        maxQuestions = wordList!!.size
        questions = 0
        howManyQuestionsText = views!!.findViewById(R.id.how_many_questions)
        val questionString = "${howManyQuestionsText!!.text} (${maxQuestions} max)"

        howManyQuestionsText!!.text = questionString
        answerQuestions = views!!.findViewById(R.id.question_edittext)
        answerQuestions!!.setText(questions.toString())
        answerQuestions!!.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var exceptionValue = 1
                try {
                    questions = s.toString().toInt()
                }
                catch(e:Exception )
                {
                    exceptionValue = 0
                }
                if (count > 0 && exceptionValue == 1)//!s.toString().equals(""))
                {
                    questions = s.toString().toInt()
                }
            }
        })


        button = views!!.findViewById(R.id.start_button)
        button!!.setOnClickListener {
            Log.i("Question number","$questions")
            if (questions > 0 && questions <= maxQuestions ) {
                val finalList =wordList!!.take(questions)
                val gameFragment = GameFragment.getInstance(context!!,finalList)

                activity!!.supportFragmentManager.beginTransaction().replace(R.id.constraint_base_layout,gameFragment).addToBackStack("game_fragment").commit()
            } else
                Toast.makeText(context,"Numero de preguntas no valido",Toast.LENGTH_SHORT).show()
        }
        return views
    }

    private fun readFromDatabase() : List<Words>
    {
        return ReadAsyncTask(db!!).execute().get()
    }


}

private class ReadAsyncTask(_db: RoomDatabaseClass) : AsyncTask<Unit, Unit, List<Words>>()
{
    val db = _db

    override fun doInBackground(vararg params: Unit?): List<Words> {
        return db.wordsDao().query()
    }
}