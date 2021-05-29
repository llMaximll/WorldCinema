package com.github.llmaximll.worldcinema

import android.content.Context
import androidx.lifecycle.ViewModel

class SignUpVM : ViewModel() {

    private val commonFunctions = CommonFunctions()

    fun checkFields(
        context: Context, name: String, secondName: String, mail: String,
        password: String, secondPassword: String
    ): Boolean {
        var checkFields = true

        when {
            name == "" -> {
                checkFields = false
                commonFunctions.toast(context, "Поле \"Имя\" пустое")
            }
            secondName == "" -> {
                checkFields = false
                commonFunctions.toast(context, "Поле \"Фамилия\" пустое")
            }
            mail == "" -> {
                checkFields = false
                commonFunctions.toast(context, "Поле \"E-mail\" пустое")
            }
            password == "" -> {
                checkFields = false
                commonFunctions.toast(context, "Поле \"Пароль\" пустое")
            }
            secondPassword == "" -> {
                checkFields = false
                commonFunctions.toast(context, "Поле \"Повторите пароль\" пустое")
            }
            password != secondPassword -> {
                checkFields = false
                commonFunctions.toast(context, "Пароли не совпадают")
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
                commonFunctions.toast(context, "Текст в поле \"E-mail\" до знака \"@\" не может " +
                        "содержать большие буквы")
            }
        }
        val illegalCharacters = charArrayOf('$', '!', '@', '#', '%', '^', '&', '*', '(', ')', '_', '-',
        '+', '=', '|', '\\', '/', '`', '~', ',', '<', '>', '?', '№', ':', ';', '?', '\'', '\"')
        if ((mail.substringBeforeLast("@")).any(illegalCharacters::contains) ||
            (mail.substringAfterLast("@")).any(illegalCharacters::contains)) {
            checkMail = false
            commonFunctions.toast(context, "Текст в поле \"E-mail\" до знака \"@\" не может " +
                    "содержать специальные знаки")
        }
        val numbers = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        if (mail.substringAfterLast("@").any(numbers::contains)) {
            checkMail = false
            commonFunctions.toast(context, "Текст в поле \"E-mail\" после знака \"@\" не может " +
                    "содержать цифры")
        }
        mail.substringAfterLast("@").forEach {
            if (it == '.') haveDot = true
            if (it.isUpperCase()) {
                checkMail = false
                commonFunctions.toast(context, "В поле \"E-mail\" в домене не может быть " +
                        "больших букв")
            }
        }
        if (!haveDot) {
            checkMail = false
            commonFunctions.toast(context, "В поле \"E-mail\" не найдено разделение домена " +
                    "символом \".\"")
        } else {
            val list = mutableListOf<Char>()
            mail.substringAfterLast(".").forEach {
                list.add(it)
            }
            when {
                list.size == 0 -> {
                    checkMail = false
                    commonFunctions.toast(context, "Поле \"E-mail\". Домен верхнего уровня " +
                            "не может быть пустой")
                }
                list.size >= 4 -> {
                    checkMail = false
                    commonFunctions.toast(context, "Поле \"E-mail\". Длина домена верхнего " +
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
            commonFunctions.toast(context, "Неверно введен E-mail")
        }

        return checkMail
    }
}