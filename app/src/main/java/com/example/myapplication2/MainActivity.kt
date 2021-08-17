package com.example.myapplication2

import MQTT.MQTTmanager
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*

val cube = CubeFragment()

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = this.supportFragmentManager?.beginTransaction()
        transaction.replace(R.id.fragmentLayout, cube)
        transaction.commitAllowingStateLoss()
        Runtime.getRuntime().gc()
        Runtime.getRuntime().freeMemory()

        var data = ""
        val api = httpAPI()
        api.postAPI()

        api.getUserMail('abc', '123', httpAPI.OnRequestListener {
            override fun onsuccess(result: Any) {
                data = result
            }
        })
    }
}









