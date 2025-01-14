import type {TurboModule} from 'react-native';
import {TurboModuleRegistry} from 'react-native';
import {RTNAdImpression} from "./RTNCasBannerNativeComponent";

export interface Spec extends TurboModule {
    /**
     * Show interstitial ad.
     *
     * @param interval delay in seconds till the next presentation is possible, 0 by default.
     * @returns promise containing impression information.
     */
    showInterstitial(interval: number | null): Promise<RTNAdImpression>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('RTNCasInterstitial');
