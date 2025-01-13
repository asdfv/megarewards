package by.grodno.casads.ads.interstitial

import android.app.Activity
import android.util.Log
import by.grodno.casads.RTNCasInterstitialNativeComponentSpec
import by.grodno.casads.TAG
import com.cleversolutions.ads.AdImpression
import com.cleversolutions.ads.AdPaidCallback
import com.cleversolutions.ads.MediationManager
import com.cleversolutions.ads.android.CAS
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.WritableNativeMap

class CasInterstitialModule(
    private val reactContext: ReactApplicationContext,
    private val adManager: MediationManager
) : RTNCasInterstitialNativeComponentSpec(reactContext) {

    override fun showInterstitial(interval: Double?, promise: Promise?) {
        if (interval != null) setInterval(interval)
        Log.d(TAG, "Interstitial starting.")
        val onPresented = object : AdPaidCallback {
            override fun onAdRevenuePaid(ad: AdImpression) {
                val impressionMap: WritableMap = WritableNativeMap().apply {
                    putString("adType", ad.adType.name)
                    putString("network", ad.network)
                    putDouble("cpm", ad.cpm)
                    putInt("priceAccuracy", ad.priceAccuracy)
                    putString("versionInfo", ad.versionInfo)
                    putString("creativeIdentifier", ad.creativeIdentifier)
                    putString("identifier", ad.identifier)
                    putInt("impressionDepth", ad.impressionDepth)
                    putDouble("lifetimeRevenue", ad.lifetimeRevenue)
                }
                promise?.resolve(impressionMap)
            }

            override fun onShowFailed(message: String) {
                promise?.reject(RuntimeException(message))
            }
        }
        adManager.showInterstitial(reactContext.currentActivity as Activity, onPresented)
    }

    private fun setInterval(interval: Double) {
        if (interval < 0) {
            Log.d(TAG, "Interstitial interval $interval is not applied, it must be non-negative value.")
        } else {
            val int = interval.toInt()
            Log.d(TAG, "Interstitial, set interval to $int seconds.")
            CAS.getSettings().interstitialInterval = int
        }
    }

    companion object {
        const val NAME = "RTNCasInterstitial"
    }
}
