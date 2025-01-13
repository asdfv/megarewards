import type {TurboModule} from 'react-native';
import {TurboModuleRegistry} from 'react-native';

/** RN representation of Cas's [AdImpression], docs are copied from the original. */
export type RTNAdImpression = {
    /** The Format Type of the impression. */
    adType: string,

    /**
     * The mediated network’s name that purchased the impression.
     * All mediation network constants from [AdNetwork].
     */
    network: string,

    /**
     * The Cost Per Mille estimated impressions of the ad in USD.
     * The value accuracy is returned in the [priceAccuracy] property.
     */
    cpm: number,

    /**
     * Accuracy of the cpm value. May return one of the following:
     * - 0 - [PriceAccuracy.FLOOR] eCPM floor, also known as minimum eCPMs
     * - 1 - [PriceAccuracy.BID] eCPM is the exact and committed value of the impression.
     * - 2 - [PriceAccuracy.UNDISCLOSED] - When the demand source does not agree to disclose the payout of every impression - in such cases the cpm is ‘0’
     */
    priceAccuracy: number,

    /** Version of the network SDK rendering the ad. */
    versionInfo: string,

    /**
     * The creative id tied to the ad, if available. May be null.
     * You can report creative issues to our Ad review team using this id.
     */
    creativeIdentifier: string | null,

    /** The placement ID from the network that showed the ad */
    identifier: string,

    /** The amount of impressions of all ad formats to the current user for all sessions. */
    impressionDepth: number,

    /** The total revenue in USD from impressions of all ad formats to the current user for all sessions. */
    lifetimeRevenue: number,
};

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
