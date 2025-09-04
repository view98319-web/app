package com.FGeminiPro;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.webkit.*;
import com.FGeminiPro.databinding.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;
import android.view.WindowInsetsController;

import com.FGeminiPro.utils.AppUtil;
import com.FGeminiPro.utils.NetworkMonitor;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    private MainBinding binding;
    private boolean IsNoInternetError = false;

    public native String Return(Context context);

    private NetworkMonitor monitor;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        binding = MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupWebview();
        setupMonitor();
        updateStatusbarIcon();
    }

    private void setupWebview() {
        /*
        getWindow().getDecorView().setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        */

        WebSettings webSettings = binding.webview1.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            WebSettingsCompat.setForceDark(webSettings, WebSettingsCompat.FORCE_DARK_AUTO);
        }
        // Make links load inside the WebView (not browser)
        // binding.webview1.setWebViewClient(new WebViewClient());

        binding.webview1.setWebViewClient(
                new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }

                    @Override
                    public void onReceivedError(
                            WebView view, WebResourceRequest request, WebResourceError error) {

                        view.loadDataWithBaseURL(null, AppUtil.error(), "text/html", "UTF-8", null);
                        IsNoInternetError = true;
                    }
                });

        binding.webview1.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        binding.webview1.loadUrl(Return(this));
    }

    public void setupMonitor() {

        monitor = new NetworkMonitor(this);

        monitor.setListener(
                new NetworkMonitor.Listener() {
                    @Override
                    public void onConnected() {
                        getWindow().setStatusBarColor(getColor(R.color.colorPrimaryDark));
                    }

                    @Override
                    public void onDisconnected() {
                        getWindow().setStatusBarColor(Color.RED);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        monitor.startMonitoring();
    }

    @Override
    protected void onStop() {
        super.onStop();
        monitor.stopMonitoring();
    }

    @Override
    public void onBackPressed() {
        if (binding.webview1.canGoBack() && !IsNoInternetError) {
            binding.webview1.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void updateStatusbarIcon() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final int nightModeFlags =
                    getResources().getConfiguration().uiMode
                            & android.content.res.Configuration.UI_MODE_NIGHT_MASK;

            WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
                    // Dark mode → light icons
                    insetsController.setSystemBarsAppearance(
                            0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
                } else {
                    // Light mode → dark icons
                    insetsController.setSystemBarsAppearance(
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
                }
            }
        } else {
            // Android 10 এর নিচে ফ্ল্যাগ দিয়ে করতে হবে
            int nightModeFlags =
                    getResources().getConfiguration().uiMode
                            & android.content.res.Configuration.UI_MODE_NIGHT_MASK;

            if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
                getWindow()
                        .getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                getWindow()
                        .getDecorView()
                        .setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }
}


