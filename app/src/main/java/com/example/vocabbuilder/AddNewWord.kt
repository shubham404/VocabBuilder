package com.example.vocabbuilder

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add_new_word.*
import java.io.PrintStream

class AddNewWord : AppCompatActivity() {

    val WORDS_FILE_NAME="extrawords.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_word)

        //val teacher=intent.getStringExtra("teacher")
        //word_to_add.setText(teacher)
    }

    fun letsAddTheWord(view : View)
    {
        // we are extracting the word and storing it in a variable
        val word=word_to_add.text.toString()

        //we are extracting the definition and storing it in a variable
        val defn=word_definition.text.toString()

        //we combine the above two into one space seperated word pair to suit the format of wordsgre.txt file
        val line="$word\t$defn"

        val outStream=PrintStream(openFileOutput(WORDS_FILE_NAME, Context.MODE_PRIVATE))
        outStream.println(line)
        outStream.close()

        // goback to my mainactivity
        val myIntent= Intent()
        myIntent.putExtra("word",word)
        myIntent.putExtra("defn",defn)
        setResult(RESULT_OK,myIntent)
        finish()
    }
}
