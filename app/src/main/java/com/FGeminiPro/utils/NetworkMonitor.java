package com.FGeminiPro.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

public class NetworkMonitor {

    public interface Listener {
        void onConnected();
        void onDisconnected();
    }

    private final ConnectivityManager connectivityManager;
    private final ConnectivityManager.NetworkCallback networkCallback;
    private Listener listener;

    public NetworkMonitor(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                if (listener != null) listener.onConnected();
            }

            @Override
            public void onLost(Network network) {
                if (listener != null) listener.onDisconnected();
            }
        };
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void startMonitoring() {
        NetworkRequest request = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();
        connectivityManager.registerNetworkCallback(request, networkCallback);
    }

    public void stopMonitoring() {
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }
}