package MQTT

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.myapplication2.CubeFragment
import com.example.myapplication2.MainActivity
import com.example.myapplication2.cube
import com.example.myapplication2.floorArray
import kotlinx.android.synthetic.main.activity_main.*
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*


data class MQTTConnectionParams(
    val clientId: String,
    val host: String,
    val topic: String,
    val device: String

)

class MQTTmanager(val context: Context, val activity: Activity) {

    private lateinit var mqttClient: MqttAndroidClient
    // TAG
    companion object {
        const val TAG = "AndroidMqttClient"
    }

    init {
        val serverURI = "tcp://192.168.5.19:1883"
        mqttClient = MqttAndroidClient(context, serverURI, "kotlin_client")
    }

    fun publish(topic: String, message: String) {
        try
        {
            var msg = message
            mqttClient.publish("phone/"+topic,msg.toByteArray(),1,false,null,object :IMqttActionListener{
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.w("Mqtt", "Publish Success!")
                }
                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.w("Mqtt", "Publish Failed!")

                }
            })
        }
        catch (ex:MqttException) {
            System.err.println("Exception publishing")
            ex.printStackTrace()
        }
    }

    fun connect() {
        mqttClient.setCallback(object : MqttCallback {
            // 所有publish回傳訊息
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d("messageArrived", message.toString())
                val data = message.toString().split(",") as ArrayList<String>
                floorArray = data
                cube.updateUI()
            }

            override fun connectionLost(cause: Throwable?) {
                Log.d(TAG, "Connection lost ${cause.toString()}")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {

            }
        })
        val options = MqttConnectOptions()
        try {
            // mqtt連線
            mqttClient.connect(options, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Connection success")
                    subscribe("phone/duc123/123")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Connection failure")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    // mqtt訂閱function
    fun subscribe(topic: String) {
        try
        {
            mqttClient.subscribe(topic, 1, null, object:IMqttActionListener {
                override fun onSuccess(asyncActionToken:IMqttToken) {
                    Log.d("Mqtt subscribe", "Subscription onSuccess")
                    publish(topic = "information", message = "duc123,duc123,123,10")
                }
                override fun onFailure(asyncActionToken:IMqttToken, exception:Throwable) {
                    Log.d("Mqtt subscribe", "Subscription onFailure")
                }
            })
        }
        catch (ex:MqttException)
        {
            System.err.println("Exception subscribing")
            ex.printStackTrace()
        }
    }

    fun unsubscribe(topic: String) {
        try
        {
            mqttClient.unsubscribe(topic,null,object :IMqttActionListener{
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d("Mqtt unsubscribe", "unSubscribe onSuccess")
                }
                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d("Mqtt unsubscribe", "unSubscribe onFailure")
                }
            })
        }
        catch (ex:MqttException)
        {
            System.err.println("Exception unsubscribe")
            ex.printStackTrace()
        }
    }
}