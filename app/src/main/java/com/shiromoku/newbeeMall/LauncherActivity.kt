package com.shiromoku.newbeeMall

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Button

class LauncherActivity : Activity() {
    lateinit var skipButton: Button
    lateinit var launcherHandler: Handler

    inner class SkipTimerThread(delayTime:Int):Thread(){
        var delayTime = delayTime
        override fun run() {
            while (--delayTime >= 0){
                val msg = Message()
                msg.arg1 = delayTime
                msg.what = 1
                launcherHandler.sendMessage(msg)

                try {
                    sleep(1000)
                } catch (e: Exception) {
                    break
                }
            }
        }
    }
    lateinit var skipTimerThread: SkipTimerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        skipButton = findViewById(R.id.launcher_button_skip)
        initHandler()
        skipTimerThread = SkipTimerThread(4)
        skipTimerThread.start()
        setListener()
    }

    private fun initHandler() {
        launcherHandler = object : Handler(mainLooper){
            override fun handleMessage(msg: Message) {
                skipButton.text = getString(R.string.skip,msg.arg1)
                if(0 == msg.arg1)startMainActivity()
            }
        }
    }

    private fun setListener() {
        skipButton.setOnClickListener {
            skipTimerThread.interrupt()
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        finish()
        startActivity(Intent(this,MainActivity::class.java))
    }
}