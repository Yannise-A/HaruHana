package com.example.test

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.RelativeLayout
import android.content.Intent
import android.view.LayoutInflater
import android.widget.ToggleButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hiraganaButton: RelativeLayout = findViewById(R.id.hiragana_button)
        val katakanaButton: RelativeLayout = findViewById(R.id.katakana)
        val kanaTableButton: Button = findViewById(R.id.kana_table_button)

        hiraganaButton.setOnClickListener {
            val intent = Intent(this, HiraganaActivity::class.java)
            startActivity(intent)
        }
        katakanaButton.setOnClickListener {
            val intent = Intent(this, KatakanaActivity::class.java)
            startActivity(intent)
        }


        kanaTableButton.setOnClickListener {
            val kanaTableFragment = KanaTableFragment()
            kanaTableFragment.show(supportFragmentManager, "kana_table_tag")
        }
    }

    fun showOptionsDialog(view: View) {
        // Inflate the dialog with the custom view
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_options, null)

        // Retrieve the current state of the preferences
        val prefs = getSharedPreferences("KanaPreferences", MODE_PRIVATE)
        val hiraganaGojuon = prefs.getBoolean("HiraganaGojuon", true)
        val hiraganaDakuonHandakuon = prefs.getBoolean("HiraganaDakuonHandakuon", false)
        val hiraganaYoon = prefs.getBoolean("HiraganaYoon", false)
        val katakanaGojuon = prefs.getBoolean("KatakanaGojuon", true)
        val katakanaDakuonHandakuon = prefs.getBoolean("KatakanaDakuonHandakuon", false)
        val katakanaYoon = prefs.getBoolean("KatakanaYoon", false)

        // Set the current state of the ToggleButtons in the dialog
        val hiraganaGojuonToggle = dialogView.findViewById<ToggleButton>(R.id.toggle_hiragana_gojuon)
        val hiraganaDakuonHandakuonToggle = dialogView.findViewById<ToggleButton>(R.id.toggle_hiragana_dakuon_handakuon)
        val hiraganaYoonToggle = dialogView.findViewById<ToggleButton>(R.id.toggle_hiragana_yoon)
        val katakanaGojuonToggle = dialogView.findViewById<ToggleButton>(R.id.toggle_katakana_gojuon)
        val katakanaDakuonHandakuonToggle = dialogView.findViewById<ToggleButton>(R.id.toggle_katakana_dakuon_handakuon)
        val katakanaYoonToggle = dialogView.findViewById<ToggleButton>(R.id.toggle_katakana_yoon)

        hiraganaGojuonToggle.isChecked = hiraganaGojuon
        hiraganaDakuonHandakuonToggle.isChecked = hiraganaDakuonHandakuon
        hiraganaYoonToggle.isChecked = hiraganaYoon
        katakanaGojuonToggle.isChecked = katakanaGojuon
        katakanaDakuonHandakuonToggle.isChecked = katakanaDakuonHandakuon
        katakanaYoonToggle.isChecked = katakanaYoon

        // Build the dialog
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Retrieve and set up the buttons from the custom view
        val buttonOk = dialogView.findViewById<Button>(R.id.button_ok)
        val buttonCancel = dialogView.findViewById<Button>(R.id.button_cancel)

        // Set up the click listeners for the custom buttons
        buttonOk.setOnClickListener {
            // Save the selections
            saveSelections(
                hiraganaGojuonToggle.isChecked,
                hiraganaDakuonHandakuonToggle.isChecked,
                hiraganaYoonToggle.isChecked,
                katakanaGojuonToggle.isChecked,
                katakanaDakuonHandakuonToggle.isChecked,
                katakanaYoonToggle.isChecked
            )
            dialog.dismiss() // Dismiss the dialog
        }

        buttonCancel.setOnClickListener {
            dialog.dismiss() // Dismiss the dialog
        }

        // Show the custom dialog
        dialog.show()
    }

    private fun saveSelections(
        hiraganaGojuon: Boolean,
        hiraganaDakuonHandakuon: Boolean,
        hiraganaYoon: Boolean,
        katakanaGojuon: Boolean,
        katakanaDakuonHandakuon: Boolean,
        katakanaYoon: Boolean
    ) {
        val prefs = getSharedPreferences("KanaPreferences", MODE_PRIVATE)
        with(prefs.edit()) {
            putBoolean("HiraganaGojuon", hiraganaGojuon)
            putBoolean("HiraganaDakuonHandakuon", hiraganaDakuonHandakuon)
            putBoolean("HiraganaYoon", hiraganaYoon)
            putBoolean("KatakanaGojuon", katakanaGojuon)
            putBoolean("KatakanaDakuonHandakuon", katakanaDakuonHandakuon)
            putBoolean("KatakanaYoon", katakanaYoon)
            apply()
        }
    }
}

