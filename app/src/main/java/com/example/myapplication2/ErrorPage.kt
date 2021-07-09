package com.example.myapplication2

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

class ErrorPage(private val relative: RelativeLayout, activity: FragmentActivity?, resources: Resources, context: Context?) {

    init {
        activity?.windowManager?.defaultDisplay?.getMetrics(displaymetrics)
        val imgW = displaymetrics.widthPixels*0.578

        val subTitleW = displaymetrics.widthPixels*0.7

        val titleMarginT = displaymetrics.heightPixels*0.025
        val subTitleMarginT = displaymetrics.heightPixels*0.01

        val contentRel = RelativeLayout(activity)
        val contentRel_params = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        contentRel_params.addRule(RelativeLayout.CENTER_HORIZONTAL)
        contentRel_params.addRule(RelativeLayout.CENTER_VERTICAL)
        relative.addView(contentRel,contentRel_params)

        val errorImg = ImageView(activity)
        errorImg.id = View.generateViewId()
        errorImg.setScaleType(ImageView.ScaleType.FIT_XY)
        errorImg.setImageResource(R.drawable.icon_intro_error)
        val errorImg_params = RelativeLayout.LayoutParams(imgW.toInt(),imgW.toInt())
        errorImg_params.addRule(RelativeLayout.CENTER_HORIZONTAL)
        errorImg_params.setMargins(0,0,0,0)
        contentRel.addView(errorImg,errorImg_params)

        val errorTitle = TextView(activity)
        errorTitle.id = View.generateViewId()
        errorTitle.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        errorTitle.setText(R.string.errorTitle)
        errorTitle.setTextColor(resources.getColor(R.color.colorError,null))
        errorTitle.setTextSize(20f)
        errorTitle.measure(0,0)
        errorTitle.includeFontPadding = false
//        val typeface = Typeface.createFromAsset(context?.assets, "notnmm.otf")
//        errorTitle.setTypeface(typeface)
        val errorTitle_params = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        errorTitle_params.addRule(RelativeLayout.BELOW,errorImg.id)
        errorTitle_params.setMargins(0,titleMarginT.toInt(),0,0)
        contentRel.addView(errorTitle,errorTitle_params)

        val errorSubTitle = TextView(activity)
        errorSubTitle.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        errorSubTitle.setText(R.string.errorSubTitle)
        errorSubTitle.setTextColor(resources.getColor(R.color.colorGray,null))
        errorSubTitle.setTextSize(15f)
//        errorSubTitle.setTypeface(typeface)
        errorSubTitle.measure(0,0)
        errorSubTitle.includeFontPadding = false
        val errorSubTitle_params = RelativeLayout.LayoutParams(subTitleW.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        errorSubTitle_params.addRule(RelativeLayout.BELOW,errorTitle.id)
        errorSubTitle_params.addRule(RelativeLayout.CENTER_HORIZONTAL)
        errorSubTitle_params.setMargins(0,subTitleMarginT.toInt(),0,0)
        contentRel.addView(errorSubTitle,errorSubTitle_params)
    }

}