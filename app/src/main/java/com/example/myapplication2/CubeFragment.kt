package com.example.myapplication2

import MQTT.MQTTmanager
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.cubecontro_fragment.*
import org.eclipse.paho.client.mqttv3.MqttMessage

class CubeFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.cubecontro_fragment, container, false)
    }

    @SuppressLint("ResourceType")
    fun change(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragmentLayout, fragment)
        transaction?.commitAllowingStateLoss()
        Runtime.getRuntime().gc()
        Runtime.getRuntime().freeMemory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            CubePage(
                relative = relative,
                resources = resources,
                context = it,
                activity = activity!!
            )
        }

        val mqtt = MQTTmanager(context = context!!, activity = activity!!)
        mqtt.connect()

        //發送控制的監聽動作
        controlBtn.setOnClickListener {
            mqtt.publish(topic = "control", message = "duc123,duc123,123,10,1")
        }

        //重新整理的監聽動作
        refreshBtn.setOnClickListener {
            mqtt.publish(topic = "information", message = "duc123,duc123,123,10")
        }

        infoBtn.setOnClickListener {
            val info_fragment = InfoFragment()
            change(fragment = info_fragment)
        }
    }

    fun updateUI() {
        // 更新UI
        activity?.runOnUiThread {
            relative.removeAllViews() //更新前清除
            CubePage(
                relative = relative,
                resources = resources,
                context = context!!,
                activity = activity!!
            )
        }
    }
}