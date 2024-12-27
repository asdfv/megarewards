import type {ViewProps} from "ViewPropTypes";
import type {HostComponent} from "react-native";
import codegenNativeComponent from "react-native/Libraries/Utilities/codegenNativeComponent";
import {BubblingEventHandler} from "react-native/Libraries/Types/CodegenTypes";

type BannerOnPresentedEvent = { }

export interface NativeProps extends ViewProps {
    size?: string;
    onPresented?: BubblingEventHandler<BannerOnPresentedEvent> | null;
}

export default codegenNativeComponent<NativeProps>("RTNCasBanner") as HostComponent<NativeProps>;
