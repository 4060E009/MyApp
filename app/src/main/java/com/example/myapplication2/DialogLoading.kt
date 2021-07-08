package com.example.myapplication2

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

class DialogLoading(private val context: Context) {

    var dialog: AlertDialog? = null

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(this.context)
        builder.setView(R.layout.loading_dialog)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog?.show()
    }

    fun startCallingDialog() {
        startLoadingDialog()
        val title = dialog?.findViewById<TextView>(R.id.titleTxt)
        title?.setText(R.string.dialog_calling)
    }

    fun successStatus() {
        val progressBar = dialog?.findViewById<ProgressBar>(R.id.progressBar)
        val successImage = dialog?.findViewById<ImageView>(R.id.successImage)
        val title = dialog?.findViewById<TextView>(R.id.titleTxt)
        progressBar?.let { fadeOut(view = it) }
        Handler().postDelayed({
            title?.let { fadeOut(view = it) }
            title?.setText(R.string.dialog_success)
            title?.let { fadeIn(view = it) }
            successImage?.let { fadeIn(view = it) }
        },1000)
        stopLoadingDialog()
    }

    fun errorStatus() {
        val progressBar = dialog?.findViewById<ProgressBar>(R.id.progressBar)
        val errorImage = dialog?.findViewById<ImageView>(R.id.errorImage)
        val title = dialog?.findViewById<TextView>(R.id.titleTxt)
        progressBar?.let { fadeOut(view = it) }
        title?.let { fadeOut(view = it) }
        Handler().postDelayed({
            title?.setText(R.string.dialog_error)
            title?.let { fadeIn(view = it) }
            errorImage?.let { fadeIn(view = it) }
        },1000)
        stopLoadingDialog()
    }

    fun stopLoadingDialog() {
        Handler().postDelayed({
            dialog?.dismiss()
            cube.updateUI()
        },2500)
    }

    @SuppressLint("ObjectAnimatorBinding")
    fun fadeOut(view: Any) {
        val fadeOut: ObjectAnimator = ObjectAnimator.ofFloat(view,"alpha",1F,0F)
        fadeOut.duration = 700
        fadeOut.start()
    }

    @SuppressLint("ObjectAnimatorBinding")
    fun fadeIn(view: Any) {
        val fadeIn: ObjectAnimator = ObjectAnimator.ofFloat(view,"alpha",0F,1F)
        fadeIn.duration = 700
        fadeIn.start()
    }
}