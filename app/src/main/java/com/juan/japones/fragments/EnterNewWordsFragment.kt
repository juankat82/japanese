package com.juan.japones.fragments

import android.content.Context
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
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.juan.japones.R
import com.juan.japones.database.RoomDatabaseClass
import com.juan.japones.dataclasses.Words


class EnterNewWordsFragment(context: Context,_db:RoomDatabaseClass) : Fragment()
{
    private var wordText:EditText? = null
    private var meaningText:EditText? = null
    private var enterButton: Button? = null
    var db: RoomDatabaseClass? = _db

    companion object {
        fun getInstance(cont: Context, db: RoomDatabaseClass) : EnterNewWordsFragment
        {
            return EnterNewWordsFragment(cont,db)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view  = inflater.inflate(R.layout.enter_dictionary_words_layout,container,false)

        wordText = view.findViewById(R.id.enter_word_definition)
        meaningText = view.findViewById(R.id.enter_word_meaning)
        enterButton = view.findViewById(R.id.add_word)

        var word = ""
        var meaning = ""

        wordText!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                word = s.toString()
            }
        })

        meaningText!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                meaning = s.toString()
            }
        })


        enterButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!word.equals("") && !meaning .equals(""))
                {
                    enterInDatabase(word,meaning)
                    wordText!!.text.clear()
                    meaningText!!.text.clear()
                    wordText!!.requestFocus()
                }
                else
                    Toast.makeText(context,"Entrada no valida",Toast.LENGTH_SHORT).show()
            }
        })
//        enterIt()
        return view
    }

    private fun enterInDatabase(_word: String, _meaning:String)
    {
        val word = Words(null,_word,_meaning)
        WriteAsyncTask(db!!, word).execute()
    }

//    private fun enterIt()
//    {
//        //ERASE ME all together with calling to this function at enterIt()!!!!!!!!
//        val myString = "kaisha no eigyo bucho-director de ventas de una empresa,(mi) padre-chichi,dono-lord,kochira (formalidad y para que)-este formal (personas),nin-contador para personas,idioma ingles-eigo,eikoku-inglaterra,jin (que indica)-nacionalidad,musuko-(mi) hijo,dewa arimasen-no ser,furansu-francia,san (para quien formal o informal)-mr y mrs formal,chūgoku-china,nihonjin-japones,namae-nombre,wain-vino,musume-(mi) hija,kun (para quien)-chicos y amigos,meishi-tarjeta de visita,yoroshiku onegai shimasu-encantado de conocerle (formal),watashi no-mi,sensei-profesor,no-de,sama (para quien y grado de respeto)-mr y mrs (maximo respeto),beikokujin-estadounidense,kazoku-familia,watashi-yo,hajimemashite-mucho gusto,sai-años,chūgokujin-chino,shacho-manager,(nom) to moshimasu-me llamo (nom),kankokujin-koreano,(mi) madre-haha,chan (para quien)-chicas y amigas,supein-españa,arigato gozaimasu-muchas gracias (formal),beikoku-estados unidos,igirisu-inglaterra,go (que indica)-idioma,kanai-(mi) esposa,kyõdai-(mis) hermanos y hermanas,nihon-japon,shufu-ama de casa,desu-ser (presente),supeingo no sensei-profesor de español,janarisuto-periodista,kankoku-korea,supeinjin no sensei-profesor español,ginkõ-banco,ka (que indica)-pregunta,shujin-(mi) esposo,furansuno (y para que)-de francia (objetos),dozo yoroshiku-encantado de conocerle,padre (otra familia)-otõsan,madre (otra familia)-okãsan,hijo (otra familia)-musuko san,hija(otra familia)-musume san,marido (otra familia)-go shujin,esposa (otra familia)-okusan,hermanos y hermanas (otra familia)-go kyõdai,familia (otra)-go kazoku,esta persona (informal)-kocchi,buenos dias (formal)-ohayõ gozaimasu,buenos dias (informal)-ohayõ,buenos dias/tardes (desde 11 am)-konnichiwa,buenas tardes/noches (desde 5 pm)-konbanwa,buenas noches (antes de acostarse) formal-oyasumi nasai,buenas noches (antes de acostarse) informal-oyasumi,adios (formal)-sayõnara,adios(informal)-bai bai/ja mata ne,al salir de la oficina...-osaki ni,perdone/disculpe-sumimasen,esto-kore,eso-sore,aquello-are,cual?-dore,este (sustantivo)-kono (sustantivo),ese (sustantivo)-sono (sustantivo),aquel (sustantivo)-ano (sustantivo),que (sustantivo)?-dono (sustantivo), dewa arimasen- no ser (formal),ja arimasen-no ser (informal),ja nai arimasen-no ser (muy informal),uno-ichi,dos-ni,san-tres,cuatro-yo/yon,go-cinco,seis-roku,siete-nana,ocho-hachi,kyū-nueve,diez-jū,numero_nin-numero_personas,numero_sai-numero_anos,yonin-cuatro personas,bienvenido-yõkoso,disculpe la interrupcion-shitsurei shimasu,el placer es mio-kochira koso,ano-esto...,etto-esto...,periodico-shinbun,jū roku-dieciseis,y/tambien-soshite,y-to,libro-hon,uisuki-whisky,si (formal)-hai,si (informal)-un,No (formal)-lie,No (informal)-Uun,Ratificar afirmacion?-desho ne,...no?-desho,ne-enfatizar accion"
//        var list = myString.split(",")
//        list.forEach{
//            Log.i("Palabra: ","$it")
//            val word = it.split("-")[0]
//            val meaning = it.split("-")[1]
//            enterInDatabase(word,meaning)
//        }
//    }
}

private class WriteAsyncTask(_db: RoomDatabaseClass, _word: Words) : AsyncTask<Unit,Unit,Unit>()
{
    val db = _db
    val word = _word

    override fun doInBackground(vararg params: Unit?) {
        db.wordsDao().insert(word)
    }
}