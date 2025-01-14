import React, {useCallback, useEffect, useState} from 'react';
import {Alert, SafeAreaView, StyleSheet, Text, View} from 'react-native';
import * as SplashScreen from 'expo-splash-screen';
import {useFonts} from "expo-font";
import CoinIcon from '../assets/coin.svg';
import {StatusBar} from "expo-status-bar";
import Button from "../components/Button";
import {BannerSize, BannerWrapper} from "./ads/BannerWrapper";
import RTNCasInterstitialNativeComponent from "rtn-cas-ads/js/specs/RTNCasInterstitialNativeComponent";
import {RTNAdImpression} from "rtn-cas-ads/js/specs/RTNCasBannerNativeComponent";

export default function App() {
    const [loaded, error] = useFonts({
        'Manrope_Regular': require('@expo-google-fonts/manrope/Manrope_400Regular.ttf'),
        'Manrope_Bold': require('@expo-google-fonts/manrope/Manrope_700Bold.ttf'),
        'Manrope_ExtraBold': require('@expo-google-fonts/manrope/Manrope_800ExtraBold.ttf'),
        'Montserrat_Bold': require('@expo-google-fonts/montserrat/Montserrat_700Bold.ttf'),
    });

    useEffect(() => {
        if (loaded || error) {
            SplashScreen.hideAsync()
        }
    }, [loaded, error]);

    const [coins, setCoins] = useState<number>(0.0)

    const addCoinsBasedOnCpm = (cpm: number) => {
        /** Convert ad presentation with [cpm] to coins. */
        const cpmToCoins = (cpm: number) => cpm * 10;
        const addCoins = (coinsToAdd: number) => setCoins(coins => coins + coinsToAdd)
        addCoins(cpmToCoins(cpm))
    }

    const onShowAdClick = useCallback(() => {
        const onSuccess = (impression: RTNAdImpression) => addCoinsBasedOnCpm(impression.cpm)
        const onError = (error: any) => Alert.alert(error.message)
        const intervalInSeconds = 5
        RTNCasInterstitialNativeComponent.showInterstitial(intervalInSeconds).then(onSuccess, onError)
    }, []);

    if (!loaded && !error) {
        return null;
    }

    return (
        <SafeAreaView style={styles.mainContainer}>
            <StatusBar style="dark"/>
            <View style={styles.container}>
                <Text style={styles.subtitle}>МОНЕТОК</Text>
                <View style={styles.titleBlock}>
                    <Text style={styles.title}>{coins}</Text>
                    <CoinIcon width={24} height={24}/>
                </View>
                <Button text="Смотреть рекламу" onPress={onShowAdClick} style={{marginBottom: 36}}/>
            </View>
            <BannerWrapper
                onPresented={(impression) => {
                    let cpm = impression.cpm;
                    if (cpm) {
                        addCoinsBasedOnCpm(cpm)
                    } else {
                        console.log("Error when loading banner.");
                    }
                }}
                size={BannerSize.ADAPTIVE}
            />
        </SafeAreaView>
    );
}

const styles = StyleSheet.create({
    mainContainer: {
        flex: 1,
        backgroundColor: '#fff',
    },
    container: {
        alignItems: "center",
        justifyContent: 'flex-end',
        paddingTop: 40,
        paddingRight: 16,
        paddingLeft: 16,
    },
    subtitle: {
        color: 'rgba(0, 0, 0, 0.5)',
        textAlign: 'center',
        fontFamily: 'Manrope_Bold',
    },
    titleBlock: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 36,
        gap: 8,
        justifyContent: 'center'
    },
    title: {
        fontFamily: 'Montserrat_Bold',
        fontSize: 32,
        textAlign: 'center',
    },
});
