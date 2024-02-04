package com.betelgeuse.corp.shopandroid

import android.text.InputFilter
import android.text.Spanned

class PhoneNumberInputFilter(private val mask: String) : InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val sb = StringBuilder()

        // Если поле пустое или все символы удалены, добавляем маску "+7"
        if (dest.isEmpty() || (dstart == 0 && dend == dest.length)) {
            sb.append(mask)
        }

        // Удаляем все символы, кроме цифр
        for (i in start until end) {
            val c = source[i]
            if (Character.isDigit(c)) {
                sb.append(c)
            }
        }
        return sb.toString()
    }
}