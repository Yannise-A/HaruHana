package com.example.test

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.random.Random

class HiraganaActivity : AppCompatActivity() {

    private val hiraganaListe = mapOf(
        "あ" to "a", "い" to "i", "う" to "u", "え" to "e", "お" to "o",
        "か" to "ka", "き" to "ki", "く" to "ku", "け" to "ke", "こ" to "ko",
        "さ" to "sa", "し" to "shi", "す" to "su", "せ" to "se", "そ" to "so",
        "た" to "ta", "ち" to "chi", "つ" to "tsu", "て" to "te", "と" to "to",
        "な" to "na", "に" to "ni", "ぬ" to "nu", "ね" to "ne", "の" to "no",
        "は" to "ha", "ひ" to "hi", "ふ" to "fu", "へ" to "he", "ほ" to "ho",
        "ま" to "ma", "み" to "mi", "む" to "mu", "め" to "me", "も" to "mo",
        "や" to "ya", "ゆ" to "yu", "よ" to "yo",
        "ら" to "ra", "り" to "ri", "る" to "ru", "れ" to "re", "ろ" to "ro",
        "わ" to "wa", "を" to "wo", "ん" to "n"
    )

    private lateinit var currentKana: String
    private lateinit var hiraganaTextView: TextView
    private lateinit var answerInput: EditText
    private lateinit var cardFrame: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hiragana)

        cardFrame = findViewById(R.id.card_frame)

        val backButton: ImageView = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }


        hiraganaTextView = findViewById(R.id.hiragana_character)
        answerInput = findViewById(R.id.answer_input)
        val soumettreButton: Button = findViewById(R.id.submit_button)
        val passerButton: Button = findViewById(R.id.passer_button)



        setRandomHiragana()

        soumettreButton.setOnClickListener {
            checkAnswer()
        }

        passerButton.setOnClickListener {
            setRandomHiragana()
        }
    }

    private fun setRandomHiragana(exclude: String? = null) {
        val availableKana = if(exclude != null ) {
            hiraganaListe.keys.filter{it != exclude}
        }else{
            hiraganaListe.keys.toList()
        }

        currentKana = availableKana.random()
        hiraganaTextView.text = currentKana
    }

    private fun checkAnswer(){
        val userAnswer = answerInput.text.toString().lowercase()
        val correctAnswer = hiraganaListe[currentKana] ?: ""

        if(userAnswer == correctAnswer){
            cardFrame.setBackgroundResource(R.drawable.correct_answer)
        }else{
            cardFrame.setBackgroundResource(R.drawable.wrong_answer)
        }

        cardFrame.postDelayed({
            setRandomHiragana(currentKana)
            cardFrame.setBackgroundResource(R.drawable.letter_card)
        },2000 )
    }
}