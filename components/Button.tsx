import React from "react";
import {StyleSheet, Text, TouchableOpacity, ViewStyle} from "react-native";

type IProps = {
    style?: ViewStyle;
    onPress?: () => void;
    text: string;
};

const Button: React.FC<IProps> = ({ style, text, onPress }) => {
    return (
        <TouchableOpacity style={[styles.button, style]} onPress={onPress}>
            <Text style={styles.buttonText}>
                {text}
            </Text>
        </TouchableOpacity>
    )
}

const styles = StyleSheet.create({
    button: {
        height: 88,
        width: 320,
        backgroundColor: '#00B64A',
        borderRadius: 20,
        alignItems: 'center',
        justifyContent: 'center',
    },
    buttonText: {
        fontSize: 24,
        fontFamily: 'Manrope_ExtraBold',
        color: '#fff',
    }
});

export default Button;