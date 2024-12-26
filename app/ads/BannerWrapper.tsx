import React from 'react';
import RTNCasBanner from "rtn-cas-ads/js/RTNCasBannerNativeComponent";

enum BannerSize {BANNER, LEADERBOARD}

type IProps = {
    size?: BannerSize;
    /**
     * It is called after banner is presented. In case of successful presentation [object] contains
     * `nativeObject: { cpm: number; }`. Missing [cpm] means presentation error.
     */
    onPresented?: (object: any) => void;
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
            onPresented={onPresented}
            style={{width: "100%", height: 150}}
        />
    );
};

export {BannerWrapper, BannerSize};
