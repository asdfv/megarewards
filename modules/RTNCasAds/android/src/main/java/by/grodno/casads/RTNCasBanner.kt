package by.grodno.casads

import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
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

class RTNCasBanner(
    private val reactContext: ThemedReactContext,
    adManager: MediationManager
) : LinearLayout(reactContext) {

    private val banner: CASBannerView

    init {
        LayoutInflater.from(reactContext).inflate(R.layout.rtn_cas_banner, this, true)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = VERTICAL
        banner = initBanner(adManager)
    }

    private fun initBanner(manager: MediationManager): CASBannerView {
        val bannerView = findViewById<CASBannerView>(R.id.bannerView)
        bannerView.manager = manager
        val label = findViewById<TextView>(R.id.label)
        label.text = "Here will be info about ads"
        bannerView.adListener = object : AdViewListener {
            override fun onAdViewLoaded(view: CASBannerView) {
                label.text = "Loaded"
                Log.d(TAG, "Banner Ad loaded and ready to present")
            }

            override fun onAdViewFailed(view: CASBannerView, error: AdError) {
                label.text = error.message
                emitOnPresented(PresentedResult.Error)
                Log.d(TAG, "Banner Ad received error: ${error.message}")
            }

            override fun onAdViewPresented(view: CASBannerView, info: AdImpression) {
                label.text = "Presented: ${info.cpm}"
                emitOnPresented(PresentedResult.Success)
                refreshLayout()
                Log.d(TAG, "Banner Ad presented from ${info.cpm}")
            }

            override fun onAdViewClicked(view: CASBannerView) {
                label.text = "Clicked"
                Log.d(TAG, "Banner Ad received Click action")
            }
        }
        return bannerView
    }

    private fun refreshLayout() {
        measure(
            MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY)
        )
        layout(left, top, right, bottom)
    }

    //region Events

    fun emitOnPresented(result: PresentedResult) {
        val surfaceId = UIManagerHelper.getSurfaceId(reactContext)
        val eventDispatcher = UIManagerHelper.getEventDispatcherForReactTag(reactContext, id)
        val payload = Arguments.createMap().apply { putString("result", result.name) }
        val event = OnPresentedEvent(surfaceId, id, payload)
        eventDispatcher?.dispatchEvent(event)
    }

    enum class PresentedResult { Success, Error }

    inner class OnPresentedEvent(
        surfaceId: Int,
        viewId: Int,
        private val payload: WritableMap
    ) : Event<OnPresentedEvent>(surfaceId, viewId) {
        override fun getEventName() = "onPresented"
        override fun getEventData() = payload
    }

    //endregion

    fun setSize(string: String) {
        banner.size = when (string) {
            "BANNER" -> AdSize.BANNER
            "LEADERBOARD" -> AdSize.LEADERBOARD
            else -> AdSize.BANNER
        }
    }
}
