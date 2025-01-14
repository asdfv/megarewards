package by.grodno.casads.ads.banner

import android.content.Context
import android.view.LayoutInflater
import android.view.View.MeasureSpec.EXACTLY
import android.view.View.MeasureSpec.makeMeasureSpec
import android.widget.FrameLayout
import by.grodno.casads.R
import com.cleversolutions.ads.AdError
import com.cleversolutions.ads.AdImpression
import com.cleversolutions.ads.AdSize
import com.cleversolutions.ads.AdViewListener
import com.cleversolutions.ads.MediationManager
import com.cleversolutions.ads.android.CASBannerView
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.WritableNativeMap
import com.facebook.react.fabric.FabricUIManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.UIManagerHelper
import com.facebook.react.uimanager.common.UIManagerType
import com.facebook.react.uimanager.events.Event
import com.facebook.react.uimanager.events.EventDispatcher

class RTNCasBanner(
    private val reactContext: ThemedReactContext,
    adManager: MediationManager
) : FrameLayout(reactContext) {

    private val banner: CASBannerView

    init {
        LayoutInflater.from(reactContext).inflate(R.layout.rtn_cas_banner, this, true)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        banner = initBanner(adManager)
    }

    private fun initBanner(adManager: MediationManager): CASBannerView =
        findViewById<CASBannerView>(R.id.bannerView).apply {
            manager = adManager
            adListener = object : AdViewListener {
                override fun onAdViewFailed(view: CASBannerView, error: AdError) {
                    emitOnPresented(null)
                }

                override fun onAdViewPresented(view: CASBannerView, info: AdImpression) {
                    emitOnPresented(info)
                    refreshLayout()
                }
            }
        }

    private fun refreshLayout() {
        measure(makeMeasureSpec(measuredWidth, EXACTLY), makeMeasureSpec(measuredHeight, EXACTLY))
        layout(left, top, right, bottom)
    }

    /** Emits [AdImpression] if present or empty event otherwise. */
    private fun emitOnPresented(impression: AdImpression?) {
        val eventDispatcher: EventDispatcher? = UIManagerHelper.getEventDispatcherForReactTag(reactContext, id)
        val surfaceId = UIManagerHelper.getSurfaceId(reactContext)
        val impressionMap: WritableMap? = if (impression == null) null else WritableNativeMap().apply {
            putString("adType", impression.adType.name)
            putString("network", impression.network)
            putDouble("cpm", impression.cpm)
            putInt("priceAccuracy", impression.priceAccuracy)
            putString("versionInfo", impression.versionInfo)
            putString("creativeIdentifier", impression.creativeIdentifier)
            putString("identifier", impression.identifier)
            putInt("impressionDepth", impression.impressionDepth)
            putDouble("lifetimeRevenue", impression.lifetimeRevenue)
        }
        val event = OnPresentedEvent(surfaceId, id, impressionMap)
        eventDispatcher?.dispatchEvent(event)
    }

    private class OnPresentedEvent(
        surfaceId: Int,
        viewId: Int,
        private val payload: WritableMap?
    ) : Event<OnPresentedEvent>(surfaceId, viewId) {
        override fun getEventName(): String = "onPresented"
        override fun getEventData(): WritableMap? = payload
    }

    fun setSize(string: String?) {
        banner.size = when (string) {
            "BANNER" -> AdSize.BANNER
            "LEADERBOARD" -> AdSize.LEADERBOARD
            "ADAPTIVE" -> AdSize.getAdaptiveBannerInScreen(reactContext.currentActivity as Context)
            else -> AdSize.getAdaptiveBannerInScreen(reactContext.currentActivity as Context)
        }
        refreshLayout()
    }
}
