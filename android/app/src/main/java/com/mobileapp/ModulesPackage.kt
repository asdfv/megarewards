package com.mobileapp

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext

class ModulesPackage : ReactPackage {
    override fun createNativeModules(context: ReactApplicationContext): List<NativeModule> = listOf<NativeModule>()
    override fun createViewManagers(reactContext: ReactApplicationContext): List<ReactAdsViewManager> = listOf(ReactAdsViewManager())
}
