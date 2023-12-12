package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout

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
}

