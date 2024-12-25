package by.grodno.casads

import com.facebook.react.TurboReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider
import com.facebook.react.uimanager.ViewManager

class CasPackage : TurboReactPackage() {
    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> =
        listOf(CasBannerManager(reactContext))

    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> =
        emptyList()

    override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? {
        return if (name == CasBannerManager.CLASS_NAME) CasBannerManager(reactContext) else null
    }

    override fun getReactModuleInfoProvider(): ReactModuleInfoProvider = ReactModuleInfoProvider {
        val info = ReactModuleInfo(
            _name = CasBannerManager.CLASS_NAME,
            _className = CasBannerManager.CLASS_NAME,
            _canOverrideExistingModule = false,
            _needsEagerInit = false,
            isCxxModule = false,
            isTurboModule = true,
        )
        mapOf(CasBannerManager.CLASS_NAME to info)
    }
}
