package com.juan.japones.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.room.Room
import androidx.room.RoomDatabase
import com.juan.japones.R
import com.juan.japones.database.RoomDatabaseClass

private const val DB_NAME = "Word Database"

class MainFragment : Fragment()
{
    private var fragmentEnterWords:EnterNewWordsFragment? = null
    private var fragmentGame:GuessingWordsFragment? = null
    private var fragmentErase:UpdateEraseFragment? = null
    private var fm: FragmentTransaction? = null
    private var startGuessingButton: Button? = null
    private var addNewWordsButton: Button? = null
    private var eraseButton: Button? = null
    private var db: RoomDatabase? = null

    companion object {
        fun getInstance(cont: Context) : MainFragment
        {
            return MainFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.main_fragment_layout,container,false)
        db = Room.databaseBuilder(context!!,RoomDatabaseClass::class.java,DB_NAME).build()
        startGuessingButton = view.findViewById(R.id.start_guessing_button)
        addNewWordsButton = view.findViewById(R.id.add_dictionary_entry_button)
        eraseButton = view.findViewById(R.id.erase_button)


        //fragments definition
        fragmentEnterWords = EnterNewWordsFragment.getInstance(context!!, (db as RoomDatabaseClass))
        fragmentGame = GuessingWordsFragment.getInstance(context!!, (db as RoomDatabaseClass))
        fragmentErase = UpdateEraseFragment.getInstance(context!!, (db as RoomDatabaseClass))


        fm = activity!!.supportFragmentManager.beginTransaction()

        startGuessingButton!!.setOnClickListener {
            fragmentGame.let {
                fm!!.replace(R.id.constraint_base_layout, it!!).addToBackStack("startGuessingButton").commit()
            }
        }

        addNewWordsButton!!.setOnClickListener {
            fragmentEnterWords.let {
                fm!!.replace(R.id.constraint_base_layout, it!!).addToBackStack("newEntry").commit()
            }
        }

        eraseButton!!.setOnClickListener {
            fragmentErase.let {
                fm!!.replace(R.id.constraint_base_layout, it!!).addToBackStack("erase_fragment").commit()
            }
        }


        return view
    }

    override fun onResume() {
        super.onResume()
        if (!this.isAdded)
        {
            activity!!.supportFragmentManager.beginTransaction().add(R.id.constraint_base_layout,getInstance(context!!)).commit()
        }
    }
}