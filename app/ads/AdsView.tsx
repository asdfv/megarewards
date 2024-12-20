import React from 'react';
import RTNCasBanner from "rtn-cas-ads/js/RTNCasBannerNativeComponent";

const AdsViewWrapper: () => JSX.Element = () => {
    return (
        <RTNCasBanner
            size="BANNER_SIZE"
            style={{ width: "100%", height: 200 }}
        />
    );
};

export default AdsViewWrapper;