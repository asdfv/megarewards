import React, {BaseSyntheticEvent} from 'react';
import RTNCasBanner, {RTNAdImpression} from "rtn-cas-ads/js/specs/RTNCasBannerNativeComponent";

enum BannerSize {BANNER, LEADERBOARD, ADAPTIVE}

type IProps = {
    size?: BannerSize;
    onPresented?: (impression: RTNAdImpression) => void;
};

const BannerWrapper: React.FC<IProps> = ({size, onPresented}) => {
    const getBannerSize = (size: BannerSize) => {
        let allowedSize: string;
        switch (size) {
            case BannerSize.BANNER:
                allowedSize = 'BANNER';
                break;
            case BannerSize.LEADERBOARD:
                allowedSize = 'LEADERBOARD';
                break;
            case BannerSize.ADAPTIVE:
                allowedSize = 'ADAPTIVE';
                break;
        }
        return allowedSize;
    }
    const onPresentedUnwrap = (event: BaseSyntheticEvent<RTNAdImpression>) => onPresented(event?.nativeEvent)
    return (
        <RTNCasBanner
            size={getBannerSize(size)}
            onPresented={onPresentedUnwrap}
            style={{width: "100%", height: 150}}/>
    );
};

export {BannerWrapper, BannerSize};
