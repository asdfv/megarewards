import React from 'react';
import RTNCasBanner from "rtn-cas-ads/js/RTNCasBannerNativeComponent";

enum BannerSize {BANNER, LEADERBOARD}

type IProps = {
    size?: BannerSize;
    onPresented?: () => void; // todo
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
    }
    return (
        <RTNCasBanner
            size={allowedSize}
            style={{width: "100%", height: 150}}
        />
    );
};

export {BannerWrapper, BannerSize};
