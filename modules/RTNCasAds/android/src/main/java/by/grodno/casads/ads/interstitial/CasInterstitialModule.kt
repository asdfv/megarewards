package by.grodno.casads.ads.interstitial

import android.app.Activity
import android.util.Log
import by.grodno.casads.RTNCasInterstitialNativeComponentSpec
import by.grodno.casads.TAG
import com.cleversolutions.ads.AdImpression
import com.cleversolutions.ads.AdPaidCallback
import com.cleversolutions.ads.MediationManager
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

    // todo vasili use param or remove
    override fun showInterstitial(value: String?) {
        Log.d(TAG, "Start interstitial.")
        adManager.showInterstitial(reactContext.currentActivity as Activity, onPresented)
    }

    companion object {
        const val NAME = "RTNCasInterstitial"
    }
}
