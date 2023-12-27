package com.example.test

import android.os.Bundle
import android.os.Build
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import android.os.VibrationEffect
import androidx.core.content.ContextCompat
import android.os.Vibrator
import android.util.Log

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

    private val dakuonHandakuonSet = mapOf(
        "ガ" to "ga", "ギ" to "gi", "グ" to "gu", "ゲ" to "ge", "ゴ" to "go",
        "ザ" to "za", "ジ" to "ji", "ズ" to "zu", "ゼ" to "ze", "ゾ" to "zo",
        "ダ" to "da", "ヂ" to "ji", "ヅ" to "zu", "デ" to "de", "ド" to "do",
        "バ" to "ba", "ビ" to "bi", "ブ" to "bu", "ベ" to "be", "ボ" to "bo",
        "パ" to "pa", "ピ" to "pi", "プ" to "pu", "ペ" to "pe", "ポ" to "po"
    )
    private val yoonSet = mapOf(
        "キャ" to "kya", "キュ" to "kyu", "キョ" to "kyo",
        "シャ" to "sha", "シュ" to "shu", "ショ" to "sho",
        "チャ" to "cha", "チュ" to "chu", "チョ" to "cho",
        "ニャ" to "nya", "ニュ" to "nyu", "ニョ" to "nyo",
        "ヒャ" to "hya", "ヒュ" to "hyu", "ヒョ" to "hyo",
        "ミャ" to "mya", "ミュ" to "myu", "ミョ" to "myo",
        "リャ" to "rya", "リュ" to "ryu", "リョ" to "ryo",
        "ギャ" to "gya", "ギュ" to "gyu", "ギョ" to "gyo",
        "ジャ" to "ja", "ジュ" to "ju", "ジョ" to "jo",
        "ビャ" to "bya", "ビュ" to "byu", "ビョ" to "byo",
        "ピャ" to "pya", "ピュ" to "pyu", "ピョ" to "pyo"
    )



    private lateinit var currentKana: String
    private lateinit var katakanaTextView: TextView
    private lateinit var answerInput: EditText
    private lateinit var cardFrame: FrameLayout
    private lateinit var preferences: SharedPreferences

    private val allKatakana = katakanaListe + dakuonHandakuonSet + yoonSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.katakana)

        preferences = getSharedPreferences("KanaPreferences", MODE_PRIVATE)

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
        val gojuonSelected = preferences.getBoolean("KatakanaGojuon", true)
        val dakuonHandakuonSelected = preferences.getBoolean("KatakanaDakuonHandakuon", false)
        val yoonSelected = preferences.getBoolean("KatakanaYoon", false)

        val filteredKana = allKatakana.filterKeys {
            (gojuonSelected && it in katakanaListe) ||
                    (dakuonHandakuonSelected && it in dakuonHandakuonSet) ||
                    (yoonSelected && it in yoonSet)
        }.keys

        val availableKana = if (exclude != null) {
            filteredKana - exclude
        } else {
            filteredKana
        }

        currentKana = availableKana.random()
        katakanaTextView.text = currentKana
    }

    private fun checkAnswer() {
        val userAnswer = answerInput.text.toString().trim().lowercase()
        val correctAnswer = katakanaListe[currentKana] ?: ""

        val soumettreButton: Button = findViewById(R.id.submit_button)
        val passerButton: Button = findViewById(R.id.passer_button)

        soumettreButton.isEnabled = false
        passerButton.isEnabled = false

        if (userAnswer == correctAnswer) {
            cardFrame.setBackgroundResource(R.drawable.correct_answer)
        } else {
            cardFrame.setBackgroundResource(R.drawable.wrong_answer)
            val responseText = "Réponse : ${correctAnswer.uppercase()}"
            answerInput.setText(responseText)
            vibratePhone()
        }

        cardFrame.postDelayed({
            setRandomKatakana(currentKana)
            cardFrame.setBackgroundResource(R.drawable.letter_card)
            soumettreButton.isEnabled = true
            passerButton.isEnabled = true
            answerInput.setText("")
        }, 2000)
    }

    private fun vibratePhone() {
        val vibrator = ContextCompat.getSystemService(this, Vibrator::class.java)
        vibrator?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Pour Android Oreo (API 26) et plus
                val vibrationEffect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
                it.vibrate(vibrationEffect)
                Log.d("Vibration", "Vibration for 500ms (Oreo and above)")
            } else {
                // Pour les versions antérieures
                it.vibrate(500)
                Log.d("Vibration", "Vibration for 500ms (Pre-Oreo)")
            }
        }
    }
}
