package by.grodno.casads

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.RTNCasBannerManagerDelegate
import com.facebook.react.viewmanagers.RTNCasBannerManagerInterface

@ReactModule(name = CasBannerManager.NAME)
class CasBannerManager(context: ReactApplicationContext) : SimpleViewManager<RTNCasBanner>(), RTNCasBannerManagerInterface<RTNCasBanner> {
    private val delegate: ViewManagerDelegate<RTNCasBanner> = RTNCasBannerManagerDelegate(this)

    override fun getDelegate(): ViewManagerDelegate<RTNCasBanner> = delegate

    override fun getName(): String = NAME

    override fun createViewInstance(context: ThemedReactContext): RTNCasBanner = RTNCasBanner(context)

    @ReactProp(name = "size")
    override fun setSize(view: RTNCasBanner, size: String?) {
        view.text = size
    }

    companion object {
        const val NAME = "RTNCasBanner"
    }
}
