import type {ViewProps} from "ViewPropTypes";
import type {HostComponent} from "react-native";
import codegenNativeComponent from "react-native/Libraries/Utilities/codegenNativeComponent";
import {BubblingEventHandler, Double, Int32} from "react-native/Libraries/Types/CodegenTypes";

export interface NativeProps extends ViewProps {
    /**
     * If not set banner - size is adapted to the screen width, but it is possible overriding it by these sizes:
     * `BANNER` (320x50) or `LEADERBOARD` (728x90).
     */
    size?: string;

    /**
     * It is invoked after presenting banner with an object containing impression in case of success.
     * Missing [RTNAdImpression] means that banner was not present because of an error.
     */
    onPresented?: BubblingEventHandler<RTNAdImpression, 'onPresented'>;
}

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
    cpm: Double,

    /**
     * Accuracy of the cpm value. May return one of the following:
     * - 0 - [PriceAccuracy.FLOOR] eCPM floor, also known as minimum eCPMs
     * - 1 - [PriceAccuracy.BID] eCPM is the exact and committed value of the impression.
     * - 2 - [PriceAccuracy.UNDISCLOSED] - When the demand source does not agree to disclose the payout of every impression - in such cases the cpm is ‘0’
     */
    priceAccuracy: Int32,

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
    impressionDepth: Int32,

    /** The total revenue in USD from impressions of all ad formats to the current user for all sessions. */
    lifetimeRevenue: Double,
};

export default codegenNativeComponent<NativeProps>("RTNCasBanner") as HostComponent<NativeProps>;
