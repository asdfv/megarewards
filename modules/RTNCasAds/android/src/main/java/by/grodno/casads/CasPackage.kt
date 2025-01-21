package by.grodno.casads

import android.content.Context
import android.util.Log
import by.grodno.casads.ads.banner.CasBannerManager
import by.grodno.casads.ads.interstitial.CasInterstitialModule
import com.cleversolutions.ads.AdType
import com.cleversolutions.ads.MediationManager
import com.cleversolutions.ads.android.CAS
import com.facebook.react.TurboReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider
import com.facebook.react.uimanager.ViewManager

/** Tag for logs is the same as internal tag for CAS logging for easier filtering. */
const val TAG = "CAS.AI"

class CasPackage : TurboReactPackage() {

    private fun getAdManager(reactContext: ReactApplicationContext): MediationManager = CAS.manager ?: run {
        val isDebug = BuildConfig.DEBUG
        CAS.settings.debugMode = isDebug
        val mode = if (isDebug) "Debug" else "Release"
        val appId = reactContext.applicationContext.packageName
        Log.i(TAG, "Ad manager initialization started in $mode mode with App ID $appId.")
        CAS.buildManager()
            .withCasId(appId)
            .withTestAdMode(isDebug)
            .withAdTypes(AdType.Banner, AdType.Interstitial)
            .withCompletionListener {
                if (it.error == null) {
                    Log.i(TAG, "Ad manager initialized.")
                } else {
                    Log.e(TAG, "Ad manager initialization failed: ${it.error}")
                }
            }
            .build(reactContext.currentActivity as Context)
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> =
        listOf(CasBannerManager(getAdManager(reactContext)))

    override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? {
        return if (name == CasInterstitialModule.NAME) {
            CasInterstitialModule(reactContext, getAdManager(reactContext))
        } else {
            null
        }
    }

    override fun getReactModuleInfoProvider(): ReactModuleInfoProvider = ReactModuleInfoProvider {
        val bannerModuleInfo = ReactModuleInfo(
            _name = CasBannerManager.NAME,
            _className = CasBannerManager.NAME,
            _canOverrideExistingModule = false,
            _needsEagerInit = false,
            isCxxModule = false,
            isTurboModule = true,
        )
        val interstitialModuleInfo = ReactModuleInfo(
            _name = CasInterstitialModule.NAME,
            _className = CasInterstitialModule.NAME,
            _canOverrideExistingModule = false,
            _needsEagerInit = false,
            isCxxModule = false,
            isTurboModule = true
        )
        mapOf(
            CasBannerManager.NAME to bannerModuleInfo,
            CasInterstitialModule.NAME to interstitialModuleInfo
        )
    }
}
