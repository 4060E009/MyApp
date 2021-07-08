package com.example.myapplication2

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import kotlin.random.Random

class Info(private val  res : Resources, rel : RelativeLayout, context: Context) {

    var relID: Int = 0

    val heightPixels = 2220
    val widthPixels = 1080

    val cardH = heightPixels * 0.453

    val avatorRelH = cardH * 0.336

    val avatorH = avatorRelH * 0.651

    val infoRelH = cardH - avatorRelH - heightPixels * 0.02

    var cacheFirstTitleW: Int = 0

    val subTitleMarginL = widthPixels * 0.056

    val dividerW = widthPixels * 0.822

    val rnd = Random(12)

    val names = arrayOf("帳號", "密碼", "IP")

    init {

        val cardRel = RelativeLayout(context)
        cardRel.id = View.generateViewId()
        cardRel.background = res.getDrawable(R.drawable.text,null)

        val cardRel_params =
            RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, cardH.toInt())
        cardRel_params.setMargins(0, 0, 0, 0)
        rel.addView(cardRel, cardRel_params)
        val avatarRel = RelativeLayout(context)
        avatarRel.id = View.generateViewId()

        val avatarRel_params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,avatorRelH.toInt())
        avatarRel_params.setMargins(0,0,0,0)
        cardRel.addView(avatarRel,avatarRel_params)

        val avatar = ImageView(context)
        avatar.id = View.generateViewId()
        avatar.scaleType = ImageView.ScaleType.FIT_CENTER
        avatar.setImageResource(R.drawable.icon_default_avatar)
        val avatar_params = RelativeLayout.LayoutParams(avatorH.toInt(), avatorH.toInt())
        avatar_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        avatar_params.addRule(RelativeLayout.CENTER_HORIZONTAL)
        avatarRel.addView(avatar, avatar_params)

        val infoRel = RelativeLayout(context)
        infoRel.id = View.generateViewId()
        val infoRel_params =
            RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, infoRelH.toInt())
        infoRel_params.addRule(RelativeLayout.BELOW, avatarRel.id)
        infoRel_params.setMargins(0, (heightPixels * 0.01).toInt(), 0, 0)
        cardRel.addView(infoRel, infoRel_params)

        val infoCRelH = infoRelH / 3

        for (i in 0 until names.size) {
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

            val infoCRel = RelativeLayout(context)
            infoCRel.id = relID + 1
            val infoControlRel_params =
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, infoCRelH.toInt())
            infoControlRel_params.addRule(RelativeLayout.BELOW, infoCRel.id - 1)
            infoRel.addView(infoCRel, infoControlRel_params)

            val textRel = RelativeLayout(context)
            val textRel_params = RelativeLayout.LayoutParams(dividerW.toInt(), (infoCRelH).toInt())
            textRel_params.addRule(RelativeLayout.CENTER_HORIZONTAL)
            infoCRel.addView(textRel, textRel_params)

            val dividerRel = RelativeLayout(context)
            val dividerRel_params =
                RelativeLayout.LayoutParams(dividerW.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
            dividerRel_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            dividerRel_params.addRule(RelativeLayout.CENTER_HORIZONTAL)
            infoCRel.addView(dividerRel, dividerRel_params)

            val title = TextView(context)
            title.text = names[i]
            title.gravity = Gravity.CENTER_VERTICAL
            title.setTextColor(res.getColor(R.color.black, null))
            title.setTextSize(19f)
            title.measure(0, 0)
            title.includeFontPadding = false
//            val typeface = Typeface.createFromAsset(.assets, "notnmm.otf")
//            title.setTypeface(typeface)
            val title_params = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            title_params.setMargins(0, 0, 0, 0)
            textRel.addView(title, title_params)

            if (i == 0) {
                cacheFirstTitleW = title.measuredWidth
            }

            Log.d("TAG", "onCreate: i = " + i)

//            val subTitle = TextView(context)
            val subTitle = EditText(context)
            when (i) {
//                0 -> subTitle.text = "accout"
//                1 -> subTitle.text = "password"
//                2 -> subTitle.text = "ip"
                0 -> subTitle.hint = "accout"
                1 -> subTitle.hint = "password"
                2 -> subTitle.hint = "ip"
            }
            subTitle.gravity = Gravity.CENTER_VERTICAL
            subTitle.setTextColor(Color.parseColor("#663f414b"))
            subTitle.setTextSize(19f)
            subTitle.setBackgroundResource(android.R.color.transparent)
            subTitle.measure(0, 0)
            subTitle.includeFontPadding = false
//            subTitle.setTypeface(typeface)
            val subTitle_params = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            subTitle_params.setMargins(cacheFirstTitleW + subTitleMarginL.toInt(), 0, 0, 0)
            textRel.addView(subTitle, subTitle_params)

            if (i != names.size - 1) {
                val divider = View(context)
                divider.setBackgroundColor(Color.parseColor("#338a98b4"))
                val divider_params = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (heightPixels * 0.002).toInt()
                )
                dividerRel.addView(divider, divider_params)
            }
            relID++
        }
    }
}