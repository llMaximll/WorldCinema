package com.github.llmaximll.worldcinema.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.github.llmaximll.worldcinema.R

class SignInFragment : Fragment() {

    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_sign_in, container, false)

        signInButton = view.findViewById(R.id.signIn_button)
        signUpButton = view.findViewById(R.id.signUp_button)

        return view
    }

    override fun onStart() {
        super.onStart()
        signInButton.setOnClickListener {
            
        }
        signUpButton.setOnClickListener {

        }
    }

    companion object {
        fun newInstance(): SignInFragment = SignInFragment()
    }
}