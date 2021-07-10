package com.example.myapplication2

import MQTT.MQTTmanager
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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
            val viberator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            viberator.vibrate(40)
            mqtt.publish(topic = "control", message = "duc123,duc123,123,10,1")
//            if (errorRel.visibility == View.VISIBLE){
//                controlBtn.setText("重新連線")
//                controlBtn.background = resources.getDrawable(R.drawable.bg_round_button,null)
//            } else if (controlMainRel.visibility == View.VISIBLE){
//                controlBtn.setText("呼叫電梯")
//                if (floor != null){
//                    controlBtn.background = resources.getDrawable(R.drawable.bg_round_button,null)
//                } else{
//                    controlBtn.background = resources.getDrawable(R.drawable.bg_round_button_rest,null)
//                }
//            }
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
            errorRel.removeAllViews()
            ErrorPage(
                relative = errorRel,
                activity = activity!!,
                resources = resources,
                context = context!!
            )
            CubePage(
                relative = relative,
                resources = resources,
                context = context!!,
                activity = activity!!
            )
            if (floorArray.count() > 0){
                relative.visibility = View.VISIBLE
                errorRel.visibility = View.GONE
                textView4.visibility = View.VISIBLE
            }
            else {
                relative.visibility = View.GONE
                errorRel.visibility = View.VISIBLE
                textView4.visibility = View.GONE
            }
        }
    }
}