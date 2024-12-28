import type {ViewProps} from "ViewPropTypes";
import type {HostComponent} from "react-native";
import codegenNativeComponent from "react-native/Libraries/Utilities/codegenNativeComponent";
import {BubblingEventHandler} from "react-native/Libraries/Types/CodegenTypes";

type BannerOnPresentedEvent = {}

export interface NativeProps extends ViewProps {
    /**
     * If not set banner - size is adapted to the screen width, but it is possible overriding it by these sizes:
     * `BANNER` (320x50) or LEADERBOARD (728x90).
     */
    size?: string;
    /**
     * Is invoked after presenting banner with an object containing CPM:
     * `{ nativeEvent: { cpm: number } }`, missing [cpm] means that banner was not present because of an error.
     */
    onPresented?: BubblingEventHandler<BannerOnPresentedEvent> | null;
}

export default codegenNativeComponent<NativeProps>("RTNCasBanner") as HostComponent<NativeProps>;
