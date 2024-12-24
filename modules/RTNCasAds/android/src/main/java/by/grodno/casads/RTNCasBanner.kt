package by.grodno.casads

import android.content.Context
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

class RTNCasBanner(context: Context, adManager: MediationManager) : LinearLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.rtn_cas_banner, this, true)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = VERTICAL
        initBanner(adManager)
    }

    private fun initBanner(manager: MediationManager) {
        val bannerView = findViewById<CASBannerView>(R.id.bannerView)
        bannerView.manager = manager
        val label = findViewById<TextView>(R.id.label)
        label.text = "Here will be info about ads"
        bannerView.adListener = object : AdViewListener {
            override fun onAdViewLoaded(view: CASBannerView) {
                label.text = "Loaded"
                refreshLayout()
                Log.d(TAG, "Banner Ad loaded and ready to present")
            }

            override fun onAdViewFailed(view: CASBannerView, error: AdError) {
                label.text = error.message
                Log.d(TAG, "Banner Ad received error: ${error.message}")
            }

            override fun onAdViewPresented(view: CASBannerView, info: AdImpression) {
                label.text = "Presented: ${info.cpm}"
                refreshLayout()
                Log.d(TAG, "Banner Ad presented from ${info.cpm}")
            }

            override fun onAdViewClicked(view: CASBannerView) {
                label.text = "Clicked"
                Log.d(TAG, "Banner Ad received Click action")
            }
        }
    }

    private fun refreshLayout() {
        with(this@RTNCasBanner) {
            measure(
                MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY)
            )
            layout(left, top, right, bottom)
        }
    }

    fun setTitle(string: String) {
        findViewById<TextView>(R.id.title).text = string
    }
}
