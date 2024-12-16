package com.mobileapp

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View

class AdsView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    init {
        setBackgroundColor(Color.RED)
    }
}
