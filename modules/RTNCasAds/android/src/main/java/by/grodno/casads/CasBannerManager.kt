package by.grodno.casads

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import com.cleversolutions.ads.AdType
import com.cleversolutions.ads.Audience
import com.cleversolutions.ads.MediationManager
import com.cleversolutions.ads.android.CAS
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.RTNCasBannerManagerDelegate
import com.facebook.react.viewmanagers.RTNCasBannerManagerInterface

const val TAG = "CAS.AI"

@ReactModule(name = CasBannerManager.NAME)
class CasBannerManager(private val reactContext: ReactApplicationContext) : SimpleViewManager<RTNCasBanner>(),
    RTNCasBannerManagerInterface<RTNCasBanner> {
    private val delegate: ViewManagerDelegate<RTNCasBanner> = RTNCasBannerManagerDelegate(this)


    private val adManager: MediationManager by lazy {
        CAS.settings.debugMode = true
        Log.i(TAG, "Ad manager initialization started")
        CAS.buildManager()
            .withManagerId("Test-id")
            .withTestAdMode(true)
            .withAdTypes(AdType.Banner)
            .withCompletionListener {
                if (it.error == null) {
                    Log.i(TAG, "Ad manager initialized")
                } else {
                    Log.e(TAG, "Ad manager initialization failed: " + it.error)
                }
            }
            .build(reactContext.currentActivity as Context)
    }

    override fun createViewInstance(context: ThemedReactContext): RTNCasBanner =
        RTNCasBanner(context, adManager)

    override fun getDelegate(): ViewManagerDelegate<RTNCasBanner> = delegate

    override fun getName(): String = NAME

    @ReactProp(name = "size")
    override fun setSize(view: RTNCasBanner, size: String?) {
        view.setTitle(size ?: "")
    }

    companion object {
        const val NAME = "RTNCasBanner"
    }
}
