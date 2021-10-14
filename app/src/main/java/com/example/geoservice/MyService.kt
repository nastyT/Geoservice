package com.example.geoservice

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast


class MyService : Service() {

    val mainHandler =
        Handler(Looper.getMainLooper()) //создаем Handler - это механизм, который позволяет работать с очередью сообщений.
    // Он привязан к конкретному потоку (thread) и работает с его очередью.
    var myRunnable = object : Runnable { //поток в котором мы постоянно считываем локацию пользователя
        override fun run() {
            LocationHelper.startLocationListener(this@MyService)
            mainHandler.postDelayed(this, 1000) //с отсрочкой 1000 милисекунд
        }
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() { //сервис запущен
        super.onCreate()
        Toast.makeText(
            this, "Служба создана", //всплывающее сообщение о запуске сервиса
            Toast.LENGTH_SHORT
        ).show()

        mainHandler.post(myRunnable) //запускаем Handler
    }


    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(
            this, "Служба остановлена", //всплывающее сообщение об остановке сервиса
            Toast.LENGTH_SHORT
        ).show()
        mainHandler.removeCallbacksAndMessages(null); //останавливаем все службы
        mainHandler.removeCallbacks(myRunnable)  //а это было для проверки наверняка
    }


}