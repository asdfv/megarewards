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
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.UIManagerHelper
import com.facebook.react.uimanager.events.Event
import com.facebook.react.uimanager.events.EventDispatcher

class RTNCasBanner(private val reactContext: ThemedReactContext, adManager: MediationManager) :
    FrameLayout(reactContext) {

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
                    emitOnPresented(info.cpm)
                    refreshLayout()
                }
            }
        }

    private fun refreshLayout() {
        measure(makeMeasureSpec(measuredWidth, EXACTLY), makeMeasureSpec(measuredHeight, EXACTLY))
        layout(left, top, right, bottom)
    }

    /** Emits [cpm] if present or empty event otherwise. */
    private fun emitOnPresented(cpm: Double?) {
        val eventDispatcher: EventDispatcher? = UIManagerHelper.getEventDispatcherForReactTag(reactContext, id)
        val surfaceId = UIManagerHelper.getSurfaceId(reactContext)
        val map = Arguments.createMap().apply { cpm?.let { putDouble("cpm", it) } }
        val event = OnPresentedEvent(surfaceId, id, map)
        eventDispatcher?.dispatchEvent(event)
    }

    private inner class OnPresentedEvent(
        surfaceId: Int,
        viewId: Int,
        private val payload: WritableMap
    ) : Event<OnPresentedEvent>(surfaceId, viewId) {
        override fun getEventName() = "onPresented"
        override fun getEventData() = payload
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
