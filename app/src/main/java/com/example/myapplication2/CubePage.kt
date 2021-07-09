package com.example.myapplication2

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import org.eclipse.paho.android.service.BuildConfig

var floorArray: ArrayList<String> =
    arrayListOf()

var displaymetrics = DisplayMetrics()

var vibrator: Vibrator? = null

var floor: String? = null

class CubePage(private val relative:RelativeLayout,val resources: Resources,val context: Context,val activity: Activity) {

    private var relID: Int = 0

    private val relativeArray: ArrayList<RelativeLayout> = ArrayList()

    private var viewID: Int = 0

    private var shadowV: RelativeLayout? = null
    private var contentV: View? = null
    private var innerV: View? = null
    private var imgV: ImageView? = null


    data class widthData(
        val relWidth: Double,
        val relX: Double,
        val shadowWidth: Double,
        val btnW: Double,
        val btnMarginL: Double,
        val innerWidth: Double,
        val innerMarginL: Double
    )

    init {
        Log.d("2","ui")

        val (relWid,relX,shadowW,btnW,btnMarginL,innerW,innerMarginL) = groupEachWidth()
        if (floorArray.size > 6) {
            for (i in 0 until floorArray.size) {
                if (i%3 == 0) {
                    setupLineSpacingLayout(relWid,shadowW,relX)
                }
                setupDynamicButtons(i, shadowW, btnW, btnMarginL, innerW, innerMarginL)
            }
        } else {
            for (i in 0 until floorArray.size) {
                if (i%2 == 0) {
                    setupLineSpacingLayout(relWid,shadowW,relX)
                }
                setupDynamicButtons(i, shadowW, btnW, btnMarginL, innerW, innerMarginL)
            }
        }
    }

    fun setupLineSpacingLayout(relWid: Double, shadowW: Double, relX: Double) {
        val rel = RelativeLayout(context)
        rel.id = relID + 1
        //rel.setBackgroundColor(Color.YELLOW)
        val relative_params = RelativeLayout.LayoutParams(relWid.toInt(),shadowW.toInt())
        relative_params.addRule(RelativeLayout.BELOW, rel.id - 1)
        relative_params.setMargins(relX.toInt(), 0,0,0)
        relative.addView(rel, relative_params)
        relativeArray.add(rel)
        relID++
    }

    fun setupDynamicButtons(
        i: Int,
        shadowW: Double,
        btnW: Double,
        btnMarginL: Double,
        innerW: Double,
        innerMarginL: Double
    ) {
        val btnRel = RelativeLayout(context)
        btnRel.id = viewID + 1
        btnRel.setBackgroundResource(R.drawable.icon_btn_shadow_rest)
        val view_params =
            RelativeLayout.LayoutParams(shadowW.toInt(), ViewGroup.LayoutParams.MATCH_PARENT)
        view_params.addRule(RelativeLayout.RIGHT_OF, btnRel.id - 1)
        view_params.setMargins(0, 0, 0, 0)
        relativeArray[relID - 1].addView(btnRel, view_params)

        val btnView = View(context)
        btnView.id = viewID + 1
        btnView.background = resources.getDrawable(R.drawable.btn_corner_rafius_0ff, null)
        val btnView_params = RelativeLayout.LayoutParams(btnW.toInt(), btnW.toInt())
        btnView_params.setMargins(btnMarginL.toInt(), btnMarginL.toInt(), 0, 0)
        btnRel.addView(btnView, btnView_params)

        val btnInner = View(context)
        btnInner.id = viewID + 1
        btnInner.background = resources.getDrawable(R.drawable.btn_inner_off, null)
        val btnInner_params = RelativeLayout.LayoutParams(innerW.toInt(), innerW.toInt())
        btnInner_params.setMargins(innerMarginL.toInt(), innerMarginL.toInt(), 0, 0)
        btnRel.addView(btnInner, btnInner_params)

        var imgName: String
        if (floorArray.count() > 6) {
            imgName = "icon_btn_floor_"+ floorArray[i].toLowerCase()
        } else {
            imgName = "icon_btn_floor_"+ floorArray[i].toLowerCase()+"_m"
        }
        var resID = resources.getIdentifier(imgName,"drawable",
            BuildConfig.APPLICATION_ID
        )
        val floorImg = ImageView(context)
        floorImg.tag = floorArray[i]
        floorImg.setScaleType(ImageView.ScaleType.FIT_XY)
        floorImg.setImageResource(resID)
        floorImg.setColorFilter(Color.parseColor("#D0D6E1"));
        btnRel.addView(floorImg,btnView_params)

        handleCubeEvent(shadowV = btnRel, contentV = btnView, innerV = btnInner, imgV = floorImg)

        viewID++

    }

