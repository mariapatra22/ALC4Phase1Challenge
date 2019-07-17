package maria.alc4.alc4phase1challenge;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class AboutActivity extends AppCompatActivity {
    CoordinatorLayout layout;
    private WebView webview;
    private CurrentNetworkState networkState;
    String andelaUrl = "https://andela.com/alc/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 finish();
            }
        });
        webview = findViewById(R.id.webview_about_andela);
        networkState = new CurrentNetworkState(AboutActivity.this);
        layout = findViewById(R.id.layout_alc);
        loadWebView(andelaUrl);

    }

    private void loadWebView(String url) {
        boolean connected = networkState.getCurrentConnection();
        if (connected) {
            webview.setWebViewClient(new WebViewClient() {
                ProgressDialog progressDialog;

                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                public void onLoadResource(WebView view, String url) {
                    if (progressDialog == null) {
                        progressDialog = new ProgressDialog(AboutActivity.this);
                        progressDialog.setCancelable(true);
                        progressDialog.setMessage("just a moment...");
                        progressDialog.show();
                    }
                }

                public void onPageFinished(WebView view, String url) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    Log.d("AboutActivity","SSL error detected");
                    handler.proceed();
                }
            });
            webview.getSettings().setSupportZoom(true);
            webview.setScrollbarFadingEnabled(false);
            webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webview.getSettings().setAllowFileAccess(true);
            webview.getSettings().setDomStorageEnabled(true);
            webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setLoadWithOverviewMode(true);
            webview.getSettings().setUseWideViewPort(true);
            webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webview.loadUrl(url);
        } else {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(AboutActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(AboutActivity.this);
            }
            builder.setTitle("Network Problem")
                    .setMessage("Oops!! No internet connection. Please try again!")
                    .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            loadWebView(URL);
                        }
                    })
                    .setNegativeButton("Close ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(R.drawable.ic_alert)
                    .show();
        }
    }
}
