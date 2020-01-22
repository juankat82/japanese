package com.juan.japones.fragments

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juan.japones.R
import com.juan.japones.database.RoomDatabaseClass
import com.juan.japones.dataclasses.Words
import kotlinx.android.synthetic.main.deleting_item_holder.view.*

private const val REQUEST_DIALOG_DATA = 666
private const val WORD_DATA = "my_modified_word"

class UpdateEraseFragment (_context:Context, _db: RoomDatabaseClass): Fragment()
{
    val db = _db
    var list:MutableList<Words>? = null
    var recyclerView:RecyclerView? = null
    var adapter:UpdatingAdapterRecycler? = null
    var linearLayoutManager:LinearLayoutManager? = null
    var eraseSelectedButton: Button? = null
    var eraseAllButton:Button? = null
    var updateButton:Button? = null
    var pos:Int = -1

    companion object {
        fun getInstance(cont: Context, _db:RoomDatabaseClass) : UpdateEraseFragment
        {
            return UpdateEraseFragment(cont,_db)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, _container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.erase_base_layout,_container,false)
        list = ReadAsyncTask(db).execute().get()
        recyclerView = v.findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = linearLayoutManager
        adapter = UpdatingAdapterRecycler(list!!,db)
        recyclerView!!.adapter = adapter


        updateButton = v.findViewById(R.id.modify_entry_button)
        updateButton!!.setOnClickListener {
            val checkList = adapter!!.getCheckList()

            // var lstBool = adapter!!.getCheckList()

            if (!adapter!!.getCheckList().contains(true))
                Toast.makeText(context, "No se ha seleccionado ningun indice", Toast.LENGTH_SHORT).show()
            else {
                AlertDialog.Builder(context!!).setTitle(getString(R.string.modify_word_string))
                    .setMessage(getString(R.string.wanna_modify))
                    .setNegativeButton(getString(R.string.no), null)
                    .setPositiveButton(getString(R.string.yes), { _dialog, _which ->
                        for (i in checkList.indices) {
                            //counts how many items are visible in the recyclerview
                            var divider = 0
                            for (j in 0..20)
                            {
                                if (recyclerView!!.getChildAt(j) != null)
                                    divider = j
                            }

                            if (checkList[i] == true) {
                                val dialog = CustomDialogFragment.getInstance(db, list!![i], i)
                                dialog.setTargetFragment(this, REQUEST_DIALOG_DATA)
                                dialog.show(fragmentManager!!, "show_custom_dialog")
                                recyclerView!!.getChildAt(i%divider).check_box.isChecked = false
                                pos = i
                            }
                        }
                    }).show()
            }

        }

        eraseSelectedButton = v.findViewById(R.id.erase_selected)
        eraseSelectedButton!!.setOnClickListener{
            val checkList = adapter!!.getCheckList()

            if (!adapter!!.getCheckList().contains(true))
                Toast.makeText(context,"No se ha seleccionado ningun indice",Toast.LENGTH_SHORT).show()
            else
                AlertDialog.Builder(context!!).
                setTitle(context!!.getString(R.string.borrar_string)).
                setMessage(context!!.getString(R.string.are_you_sure_string)).
                setNegativeButton(context!!.getString(R.string.no),null).
                setPositiveButton(context!!.getString(R.string.yes), { dialog, which ->
                    for (i in checkList.lastIndex downTo 0)
                    {
                        if (checkList[i] == true)
                        {

                            EraseAsyncTask(db,list!![i]).execute()
                            list!!.removeAt(i)
                            adapter!!.notifyItemRemoved(i)
                            checkList.removeAt(i)
                        }
                    }
                }).show()
            adapter!!.notifyDataSetChanged()
        }

        eraseAllButton = v.findViewById(R.id.erase_everything)
        eraseAllButton!!.setOnClickListener{
            if (list!!.isNullOrEmpty())
                Toast.makeText(context,"No hay nada que borrar",Toast.LENGTH_SHORT).show()
            else {
                AlertDialog.Builder(context!!).
                    setTitle(context!!.getString(R.string.borrar_todo_string)).
                    setMessage(context!!.getString(R.string.are_you_sure_string)).
                    setNegativeButton(context!!.getString(R.string.no),null).
                    setPositiveButton(context!!.getString(R.string.yes), { dialog, which ->
                        EraseAllAsyncTask(db, list as List<Words>).execute()
                        list!!.clear()
                        adapter!!.notifyDataSetChanged()
                    }).show()
            }
        }
        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var word:Words? = null

        if (resultCode != Activity.RESULT_OK)
            return


        if (requestCode == REQUEST_DIALOG_DATA)
        {
            val wordString = data!!.getStringExtra(WORD_DATA) ?: ""

            wordString.substring(1,wordString.lastIndex).split(", ").let {
               word = Words(it[0].split("=")[1].toInt(), it[1].split("=")[1], it[2].split("=")[1])
            }
        }

        UpdaterAsyncTask(db,word!!).execute()
        adapter!!.notifyDataSetChanged()


    }

    private class ReadAsyncTask(_db: RoomDatabaseClass) : AsyncTask<Unit, Unit, MutableList<Words>>()
    {
        val db = _db

        override fun doInBackground(vararg params: Unit?): MutableList<Words> {
            return db.wordsDao().query()
        }
    }

    private class EraseAsyncTask(_db: RoomDatabaseClass, _word:Words) : AsyncTask<Unit, Unit, Unit>()
    {
        val db = _db
        val word = _word
        override fun doInBackground(vararg params: Unit?) {
            db.wordsDao().deleteOne(word)
        }
    }

    private class EraseAllAsyncTask(_db: RoomDatabaseClass, _list:List<Words>) : AsyncTask<Unit, Unit, Unit>()
    {
        val db = _db
        val wordList = _list
        override fun doInBackground(vararg params: Unit?) {
            db.wordsDao().deleteAll()
        }
    }

    private class UpdaterAsyncTask(_db: RoomDatabaseClass, _word: Words) : AsyncTask<Unit, Unit, Unit>()
    {
        val db = _db
        val word = _word
        override fun doInBackground(vararg params: Unit?) {
            db.wordsDao().updateRegistry(word)
        }
    }
}