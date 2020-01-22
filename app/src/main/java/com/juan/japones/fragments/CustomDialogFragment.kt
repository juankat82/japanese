package com.juan.japones.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.juan.japones.R
import com.juan.japones.database.RoomDatabaseClass
import com.juan.japones.dataclasses.Words

private const val WORD_DATA = "my_modified_word"

class CustomDialogFragment(_db:RoomDatabaseClass, _word:Words, _position:Int) : DialogFragment()
{
    val word = _word
    var wordDefinitionDialog: EditText? = null
    var wordMeaningDialog: EditText? = null
    var wordString:String =""
    var meaningString:String = ""
    var position = _position

    companion object {
        fun getInstance(_db:RoomDatabaseClass, _word:Words, _position:Int) : CustomDialogFragment
        {
            var args = Bundle()
            var fragment = CustomDialogFragment(_db, _word, _position)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = activity!!.layoutInflater

        val builder = AlertDialog.Builder(context!!)

        val view = inflater.inflate(R.layout.custom_dialog_fragment_layout,null)
        wordDefinitionDialog = view.findViewById(R.id.word_definition_dialog)
        wordDefinitionDialog!!.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                wordString = s.toString()
            }
        })

        wordMeaningDialog = view.findViewById(R.id.word_meaning_dialog)
        wordMeaningDialog!!.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                meaningString = s.toString()
            }
        })

        builder.setView(view)
        return builder.setNegativeButton(R.string.no, null).
                setPositiveButton(R.string.yes, { _dialog, _which ->
                    if (!wordString.equals("") && !meaningString.equals(""))
                    {
                        word.apply {
                            word = wordString
                            meaning = meaningString
                        }
                        sendResult(Activity.RESULT_OK,word)
                    }
                    else
                        Toast.makeText(context,"Entrada no valida",Toast.LENGTH_SHORT).show()
                }).create()
    }

    private fun sendResult(resultCode:Int, word:Words)
    {
        if (targetFragment == null)
            return

        val intent = Intent()
        val wordString = word.toString()
        intent.putExtra(WORD_DATA,wordString)//we will have to pass it intol a WORD type later on
        targetFragment!!.onActivityResult(targetRequestCode,resultCode,intent)
    }
}