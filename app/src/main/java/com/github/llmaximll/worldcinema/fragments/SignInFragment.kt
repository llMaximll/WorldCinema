package com.github.llmaximll.worldcinema.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.llmaximll.worldcinema.R
import com.github.llmaximll.worldcinema.common.CommonFunctions
import com.github.llmaximll.worldcinema.vm.SignInVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    interface Callbacks {
        fun onSignInFragment(token: Int?, fragmentNumber: Int)
    }

    private lateinit var viewModel: SignInVM
    private lateinit var cf: CommonFunctions
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button
    private lateinit var mailEditText: EditText
    private lateinit var passwordEditText: EditText
    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cf = CommonFunctions.get()
        viewModel = cf.initVM(this, SignInVM::class.java) as SignInVM
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_sign_in, container, false)

        signInButton = view.findViewById(R.id.signIn_button)
        signUpButton = view.findViewById(R.id.signUp_button)
        mailEditText = view.findViewById(R.id.mail_editText)
        passwordEditText = view.findViewById(R.id.password_editText)

        return view
    }

    override fun onStart() {
        super.onStart()
        signInButton.setOnClickListener {
            val mail = mailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val checkFields = viewModel.checkFields(requireContext(), mail, password)
            if (checkFields)
                viewModel.signIn(requireContext(), mail, password)
            successfulAuthentication()
        }
        signUpButton.setOnClickListener {
            callbacks?.onSignInFragment(null, 1)
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun successfulAuthentication() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.signIn.collect {
                if (it != "") {
                    callbacks?.onSignInFragment(it.toInt(), 0)
                }
            }
        }
    }

    companion object {
        fun newInstance(): SignInFragment = SignInFragment()
    }
}