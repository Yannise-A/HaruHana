package com.example.test

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class KatakanaActivity : AppCompatActivity() {

    private val katakanaListe = mapOf(
        "ア" to "a", "イ" to "i", "ウ" to "u", "エ" to "e", "オ" to "o",
        "カ" to "ka", "キ" to "ki", "ク" to "ku", "ケ" to "ke", "コ" to "ko",
        "サ" to "sa", "シ" to "shi", "ス" to "su", "セ" to "se", "ソ" to "so",
        "タ" to "ta", "チ" to "chi", "ツ" to "tsu", "テ" to "te", "ト" to "to",
        "ナ" to "na", "ニ" to "ni", "ヌ" to "nu", "ネ" to "ne", "ノ" to "no",
        "ハ" to "ha", "ヒ" to "hi", "フ" to "fu", "ヘ" to "he", "ホ" to "ho",
        "マ" to "ma", "ミ" to "mi", "ム" to "mu", "メ" to "me", "モ" to "mo",
        "ヤ" to "ya", "ユ" to "yu", "ヨ" to "yo",
        "ラ" to "ra", "リ" to "ri", "ル" to "ru", "レ" to "re", "ロ" to "ro",
        "ワ" to "wa", "ヲ" to "wo", "ン" to "n"
    )

    private lateinit var currentKana: String
    private lateinit var katakanaTextView: TextView
    private lateinit var answerInput: EditText
    private lateinit var cardFrame: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.katakana)

        cardFrame = findViewById(R.id.card_frame)

        val backButton: ImageView = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        katakanaTextView = findViewById(R.id.katakana_character)
        answerInput = findViewById(R.id.answer_input)
        val soumettreButton: Button = findViewById(R.id.submit_button)
        val passerButton: Button = findViewById(R.id.passer_button)

        setRandomKatakana()

        soumettreButton.setOnClickListener {
            checkAnswer()
        }

        passerButton.setOnClickListener {
            setRandomKatakana()
        }
    }

    private fun setRandomKatakana(exclude: String? = null) {
        val availableKana = if (exclude != null) {
            katakanaListe.keys.filter { it != exclude }
        } else {
            katakanaListe.keys.toList()
        }

        currentKana = availableKana.random()
        katakanaTextView.text = currentKana
    }

    private fun checkAnswer() {
        val userAnswer = answerInput.text.toString().lowercase()
        val correctAnswer = katakanaListe[currentKana] ?: ""

        if (userAnswer == correctAnswer) {
            cardFrame.setBackgroundResource(R.drawable.correct_answer)
        } else {
            cardFrame.setBackgroundResource(R.drawable.wrong_answer)
        }

        cardFrame.postDelayed({
            setRandomKatakana(currentKana)
            cardFrame.setBackgroundResource(R.drawable.letter_card)
        }, 2000)
    }
}
