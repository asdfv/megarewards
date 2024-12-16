import React from 'react';
import {requireNativeComponent, View} from 'react-native';

const _AdsView = requireNativeComponent('RCTAdsView');

type IProps = {
    color: String;
};

const AdsViewWrapper: React.FC<IProps> = ({color}) => {
    return (
        <View>
            <_AdsView style={{width: 124, height: 124, marginTop: 64}} color={color}/>
        </View>
    );
};

export default AdsViewWrapper;
