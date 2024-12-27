package by.grodno.casads.ads.banner

import com.cleversolutions.ads.MediationManager
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.RTNCasBannerManagerDelegate
import com.facebook.react.viewmanagers.RTNCasBannerManagerInterface

@ReactModule(name = CasBannerManager.NAME)
class CasBannerManager(private val adManager: MediationManager) :
    SimpleViewManager<RTNCasBanner>(),
    RTNCasBannerManagerInterface<RTNCasBanner> {

    private val delegate: ViewManagerDelegate<RTNCasBanner> = RTNCasBannerManagerDelegate(this)

    override fun createViewInstance(context: ThemedReactContext): RTNCasBanner = RTNCasBanner(context, adManager)

    override fun getDelegate(): ViewManagerDelegate<RTNCasBanner> = delegate

    override fun getName(): String = NAME

    @ReactProp(name = "size")
    override fun setSize(view: RTNCasBanner, size: String?) {
        size?.let { view.setSize(size) }
    }

    override fun getExportedCustomBubblingEventTypeConstants(): Map<String, Any> =
        mapOf(
            "onPresented" to mapOf(
                "phasedRegistrationNames" to mapOf(
                    "bubbled" to "onPresented",
                    "captured" to "onPresentedCapture"
                )
            )
        )

    companion object {
        const val NAME = "RTNCasBanner"
    }
}
