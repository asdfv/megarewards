import React from 'react';
import RTNCasBanner from "rtn-cas-ads/js/RTNCasBannerNativeComponent";

type IProps = {
    size?: string;
    onPresented?: () => void; // todo
};

const AdsViewWrapper: React.FC<IProps> = ({size, onPresented}) => {
    let allowedSize: string;
    if (["BANNER", "LEADERBOARD"].includes(size)) {
        allowedSize = size;
    } else {
        allowedSize = "BANNER";
    }

    return (
        <RTNCasBanner
            size={allowedSize}
            style={{width: "100%", height: 150}}
        />
    );
};

export default AdsViewWrapper;
