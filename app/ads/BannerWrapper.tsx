import React from 'react';
import RTNCasBanner from "rtn-cas-ads/js/specs/RTNCasBannerNativeComponent";

enum BannerSize {BANNER, LEADERBOARD, ADAPTIVE}

type IProps = {
    size?: BannerSize;
    onPresented?: (cpm: number | null) => void;
};

const BannerWrapper: React.FC<IProps> = ({size, onPresented}) => {
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
    return (
        <RTNCasBanner
            size={allowedSize}
            onPresented={({nativeEvent}) => onPresented(nativeEvent.cpm)}
            style={{width: "100%", height: 150}}
        />
    );
};

export {BannerWrapper, BannerSize};
