package com.example.githubrepos.utils

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsIntent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar

fun ImageView.loadImage(ref: String?) {
    Glide.with(context)
        .load(ref)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun View.showError(message: String) {
    val duration = Snackbar.LENGTH_LONG
    Snackbar.make(this, message, duration).apply {
        anchorView?.let { this.anchorView = it }
        show()
    }
}

fun Context.openUrl(url: String){
    val customTabsIntent = CustomTabsIntent.Builder().build()
    customTabsIntent.launchUrl(this, Uri.parse(url))
}