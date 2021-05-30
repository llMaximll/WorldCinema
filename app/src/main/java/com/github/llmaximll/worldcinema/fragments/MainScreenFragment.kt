package com.github.llmaximll.worldcinema.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.llmaximll.worldcinema.R

class MainScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_main_screen, container, false)



        return view
    }

    companion object {
        fun newInstance(): MainScreenFragment = MainScreenFragment()
    }
}