package com.github.llmaximll.worldcinema.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.llmaximll.worldcinema.common.CommonFunctions
import com.github.llmaximll.worldcinema.repositories.CinemaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "SignUpVM"

class SignUpVM : ViewModel() {

    private val repository = CinemaRepository.get()
    private val cf = CommonFunctions.get()
    private val _signIn = MutableStateFlow(false)
    val signIn = _signIn.asStateFlow()

    fun signUp(context: Context, email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.signUp(email, password, firstName, lastName)
            withContext(Dispatchers.Main) {
                if (response) {
                    signIn(context, email, password)
                }
                else
                    cf.toast(context, "Ошибка при регистрации")
            }
        }
    }

    fun signIn(context: Context, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.signIn(email, password)
            withContext(Dispatchers.Main) {
                if (response != null) {
                    _signIn.value = true
                    cf.toast(context, "Регистрация успешна | " +
                                "token=${response.token}")
                    cf.log(TAG, "Регистрация успешна | token=${response.token}")
                }
                else
                    cf.toast(context, "Регистрация успешна. " +
                            "Ошибка при аутентификации")
            }
        }
    }

    fun checkFields(
        context: Context, name: String, secondName: String, mail: String,
        password: String, secondPassword: String
    ): Boolean {
        var checkFields = true

        when {
            name == "" -> {
                checkFields = false
                cf.toast(context, "Поле \"Имя\" пустое")
            }
            secondName == "" -> {
                checkFields = false
                cf.toast(context, "Поле \"Фамилия\" пустое")
            }
            mail == "" -> {
                checkFields = false
                cf.toast(context, "Поле \"E-mail\" пустое")
            }
            password == "" -> {
                checkFields = false
                cf.toast(context, "Поле \"Пароль\" пустое")
            }
            secondPassword == "" -> {
                checkFields = false
                cf.toast(context, "Поле \"Повторите пароль\" пустое")
            }
            password != secondPassword -> {
                checkFields = false
                cf.toast(context, "Пароли не совпадают")
            }
            !checkMail(context, mail) -> checkFields = false
        }

        return checkFields
    }

    private fun checkMail(context: Context, mail: String): Boolean {
        var checkMail = true
        var haveDot = false
        mail.substringBeforeLast("@").forEach {
            if (it.isUpperCase()) {
                checkMail = false
                cf.toast(context, "Текст в поле \"E-mail\" до знака \"@\" не может " +
                        "содержать большие буквы")
            }
        }
        val illegalCharacters = charArrayOf('$', '!', '@', '#', '%', '^', '&', '*', '(', ')', '_', '-',
        '+', '=', '|', '\\', '/', '`', '~', ',', '<', '>', '?', '№', ':', ';', '?', '\'', '\"')
        if ((mail.substringBeforeLast("@")).any(illegalCharacters::contains) ||
            (mail.substringAfterLast("@")).any(illegalCharacters::contains)) {
            checkMail = false
            cf.toast(context, "Текст в поле \"E-mail\" до знака \"@\" не может " +
                    "содержать специальные знаки")
        }
        val numbers = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        if (mail.substringAfterLast("@").any(numbers::contains)) {
            checkMail = false
            cf.toast(context, "Текст в поле \"E-mail\" после знака \"@\" не может " +
                    "содержать цифры")
        }
        mail.substringAfterLast("@").forEach {
            if (it == '.') haveDot = true
            if (it.isUpperCase()) {
                checkMail = false
                cf.toast(context, "В поле \"E-mail\" в домене не может быть " +
                        "больших букв")
            }
        }
        if (!haveDot) {
            checkMail = false
            cf.toast(context, "В поле \"E-mail\" не найдено разделение домена " +
                    "символом \".\"")
        } else {
            val list = mutableListOf<Char>()
            mail.substringAfterLast(".").forEach {
                list.add(it)
            }
            when {
                list.size == 0 -> {
                    checkMail = false
                    cf.toast(context, "Поле \"E-mail\". Домен верхнего уровня " +
                            "не может быть пустой")
                }
                list.size >= 4 -> {
                    checkMail = false
                    cf.toast(context, "Поле \"E-mail\". Длина домена верхнего " +
                            "уровня не может быть больше 3 символов")
                }
            }
        }
        var haveSymbol = false
        mail.forEach {
            if (it == '@') haveSymbol = true
        }
        if (!haveSymbol) {
            checkMail = false
            cf.toast(context, "Неверно введен E-mail")
        }

        return checkMail
    }
}