package by.grodno.casads.ads.interstitial

import android.app.Activity
import android.util.Log
import by.grodno.casads.RTNCasInterstitialNativeComponentSpec
import by.grodno.casads.TAG
import com.cleversolutions.ads.AdImpression
import com.cleversolutions.ads.AdPaidCallback
import com.cleversolutions.ads.MediationManager
import com.cleversolutions.ads.android.CAS
import com.facebook.react.bridge.ReactApplicationContext

class CasInterstitialModule(
    private val reactContext: ReactApplicationContext,
    private val adManager: MediationManager
) : RTNCasInterstitialNativeComponentSpec(reactContext) {

    private val onPresented = object : AdPaidCallback {
        override fun onAdRevenuePaid(ad: AdImpression) {
            Log.d(TAG, "Interstitial Ad revenue paid from " + ad.network)
        }

        override fun onShowFailed(message: String) {
            Log.e(TAG, "Interstitial Ad show failed: $message")
        }
    }

    override fun showInterstitial(interval: Double?) {
        if (interval != null) setInterval(interval)
        Log.d(TAG, "Start interstitial.")
        adManager.showInterstitial(reactContext.currentActivity as Activity, onPresented)
    }

    private fun setInterval(interval: Double) {
        if (interval < 0) {
            Log.d(TAG, "Interval $interval is not applied, it must be non-negative value.")
        } else {
            val int = interval.toInt()
            Log.d(TAG, "Set interval for interstitial to $int seconds.")
            CAS.getSettings().interstitialInterval = int
        }
    }

    companion object {
        const val NAME = "RTNCasInterstitial"
    }
}
