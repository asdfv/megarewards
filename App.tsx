import React, {useCallback, useEffect} from 'react';
import {Alert, SafeAreaView, StyleSheet, Text, View} from 'react-native';
import * as SplashScreen from 'expo-splash-screen';
import {useFonts} from "expo-font";
import CoinIcon from './assets/coin.svg';
import {StatusBar} from "expo-status-bar";
import Button from "./components/Button";
import AdsViewWrapper from "./app/ads/AdsView";

export default function App() {
    const [loaded, error] = useFonts({
        'Manrope_Regular': require('./node_modules/@expo-google-fonts/manrope/Manrope_400Regular.ttf'),
        'Manrope_Bold': require('./node_modules/@expo-google-fonts/manrope/Manrope_700Bold.ttf'),
        'Manrope_ExtraBold': require('./node_modules/@expo-google-fonts/manrope/Manrope_800ExtraBold.ttf'),
        'Montserrat_Bold': require('./node_modules/@expo-google-fonts/montserrat/Montserrat_700Bold.ttf'),
    });

    useEffect(() => {
        if (loaded || error) {
            SplashScreen.hideAsync();
        }
    }, [loaded, error]);

    const callback = useCallback(() => {
        Alert.alert("показ рекламы");
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
                    <Text style={styles.title}>0</Text>
                    <CoinIcon width={24} height={24}/>
                </View>
                <AdsViewWrapper />
                <Button text="Смотреть рекламу" onPress={callback} style={{marginTop: 36}}/>
            </View>
        </SafeAreaView>
    );
}

const styles = StyleSheet.create({
    mainContainer: {
        flex: 1,
        backgroundColor: '#fff',
    },
    subtitle: {
        color: 'rgba(0, 0, 0, 0.5)',
        textAlign: 'center',
        fontFamily: 'Manrope_Bold',
    },
    container: {
        alignItems: "center",
        paddingTop: 40,
        paddingRight: 16,
        paddingLeft: 16,
    },
    titleBlock: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 8,
        justifyContent: 'center'
    },
    title: {
        fontFamily: 'Montserrat_Bold',
        fontSize: 32,
        textAlign: 'center',
    }
});
