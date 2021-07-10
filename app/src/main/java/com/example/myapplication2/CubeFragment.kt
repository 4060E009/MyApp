package com.example.myapplication2

import MQTT.MQTTmanager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.cubecontro_fragment.*


class CubeFragment: Fragment(){

    var dialog : DialogLoading? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog = this.context?.let { it -> DialogLoading(it) }

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

        val mqtt = MQTTmanager(context = context!!, activity = activity!!)
        mqtt.connect()

        //發送控制的監聽動作
        controlBtn.setOnClickListener {
            mqtt.publish(topic = "control", message = "duc123,duc123,123,10," + floor)
            btnStatus(false)
            dialog?.startCallingDialog()
            dialog?.successStatus()
        }

        //重新整理的監聽動作
        refreshBtn.setOnClickListener {
            val viberator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            viberator.vibrate(40)
            mqtt.publish(topic = "information", message = "duc123,duc123,123,10")
            dialog?.startLoadingDialog()
            dialog?.stopLoadingDialog()

        }

        infoBtn.setOnClickListener {
            val viberator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            viberator.vibrate(40)
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

    // change control button status
    fun btnStatus(status: Boolean) {
        controlBtn.isEnabled = status
        controlBtn.isClickable = status
        if (status)
            controlBtn.setBackgroundResource(R.drawable.btn_corner_radius_on)
        else
            controlBtn.setBackgroundResource(R.drawable.btn_corner_radius_off)
    }
}