    fun handleCubeEvent(shadowV: RelativeLayout, contentV: View, innerV: View, imgV: ImageView) {
        shadowV.setOnClickListener {
            floor = imgV.tag.toString()
            vibrator()
            shadowV.startAnimation(
                AnimationUtils.loadAnimation(context,
                    R.anim.bounce_animation
                ))
            this.shadowV?.let {
                    it1 -> this.contentV?.let {
                    it2 -> this.innerV?.let {
                    it3 -> this.imgV?.let {
                    it4 -> cubeStateOff(shadowV = it1, contentV = it2, innerV = it3, imgV = it4) } } } }
            cubeStateOn(shadowV = shadowV, contentV = contentV, innerV = innerV, imgV = imgV)
            this.shadowV = shadowV; this.contentV = contentV; this.innerV = innerV; this.imgV = imgV
//            cube.checkControlBtnStatus()
        }
    }


    fun cubeStateOn(shadowV: RelativeLayout, contentV: View, innerV: View, imgV: ImageView) {
        shadowV.setBackgroundResource(R.drawable.icon_btn_shadow_on)
        contentV.background = resources.getDrawable(R.drawable.btn_corner_radius_on,null)
        innerV.background = resources.getDrawable(R.drawable.btn_inner_on,null)
        imgV.setColorFilter(Color.parseColor("#ffffff"));
    }

    fun cubeStateOff(shadowV: RelativeLayout, contentV: View, innerV: View, imgV: ImageView) {
        shadowV.setBackgroundResource(R.drawable.icon_btn_shadow_rest)
        contentV.background = resources.getDrawable(R.drawable.btn_corner_radius_off,null)
        innerV.background = resources.getDrawable(R.drawable.btn_inner_off,null)
        imgV.setColorFilter(Color.parseColor("#D0D6E1"));
    }

    fun vibrator() {
        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(40,10))
        } else {
            vibrator?.vibrate(40)
        }
    }

    fun groupEachWidth(): widthData {
        activity.windowManager.defaultDisplay?.getMetrics(displaymetrics)
        if (floorArray.size > 6) {
            val three = widthData(
                relWidth = displaymetrics.widthPixels * 0.966,
                relX = (displaymetrics.widthPixels - displaymetrics.widthPixels * 0.966) / 2,
                shadowWidth = displaymetrics.widthPixels * 0.322,
                btnW = displaymetrics.widthPixels * 0.267,
                btnMarginL = (displaymetrics.widthPixels * 0.322 - displaymetrics.widthPixels * 0.267) / 2,
                innerWidth = displaymetrics.widthPixels * 0.25,
                innerMarginL = (displaymetrics.widthPixels * 0.322 - displaymetrics.widthPixels * 0.25) / 2
            )
            return three
        } else {
            val two = widthData(
                relWidth = displaymetrics.widthPixels * 0.74,
                relX = (displaymetrics.widthPixels - displaymetrics.widthPixels * 0.74) / 2,
                shadowWidth = displaymetrics.widthPixels * 0.37,
                btnW = displaymetrics.widthPixels * 0.311,
                btnMarginL = (displaymetrics.widthPixels * 0.37 - displaymetrics.widthPixels * 0.311) / 2,
                innerWidth = displaymetrics.widthPixels * 0.292,
                innerMarginL = (displaymetrics.widthPixels * 0.37 - displaymetrics.widthPixels * 0.292) / 2
            )
            return two
        }
    }
}
