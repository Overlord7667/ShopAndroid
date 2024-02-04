package com.betelgeuse.corp.shopandroid

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // Объявляем переменные как члены класса, чтобы они были доступны в других методах
    private lateinit var editText: EditText
    private lateinit var editText2: EditText
    private lateinit var editText3: EditText
    private lateinit var loginButton: Button

    private lateinit var secondName: String
    private lateinit var name: String
    private lateinit var phoneNumber: String

    // Объявляем переменную как член класса
    private var isButtonEnabled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text2)
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        editText = findViewById(R.id.firstNameID)
        editText2 = findViewById(R.id.secondNameID)
        editText3 = findViewById(R.id.phoneNumberID)
        val clearButton = findViewById<ImageButton>(R.id.clearName)
        val clearButton2 = findViewById<ImageButton>(R.id.clearSecondName)
        val clearButton3 = findViewById<ImageButton>(R.id.clearPhoneNumber)
        loginButton = findViewById(R.id.loginID)

        name = editText.text.toString()
        secondName = editText2.text.toString()
        phoneNumber = editText.text.toString()

        updateLoginButtonState()

        val mask = "+7"
        editText3.setText(mask)
        editText3.setSelection(mask.length)

        editText3.setOnClickListener {
            // Устанавливаем курсор после маски при клике на поле ввода
            editText3.setSelection(mask.length)
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = editText.text.toString()
                val isOnlyCyrillic = input.matches(Regex("[А-ЯЁа-яё]*"))

                if (!isOnlyCyrillic) {
                    editText.setBackgroundResource(R.drawable.invalid_background)
                    clearButton.visibility = View.VISIBLE
                } else {
                    editText.setBackgroundResource(android.R.color.transparent)
                    clearButton.visibility = if (input.isNotEmpty()) View.VISIBLE else View.GONE
                }

                updateLoginButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        editText2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = editText2.text.toString()
                val isOnlyCyrillic = input.matches(Regex("[А-ЯЁа-яё]*"))

                if (!isOnlyCyrillic) {
                    editText2.setBackgroundResource(R.drawable.invalid_background)
                    clearButton2.visibility = View.VISIBLE
                } else {
                    editText2.setBackgroundResource(android.R.color.transparent)
                    clearButton2.visibility = if (input.isNotEmpty()) View.VISIBLE else View.GONE
                }

                updateLoginButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        editText3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility =
                    if (s.isNullOrEmpty() || s.length <= mask.length) View.GONE else View.VISIBLE

                updateLoginButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        clearButton.setOnClickListener {
            editText.text = null
            editText.requestFocus() // Очищаем поле ввода при нажатии на иконку крестика
        }
        clearButton2.setOnClickListener {
            editText2.text = null
            editText2.requestFocus() // Очищаем поле ввода при нажатии на иконку крестика
        }
        clearButton3.setOnClickListener {
            editText3.text = null
            //editText3.requestFocus()
            //editText3.setSelection(mask.length)
            // Проверяем, что длина маски не превышает длину строки
            if (mask.length < editText.length()) {
                editText.setSelection(mask.length)
            } else {
                editText.setSelection(editText.length()) // Если длина маски превышает длину строки, устанавливаем курсор в конец строки
            }

            val filters = arrayOf<InputFilter>(PhoneNumberInputFilter(mask))
            editText3.filters = filters
        }
    }
    // Обновление состояния кнопки "Войти"
    fun updateLoginButtonState() {
        name = editText.text.toString()
        secondName = editText2.text.toString()
        phoneNumber = editText3.text.toString()

        fun isNameValidFunction(name: String): Boolean {
            // Здесь выполняется логика проверки валидности имени
            // Возвращается true, если имя валидно, и false в противном случае
            return name.isNotBlank() // Пример простой проверки на пустоту
        }

        fun isLastNameValidFunction(secondName: String): Boolean {
            // Здесь выполняется логика проверки валидности фамилии
            // Возвращается true, если фамилия валидна, и false в противном случае
            return secondName.isNotBlank() // Пример простой проверки на пустоту
        }

        fun isPhoneNumberValidFunction(phoneNumber: String): Boolean {
            // Здесь выполняется логика проверки валидности номера телефона
            // Возвращается true, если номер телефона валиден, и false в противном случае
            return phoneNumber.isNotBlank() // Пример простой проверки на пустоту
        }

        val isNameValid = isNameValidFunction(name) // Проверка валидности имени
        val isLastNameValid = isLastNameValidFunction(secondName) // Проверка валидности фамилии
        val isPhoneNumberValid = isPhoneNumberValidFunction(phoneNumber) // Проверка валидности номера телефона

        // Устанавливаем состояние кнопки "Войти" в зависимости от валидности всех полей
        isButtonEnabled = isNameValid && isLastNameValid && isPhoneNumberValid
        loginButton.isEnabled = isButtonEnabled

        // Обработчик события для кнопки "Войти"
        loginButton.setOnClickListener {
            if (isButtonEnabled) {
                val isNameValid = isNameValidFunction(editText.text.toString()) // Проверка валидности имени
                val isLastNameValid = isLastNameValidFunction(editText2.text.toString()) // Проверка валидности фамилии
                val isPhoneNumberValid = isPhoneNumberValidFunction(editText3.text.toString()) // Проверка валидности номера телефона

                if (isNameValid && isLastNameValid && isPhoneNumberValid) {
                    // Осуществляем переход на экран "Главная"
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    // Если какое-то из полей не валидно, можно показать сообщение об ошибке
                    Toast.makeText(this, "Пожалуйста, заполните все поля корректно", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}