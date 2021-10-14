package com.example.geoservice

import android.app.PendingIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text
import android.content.Intent
import android.telephony.SmsManager
import android.view.View
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {
    lateinit var coordList: LinearLayout
    lateinit var button: Button
    lateinit var buttonExit: Button
    lateinit var buttonShow: Button
    lateinit var buttonSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myService = Intent(this, MyService::class.java)  //создаем интент для сервиса

        coordList = findViewById(R.id.coordList)

        button = findViewById(R.id.button)
        button.setOnClickListener {
            startService(myService) //Запускаем сервис
        }

        buttonExit = findViewById(R.id.buttonExit)
        buttonExit.setOnClickListener {
            stopService(myService) //останавливаем сервис
        }

        buttonShow = findViewById(R.id.buttonShow)
        buttonShow.setOnClickListener { //при запросе выводим на экран координаты пользователя
            val tv = TextView(this)
            tv.text =
                "Широта: ${LocationHelper.Imhere?.latitude}; Долгота: ${LocationHelper.Imhere?.longitude}"
            coordList.addView(tv)
        }

        buttonSend = findViewById(R.id.buttonSend)
        buttonSend.setOnClickListener { //считываем номер телефона и отправляем координаты по смс
            var numberTelephone = findViewById<TextInputEditText>(R.id.textInputEdit)
            var number = numberTelephone.text.toString()
            sendSMS(
                number,
                "Im here ${LocationHelper.Imhere?.latitude}.${LocationHelper.Imhere?.longitude}"
            )
        }
    }

    fun exitButton(view: View){ //кнопка выхода
        this.finishAffinity()
    }

    private fun sendSMS(phoneNumber: String, message: String) { //стандартная функция для отправки сообщения по смс
        val sentPI: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"), 0)
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, sentPI, null)
    }
}