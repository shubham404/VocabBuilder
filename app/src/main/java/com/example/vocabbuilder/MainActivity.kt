package com.example.vocabbuilder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileReader
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val ADD_STUPID_CODE=193
    private val wordToDefn =HashMap<String,String>()
    private val words=ArrayList<String>()
    private val defn=ArrayList<String>()
    private lateinit var myAdapter : ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //read dictionary and display random definitions
        val reader= Scanner(resources.openRawResource(R.raw.grewords))
        readDictFile(reader)
        val reader2 = Scanner(openFileInput("extrawords.txt"))
        readDictFile(reader2)

        setuplist()

        definitions_list.setOnItemClickListener { _, _, index, _ ->
            val word = the_word.text.toString()
            val defnCorrect = wordToDefn[word]
            val defnChosen = defn[index]
            if (defnChosen == defnCorrect) {
                Toast.makeText(this, "you got it right", Toast.LENGTH_SHORT).show()
                setuplist()
            } else {
                Toast.makeText(this, "wrong", Toast.LENGTH_SHORT).show()

            }

        }
    }

    private fun readDictFile(reader: Scanner)
    {
        while(reader.hasNextLine())
        {
            val line=reader.nextLine()
            Log.d("Marty","the line is $line")
            val pieces=line.split("\t")
            if(pieces.size >=2) {
                words.add(pieces[0])
                wordToDefn.put(pieces[0], pieces[1])
            }
        }

    }

    private fun setuplist()
    {
        defn.clear()
        //pick a random word
        val index= Random().nextInt(words.size)
        val word=words[index]
        the_word.text=word

        //pick the correct definition along with 4 other random definitions
        defn.add(wordToDefn[word]!!)
        words.shuffle()
        for( otherword in words.subList(0,5) )
        {
            if( otherword == word || defn.size == 5 ){
                continue
            }
            defn.add(wordToDefn[otherword]!!)
        }

        defn.shuffle()

        myAdapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,defn)
        definitions_list.adapter=myAdapter
    }

    fun addWordButtonClick(view : View)
    {
            val myIntent= Intent(this,AddNewWord::class.java)
            //myIntent.putExtra("teacher","marty")
            startActivityForResult(myIntent,ADD_STUPID_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, myIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, myIntent)
        if (requestCode == ADD_STUPID_CODE) {
            if (myIntent != null) {
                val word = myIntent.getStringExtra("word")
                val defn = myIntent.getStringExtra("defn")
                wordToDefn.put(word, defn)
                words.add(word)
            }
        }
    }

    override fun onStart()
    {
        super.onStart()
        Log.i("Marty","onStart was called")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle)
    {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("Marty","onRestore instance 1 was called")
        val current_word=savedInstanceState.getString("the_word")
        the_word.text=current_word
        val current_defn=savedInstanceState.getStringArrayList("defns")
        if(current_defn !=null) {
            defn.addAll(current_defn)
        }
        myAdapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,defn)
        definitions_list.adapter=myAdapter
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?)
    {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        Log.i("Marty","onRestore instance 2 was called")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Marty","onResume was called")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.i("Marty","onSaveInstance was called")
        val word=the_word.text.toString()
        outState.putString("the_word",word)
        outState.putStringArrayList("defns",defn)
    }

    override fun onPause() {
        super.onPause()
        Log.i("Marty","onPause was called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Marty","onStop was called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Marty","onDestroy was called")
    }

}
