package com.example.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class KatakanaFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate le layout pour ce fragment
        return inflater.inflate(R.layout.fragment_katakana, container, false)
    }

    // Ajoutez ici toute logique supplémentaire spécifique à ce fragment
}
