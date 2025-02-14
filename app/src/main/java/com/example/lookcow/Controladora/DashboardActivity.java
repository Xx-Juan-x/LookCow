package com.example.lookcow.Controladora;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lookcow.R;
import com.example.lookcow.databinding.ActivityDashboardBinding;
import com.example.lookcow.databinding.ActivityDispositivoBinding;
import com.example.lookcow.databinding.ActivityTemperaturaBinding;

public class DashboardActivity extends DrawerBaseActivity {
    ActivityDashboardBinding activityBinding;
    private WebView analyticsWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityBinding.getRoot());

        analyticsWebView = findViewById(R.id.analyticsWebView);
        analyticsWebView.getSettings().setJavaScriptEnabled(true);
        analyticsWebView.setWebViewClient(new WebViewClient());
        analyticsWebView.loadUrl("https://console.firebase.google.com/u/0/project/lookcow-b911a/overview"); // Aqui va tu URL de Firebase Analytics
    }

}