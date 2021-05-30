package com.github.llmaximll.worldcinema.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.github.llmaximll.worldcinema.R
import com.github.llmaximll.worldcinema.common.CommonFunctions
import com.github.llmaximll.worldcinema.vm.SignUpVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    interface Callbacks {
        fun onSignUpFragment(fragment: Int)
    }

    private lateinit var viewModel: SignUpVM
    private lateinit var nameEditText: EditText
    private lateinit var secondNameEditText: EditText
    private lateinit var mailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var secondPasswordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var signInButton: Button
    private lateinit var cf: CommonFunctions
    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cf = CommonFunctions.get()
        viewModel = cf.initVM(this, SignUpVM::class.java) as SignUpVM
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_sign_up, container, false)

        nameEditText = view.findViewById(R.id.name_editText)
        secondNameEditText = view.findViewById(R.id.second_name_editText)
        mailEditText = view.findViewById(R.id.mail_editText)
        passwordEditText = view.findViewById(R.id.password_editText)
        secondPasswordEditText = view.findViewById(R.id.second_password_editText)
        signUpButton = view.findViewById(R.id.signUp_button)
        signInButton = view.findViewById(R.id.signIn_button)

        return view
    }

    override fun onStart() {
        super.onStart()
        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val secondName = secondNameEditText.text.toString()
            val mail = mailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val secondPassword = secondPasswordEditText.text.toString()
            //Проверка полей
            val checkFields =
                viewModel.checkFields(requireContext(), name, secondName, mail, password, secondPassword)
            if (checkFields) {
                //Сохранение состояния в SharedPreferences
                //Переход в SignIn
                signUp(mail, password, name, secondName)
                successfulAuthentication()
            }
        }
        signInButton.setOnClickListener {
            callbacks?.onSignUpFragment(1)
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun successfulAuthentication() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.signIn.collect {
                if (it) {
                    val sp = cf.sharedPreferences(requireContext())
                    val editor = sp?.edit()
                    editor?.putBoolean(cf.spFirstLaunch, true)
                    editor?.apply()
                    callbacks?.onSignUpFragment(0)
                }
            }
        }
    }

    private fun signUp(mail: String, password: String, name: String, secondName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.signUp(requireContext(), mail, password, name, secondName)
        }
    }

    companion object {
        fun newInstance() = SignUpFragment()
    }
}