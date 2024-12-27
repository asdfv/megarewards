import type {TurboModule} from 'react-native';
import {TurboModuleRegistry} from 'react-native';

export interface Spec extends TurboModule {
    /**
     * Show interstitial ad.
     * @param interval delay in seconds till the next presentation is possible, 0 by default.
     * @returns promise containing current CPM either error.
     */
    showInterstitial(interval: number | null): Promise<number>;
}

export default TurboModuleRegistry.getEnforcing<Spec>(
    'RTNCasInterstitial',
);
