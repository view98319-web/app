package com.FGeminiPro.utils;

import android.util.Base64;

public class GetString {
    public static String decode(String encoded) {
        return new String(Base64.decode(encoded, Base64.DEFAULT));
    }
}