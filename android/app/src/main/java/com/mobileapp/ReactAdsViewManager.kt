package com.mobileapp

import android.graphics.Color
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

class ReactAdsViewManager : SimpleViewManager<AdsView>() {

    override fun getName(): String = "RCTAdsView"

    override fun createViewInstance(context: ThemedReactContext) = AdsView(context)

    @ReactProp(name = "color")
    fun setColor(view: AdsView, color: String) {
        view.setBackgroundColor(Color.parseColor(color))
    }
}
