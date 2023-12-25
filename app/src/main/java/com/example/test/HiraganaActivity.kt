package com.example.test

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
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

    private val dakuonHandakuonSet = mapOf(
        "が" to "ga", "ぎ" to "gi", "ぐ" to "gu", "げ" to "ge", "ご" to "go",
        "ざ" to "za", "じ" to "ji", "ず" to "zu", "ぜ" to "ze", "ぞ" to "zo",
        "だ" to "da", "ぢ" to "ji", "づ" to "zu", "で" to "de", "ど" to "do",
        "ば" to "ba", "び" to "bi", "ぶ" to "bu", "べ" to "be", "ぼ" to "bo",
        "ぱ" to "pa", "ぴ" to "pi", "ぷ" to "pu", "ぺ" to "pe", "ぽ" to "po"
    )

    private val yoonSet = mapOf(
        "きゃ" to "kya", "きゅ" to "kyu", "きょ" to "kyo",
        "しゃ" to "sha", "しゅ" to "shu", "しょ" to "sho",
        "ちゃ" to "cha", "ちゅ" to "chu", "ちょ" to "cho",
        "にゃ" to "nya", "にゅ" to "nyu", "にょ" to "nyo",
        "ひゃ" to "hya", "ひゅ" to "hyu", "ひょ" to "hyo",
        "みゃ" to "mya", "みゅ" to "myu", "みょ" to "myo",
        "りゃ" to "rya", "りゅ" to "ryu", "りょ" to "ryo",
        "ぎゃ" to "gya", "ぎゅ" to "gyu", "ぎょ" to "gyo",
        "じゃ" to "ja", "じゅ" to "ju", "じょ" to "jo",
        "びゃ" to "bya", "びゅ" to "byu", "びょ" to "byo",
        "ぴゃ" to "pya", "ぴゅ" to "pyu", "ぴょ" to "pyo"
    )

    private lateinit var currentKana: String
    private lateinit var hiraganaTextView: TextView
    private lateinit var answerInput: EditText
    private lateinit var cardFrame: FrameLayout
    private lateinit var preferences: SharedPreferences

    private val allHiragana = hiraganaListe + dakuonHandakuonSet + yoonSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hiragana)

        // Initialisation des vues
        cardFrame = findViewById(R.id.card_frame)
        hiraganaTextView = findViewById(R.id.hiragana_character)
        answerInput = findViewById(R.id.answer_input)
        val soumettreButton: Button = findViewById(R.id.submit_button)
        val passerButton: Button = findViewById(R.id.passer_button)
        val backButton: ImageView = findViewById(R.id.back_button)

        // Chargement des préférences
        preferences = getSharedPreferences("KanaPreferences", MODE_PRIVATE)

        // Définir le comportement des boutons
        backButton.setOnClickListener {
            finish()
        }

        soumettreButton.setOnClickListener {
            checkAnswer()
        }

        passerButton.setOnClickListener {
            setRandomHiragana()
        }

        // Initialiser le premier caractère Hiragana
        setRandomHiragana()
    }

    private fun setRandomHiragana(exclude: String? = null) {
        val gojuonSelected = preferences.getBoolean("Gojuon", true)
        val dakuonHandakuonSelected = preferences.getBoolean("DakuonHandakuon", false)
        val yoonSelected = preferences.getBoolean("Yoon", false)

        val filteredKana = allHiragana.filterKeys {
            (gojuonSelected && it in hiraganaListe) ||
                    (dakuonHandakuonSelected && it in dakuonHandakuonSet) ||
                    (yoonSelected && it in yoonSet)
        }.keys

        val availableKana = if (exclude != null) {
            filteredKana - exclude
        } else {
            filteredKana
        }

        currentKana = availableKana.random()
        hiraganaTextView.text = currentKana
    }

    private fun checkAnswer(){
        val userAnswer = answerInput.text.toString().lowercase()
        val correctAnswer = hiraganaListe[currentKana] ?: ""
        val soumettreButton: Button = findViewById(R.id.submit_button)
        val passerButton: Button = findViewById(R.id.passer_button)

        soumettreButton.isEnabled = false
        passerButton.isEnabled = false

        if(userAnswer == correctAnswer){
            cardFrame.setBackgroundResource(R.drawable.correct_answer)
        }else{
            cardFrame.setBackgroundResource(R.drawable.wrong_answer)
            answerInput.setText("Réponse : $correctAnswer")
        }

        cardFrame.postDelayed({
            setRandomHiragana(currentKana)
            cardFrame.setBackgroundResource(R.drawable.letter_card)
            soumettreButton.isEnabled = true
            passerButton.isEnabled = true
            answerInput.setText("")
        },2000 )
    }
}